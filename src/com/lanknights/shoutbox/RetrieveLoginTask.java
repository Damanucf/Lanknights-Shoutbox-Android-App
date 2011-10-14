package com.lanknights.shoutbox;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

public class RetrieveLoginTask  extends AsyncTask<String, Void, Void> {

	public Context startContext;
	
	RetrieveLoginTask(Context parent) {
		startContext = parent;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		 LKLogin.ExecuteLogin(params[0], params[1]);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void a) {
		Log.d("LKShoutbox", "Logged in! " + LKLogin.session_id + " " + LKLogin.secure_hash + " " + LKLogin.pass_hash);
    	Intent myIntent = new Intent(startContext, LKShoutboxDisplay.class);
    	startContext.startActivity(myIntent);
    	((Activity)startContext).finish();
	}
	
}
