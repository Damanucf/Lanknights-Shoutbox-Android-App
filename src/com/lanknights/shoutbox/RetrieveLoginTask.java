package com.lanknights.shoutbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class RetrieveLoginTask  extends AsyncTask<String, Void, Void> {

	private LKLogin taskLogin; 
	private Context startContext;
	
	RetrieveLoginTask(Context parent) {
		startContext = parent;
		taskLogin = new LKLogin();
	}
	
	@Override
	protected Void doInBackground(String... params) {
		 taskLogin.ExecuteLogin(params[0], params[1]);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void a) {
		Log.d("LKShoutbox", "Logged in! " + taskLogin.session_id + " " + taskLogin.secure_hash + " " + taskLogin.pass_hash);
    	Intent myIntent = new Intent(startContext, LKShoutboxDisplay.class);
        
    	//Information is passed between activities using bundles, recreate the login on the other side.
    	//Can't make LKLogin static due to the service needing these values 24/7, so we have to pass it off to it.
//        Bundle loginBundle = new Bundle();
//        loginBundle.putParcelable("LoginData", taskLogin);

        myIntent.putExtra("LoginData", taskLogin);
//        myIntent.putExtra("LoginData", loginBundle);
    	startContext.startActivity(myIntent);
    	((Activity)startContext).finish();
	}
	
}
