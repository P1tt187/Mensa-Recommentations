package com.fhs.mensaproductrecomentation.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import android.util.Log;
import android.widget.ImageView;

import com.fhs.mensa.network.MessageConstants;
import com.fhs.mensaproductrecomentation.MainActivity;
import com.fhs.mensaproductrecomentation.data.FootRecord;

/** class to handle server connections */
public class Communicator {
	/** the serverport */
	public static final int	PORT	= 8080;
	
	/** the server connection */
	private Socket	  connection;
	/** input stream */
	private Scanner	  input;
	/** output stream */
	private Formatter	output;
	
	public List<FootRecord> givePlan(String userid) {
		output.format("%s%n", MessageConstants.GIVE_PLAN);
		output.format("%s%n", userid);
		output.flush();
		List<FootRecord> result = new LinkedList<FootRecord>();
		String response;
		while (!(response = input.nextLine().trim()).equals(MessageConstants.FINISH_PLAN.name())) {
			FootRecord element = new FootRecord();
			element.name = response;
			element.id = input.nextLong();
			element.mean = input.nextFloat();
			element.tagSum = input.nextShort();
			input.nextLine();
			result.add(element);
		}
		
		return result;
	}
	
	public void disconnect() {
		output.format("%s%n", MessageConstants.DISCONNECT);
		output.flush();
		try {
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Float vote(String userID, Long id, Integer index) {
		output.format(Locale.ENGLISH, "%s%n", MessageConstants.VOTE);
		output.flush();
		output.format(Locale.ENGLISH, "%d %d %s%n", id, index, userID);
		output.flush();
		String response = input.next();
		
		Log.d("server vote response", response);
		Float mean = Float.parseFloat(response);
		input.nextLine();
		return mean;
		
		
	}
	
	public boolean connect() {
		try {
			
			connection = new Socket("piddy87.dyndns-ip.com", PORT);
			// connection = new Socket(InetAddress.getByName("192.168.2.131"),
			// 8080);
			input = new Scanner(connection.getInputStream());
			input.useLocale(Locale.ENGLISH);
			output = new Formatter(connection.getOutputStream());
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
