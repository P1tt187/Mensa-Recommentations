package com.fhs.mensaproductrecomentation;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;


import com.fhs.mensaproductrecomentation.data.FavoridFinder;
import com.fhs.mensaproductrecomentation.data.FootRecord;
import com.fhs.mensaproductrecomentation.network.task.DownloadPlanTask;

/**
 * the main activity
 * 
 * @author fabian
 */
public class MainActivity extends Activity {
	/** ressource constants */
	public static final int[]	IMAGE_ID	= new int[] { R.drawable.gelb, R.drawable.naja, R.drawable.gruen };
	
	/** the user id */
	private String	          userid;
	/** the favorite */
	private FootRecord	      favorite;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getPreferences(MODE_PRIVATE);
		userid = settings.getString("userid", null);
		if (userid == null) {
			userid = UUID.randomUUID().toString();
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("userid", userid);
			editor.commit();
		}
		
		List<FootRecord> plan;
		try {
			plan = new DownloadPlanTask().execute(userid).get(10, TimeUnit.SECONDS);
			favorite = FavoridFinder.findFavorite(plan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			plan = new LinkedList<FootRecord>();
			System.exit(0);
		}
		
		Log.d(getString(R.string.app_name), plan.toString());
		// setContentView(R.layout.main);
		initLayout(plan);
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(getString(R.string.app_name), "startet");
		
	}
	
	private void initLayout(List<FootRecord> plan) {
		
		ScrollView rootLayout = new ScrollView(this);
		LinearLayout childView = new LinearLayout(this);
		childView.setOrientation(LinearLayout.VERTICAL);
		WindowManager mWinMgr = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Point displaySize = new Point();
		
		mWinMgr.getDefaultDisplay().getSize(displaySize);
		
		for (FootRecord element : plan) {
			LinearLayout layout = new LinearLayout(this);
			
			layout.setPadding(0, 0, 0, 15);
			
			TextView footView = new TextView(this);
			footView.setMaxWidth(displaySize.x / 3);
			footView.setText(element.name);
			
			ImageView meanImage = new ImageView(this);
			meanImage.setImageResource(IMAGE_ID[Math.round(element.mean)]);
			
			RadioGroup radioGroup = new RadioGroup(this);
			radioGroup.setOrientation(RadioGroup.HORIZONTAL);
			
			for (int i = 0; i < IMAGE_ID.length; i++) {
				RadioButton radioButton = new RadioButton(this);
				
				radioButton.setBackgroundResource(IMAGE_ID[i]);
				radioButton.setOnClickListener(new ClickeventHandler(userid, element.id, i, meanImage));
				radioButton.setMaxWidth((displaySize.x / 3) / 4);
								
				radioGroup.addView(radioButton, standardLayoutParams());
			}
			
			ImageView recommendedImage = new ImageView(this);
			recommendedImage.setImageResource(R.drawable.ic_recommend);
			if (!element.equals(favorite)) {
				recommendedImage.setVisibility(View.INVISIBLE);
			} 
			
			layout.addView(footView, standardLayoutParams());
			layout.addView(radioGroup, standardLayoutParams());
			layout.addView(meanImage, standardLayoutParams());
			layout.addView(recommendedImage);
			childView.addView(layout, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		rootLayout.addView(childView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setContentView(rootLayout);
	}
	
	private LayoutParams standardLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}
	
}
