package com.fhs.mensaproductrecomentation.network.task;

import java.util.List;

import com.fhs.mensaproductrecomentation.data.FootRecord;
import com.fhs.mensaproductrecomentation.network.Communicator;

import android.os.AsyncTask;
/**Task that downloads the plan*/
public class DownloadPlanTask extends AsyncTask<String, Integer, List<FootRecord> > {

	@Override
    protected List<FootRecord> doInBackground(String... params) {
	    
		Communicator com = new Communicator();
		com.connect();
		List<FootRecord> result = com.givePlan(params[0]);
		com.disconnect();		
	    return result;
    }
	
}
