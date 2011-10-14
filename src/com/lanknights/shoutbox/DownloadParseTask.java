package com.lanknights.shoutbox;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadParseTask extends AsyncTask<String, Void, ArrayList<Shout>> {
	
	/*
	private String session_id;
	private String secure_hash;
	
	public DownloadParseTask(String session_id, String secure_hash) {
		this.session_id = session_id;
		this.secure_hash = secure_hash;
	}
	*/
	
	@Override
	protected ArrayList<Shout> doInBackground(String... params) {
		 return LKShoutManager.downloadParseShouts(params[0], params[1]);
	}
	
	@Override
	protected void onPostExecute(ArrayList<Shout> result) {
		if(result != null) {
		Log.d("LKShoutbox", "Going to layout the shouts with a result of length: " + result.size());
		LKShoutboxDisplay.layoutShouts(result);
		} else {
			//do nothing?
		}
	}
	
}
