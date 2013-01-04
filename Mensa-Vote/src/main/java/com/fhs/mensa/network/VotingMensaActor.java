package com.fhs.mensa.network;

import java.net.Socket;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.fhs.mensa.datastorage.DatabaseOperations;
import com.fhs.mensa.datastorage.HibernateUtil;
import com.fhs.mensa.network.messages.FootContent;

public class VotingMensaActor implements Runnable {
	
	/** the connection */
	private Socket	  connection;
	/** input stream */
	private Scanner	  input;
	/** output stream */
	private Formatter	output;
	
	public VotingMensaActor(Socket connection) {
		
		this.connection = connection;
		try {
			input = new Scanner(connection.getInputStream());
			input.useLocale(Locale.ENGLISH);
			output = new Formatter(connection.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onReceive(String messageString) throws Exception {
		System.out.println("Message " + messageString);
		
		MessageConstants message = MessageConstants.valueOf(messageString.trim());
		switch (message) {
		
			case GIVE_PLAN:
				givePlan();
				break;
			case DISCONNECT:
				input.close();
				output.close();
				connection.close();
				
				Thread.currentThread().interrupt();
				break;
			case VOTE:
				try {
					vote();
				} catch (Exception e) {
					e.printStackTrace();
					output.format("%f%n", new Float(0));
					output.flush();
				}
				break;
			default:
				
		}
		
	}
	
	private void vote() {
		Long id = input.nextLong();
		Short rating = input.nextShort();
		String userString = input.nextLine().trim();
		System.out.println("" + id + " " + rating + " " + userString);
		
		ActorHelper.vote(HibernateUtil.getSession(), userString, rating, id);
		Float mean = DatabaseOperations.getMeanVotesForFoot(HibernateUtil.getSession(), DatabaseOperations.getFootById(HibernateUtil.getSession(), id));
		output.format(Locale.ENGLISH, "%f%n", mean);
		output.flush();
	}
	
	private void givePlan() {
		
		String userID = input.nextLine();
		List<FootContent> plan = ActorHelper.givePlan(HibernateUtil.getSession(), userID);
		System.out.println("send: " + plan);
		for (FootContent content : plan) {
			output.format(Locale.ENGLISH, "%s%n", content.getName());
			output.format(Locale.ENGLISH, "%d %f %d%n", content.getId(), content.getMean(), content.getTagSum());
			output.flush();
		}
		output.format(Locale.ENGLISH, "%s%n", MessageConstants.FINISH_PLAN);
		output.flush();
	}
	
	@Override
	protected void finalize() throws Throwable {
		HibernateUtil.getSession().close();
		super.finalize();
		
	}
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				while (!input.hasNext() && !Thread.interrupted()) {
					Thread.sleep(10);
				}
				if (Thread.interrupted()) {
					return;
				}
				onReceive(input.nextLine());
				
			} catch (Exception e) {
				
				e.printStackTrace();
				return;
			}
		}
		
	}
	
}
