package com.fhs.mensaproductrecomentation.network.task;

import com.fhs.mensaproductrecomentation.network.Communicator;

import android.os.AsyncTask;

public class VoteTask extends AsyncTask<Void, Integer, Float> {

	
	/** id to vote for */
	private Long	     id;
	
	/** index of radio element */
	private Integer	     index;
	
	/** the userid */
	private String	     userId;
	/** network interface */
	private Communicator	communicator;
	
	/**
	 * @param id
	 * @param index
	 * @param userId
	 * @param communicator
	 */
    public VoteTask(Long id, Integer index, String userId) {
	    super();
	    this.id = id;
	    this.index = index;
	    this.userId = userId;
	    this.communicator = new Communicator();
    }

    @Override
	protected Float doInBackground(Void... params) {
		communicator.connect();
		Float mean = communicator.vote(userId, id, index);
		communicator.disconnect();
		return mean;
	}
	
	
}
