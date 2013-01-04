/**
 * 
 */
package com.fhs.mensaproductrecomentation;

import java.util.concurrent.TimeUnit;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.fhs.mensaproductrecomentation.network.task.VoteTask;

/**
 * handles user input
 * 
 * @author fabian
 */
public class ClickeventHandler implements OnClickListener {
	/** id to vote for */
	private Long	     id;
	
	/** index of radio element */
	private Integer	     index;
	
	/** the userid */
	private String	     userId;
	
	/**the smiley in the view that represents the mean*/
	private ImageView	 image;
	
	public ClickeventHandler(String userId, Long id, Integer index, ImageView image) {
		super();
		this.id = id;
		this.index = index;
		this.userId = userId;
		
		this.image = image;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		
		try {
			Float mean = new VoteTask(id, index, userId).execute().get(2, TimeUnit.SECONDS);
			image.setImageResource(MainActivity.IMAGE_ID[Math.round(mean)]);
		} catch (Exception e) {
			Log.e(ClickeventHandler.class.getSimpleName(), e.toString());
		}
	}
	
	
	
}
