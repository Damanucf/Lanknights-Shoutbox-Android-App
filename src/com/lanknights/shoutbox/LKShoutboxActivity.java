package com.lanknights.shoutbox;
/*
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpUriRequest;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.Header;
//import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
*/

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.Context;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
//import android.widget.Toast;
import android.widget.Toast;

public class LKShoutboxActivity extends Activity {
	
	public static final String PREFS_NAME = "LKShoutboxPrefs";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        //Loads settings from file, sets username/pwd if the user previously checked remember me.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String userStored = settings.getString("Username", "");
        String pwdStored = settings.getString("Password", "");
        
        
        ((EditText)findViewById(R.id.unameinput)).setText(userStored);
        ((EditText)findViewById(R.id.pwdinput)).setText(pwdStored);
        
        if(userStored.length() > 0 && pwdStored.length() > 0) {
        	((CheckBox)findViewById(R.id.remembermecbox)).setChecked(true);
        }
        
    }
    
    public void onClickLogin(View v) {
    	
    	//If we should remember the user, save them to disk.
    	if(((CheckBox)findViewById(R.id.remembermecbox)).isChecked()) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            
            //this is dumb, why is this necessary
            SharedPreferences.Editor editor = settings.edit();
            
            editor.putString("Username", ((EditText)findViewById(R.id.unameinput)).getText().toString());
            editor.putString("Password", ((EditText)findViewById(R.id.pwdinput)).getText().toString());
            
            editor.commit();
    	}
    	
    	//Attempt login in new thread
    	new RetrieveLoginTask(v.getContext()).execute(((EditText)findViewById(R.id.unameinput)).getText().toString(), ((EditText)findViewById(R.id.pwdinput)).getText().toString());

    	//Something nice
    	CharSequence text = "Logging in!";
    	int duration = Toast.LENGTH_LONG;
 
    	Toast toast = Toast.makeText(v.getContext(), text, duration);
    	toast.show();
    }
    
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
      menu.add(1, 1, 1, "Options");

      return super.onCreateOptionsMenu(menu);  
    }  
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case 1:
    		Toast.makeText(this, "Options menu opening..", 1);
    		Intent prefIntent = new Intent(this, LKPreferences.class);
    		this.startActivity(prefIntent);
    		break;
    	}
    	
    	return true;
    }
}