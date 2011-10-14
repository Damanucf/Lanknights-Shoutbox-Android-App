package com.lanknights.shoutbox;


import java.security.DigestException;
//import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
//import android.text.util.Linkify;
import android.util.Log;
//import android.view.View;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LKShoutboxDisplay extends Activity {
	
	static LinearLayout shoutcontainer;
	static ArrayList<TextView> finalshouts = new ArrayList<TextView>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LKShoutbox", "OnCreate Called");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shoutboxdisplay);
        if(LKShoutManager.processedlast == 0) {
        	LKShoutManager.GetShouts(LKLogin.session_id, LKLogin.secure_hash);
	        
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.MINUTE, 1);
	        Intent intent = new Intent(super.getApplicationContext(), AlarmReceiver.class);
	        PendingIntent sender = PendingIntent.getBroadcast(this, 135432, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	
	        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
	        am.setInexactRepeating(AlarmManager.RTC, cal.getTimeInMillis(), (long)30000, sender);
	
	        shoutcontainer = (LinearLayout)findViewById(R.id.linearshoutcontainer);
	        
	        //handle on-enter submission
	        final EditText postBox = (EditText)findViewById(R.id.posttxt);
	        postBox.setOnKeyListener(new OnKeyListener() //thanks stackoverflow
	        {
	            public boolean onKey(View v, int keyCode, KeyEvent event)
	            {
	                if (event.getAction() == KeyEvent.ACTION_DOWN)
	                {
	                    switch (keyCode)
	                    {
	                        case KeyEvent.KEYCODE_DPAD_CENTER:
	                        case KeyEvent.KEYCODE_ENTER:
	                            if(LKShoutManager.postShout(postBox.getText().toString(), LKLogin.session_id, LKLogin.secure_hash)) {
	                            	postBox.setText(null);
	                            	Toast.makeText(postBox.getContext(), "Posted!", 5000).show();
	                            }
	                            LKShoutManager.UpdateShouts(LKLogin.session_id, LKLogin.secure_hash);
	                            return true;
	                        default:
	                            break;
	                    }
	                }
	                return false;
	            }
	        });
        }
    }
    
    public static void layoutShouts(ArrayList<Shout> newList) {
    	LKShoutManager.processedshouts.addAll(0, newList);
    	try {
			layoutShouts();
		} catch (DigestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	LKShoutManager.processedlast = LKShoutManager.processedshouts.get(0).ID;
    	
    }
    
    public static void insertShouts() {
    	for(TextView t : finalshouts) {
    		if (shoutcontainer.getChildCount() < 50) {
        		if(LKShoutManager.processedlast == 0) {
        			shoutcontainer.addView(t);
        		} else {
        			shoutcontainer.addView(t, 0);
        		}
    		} else if(LKShoutManager.processedlast != 0 && t.getId() > LKShoutManager.processedlast) {
    			shoutcontainer.removeViewAt(49);
    			shoutcontainer.addView(t, 0);
    		}
    	}
    }
    
    public static void debugShouts() {

    	Log.d("LKShoutbox", "shoutcontainer kids before debugrun: " + shoutcontainer.getChildCount());
//    	for(TextView t: finalshouts) {
//    		t.bringToFront();
//    		t.forceLayout();
//    		t.postInvalidate();
//    		t.setVisibility(1);
    		//    		shoutcontainer.addView(t);
//    	}
//    	shoutcontainer.getParent().requestLayout();
    	shoutcontainer.postInvalidate();
    	Log.d("LKShoutbox", "shoutcontainer kids before debugrun: " + shoutcontainer.getChildCount());

    }
    
    public static void layoutShouts() throws DigestException {
    	
    	LinearLayout container = shoutcontainer;
    	Pattern usercolor = Pattern.compile("color:#(.*?);");
    	Pattern helloitismethewebsiteandIcannotdecideifIwanttousewordsorhexadecimal = Pattern.compile("color:(.*?);");
    	
    	//I assume declaring these once outside of the loop is beneficial to memory usage
    	Matcher colmatcher;
    	SpannableString tempUser;
    	Spanned parsedname;
    	
    	for(Shout i : LKShoutManager.processedshouts) {
    		//
    		// TODO: THIS IS TOO SLOW.
    		//
    		if(LKShoutManager.processedlast >= i.ID) {
    			//do nothing
    		} else {

	    		TextView tempshout = new TextView(container.getContext());
	    		tempshout.setTextColor(Color.parseColor("#FFFFFF"));
	//    		tempshout.setText(Html.fromHtml(i.username + ": " + i.text));
	    		
	    		colmatcher = usercolor.matcher(i.username);
	    		parsedname = Html.fromHtml(i.username);
				tempUser = new SpannableString(parsedname.toString());
				
	    		if(!colmatcher.find()) {
	    			colmatcher = helloitismethewebsiteandIcannotdecideifIwanttousewordsorhexadecimal.matcher(i.username);
	    			if(!colmatcher.find()) {
	    				tempshout.setText(parsedname); //No CSS color identifier was found, set the name to the name with HTML parsed out.
	    			} else {
	    				tempUser.setSpan(new StyleSpan(Typeface.BOLD), 0, tempUser.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //Anyone who has a color is also bold.
	    				//there has to be a better way to do this in Java
	    				if(colmatcher.group(1).matches("gold")) {
	    					tempUser.setSpan(new ForegroundColorSpan(Color.parseColor("#FFD700")), 0, parsedname.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	    				} else if(colmatcher.group(1).matches("white")) {
//	    					tempUser.setSpan(new ForegroundColorSpan(0xFFFFFF), 0, parsedname.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //names are already white, save memory?
	    				} else {
	    					throw new DigestException("program had a hard time digesting bad styling complain to gamma");
	    				}
	
	    			}
	    		} else {
	    			tempUser.setSpan(new StyleSpan(Typeface.BOLD), 0, parsedname.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	    			tempUser.setSpan(new ForegroundColorSpan(Integer.parseInt(colmatcher.group(1).substring(1,colmatcher.group(1).length()))), 0, parsedname.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //wow
	    		}
	
	    		tempshout.setText(tempUser);
	    		tempshout.append(Html.fromHtml(": " + i.text));
//	    		tempshout.setBackgroundColor(Color.parseColor("#1F1F1F"));
	    		
	//    		tempshout.setText(Html.fromHtml("<span style='color:gold;'><b>JESUS CHRIST WORK ALREADY</b></font>" + ":" + "aaaaaaaa"));
	    		
	//    		tempshout.setAutoLinkMask(Linkify.ALL);
	    		tempshout.setMovementMethod(LinkMovementMethod.getInstance());
	    		tempshout.setId(i.ID);
	    		
	    		Log.d("Username test", i.username);
	    		
	    		if(LKShoutManager.processedlast != 0) {
	    			finalshouts.add(0, tempshout);
	    		} else {
	    			finalshouts.add(tempshout);
	    		}

//	    		LKShoutManager.processedlast = i.ID;
    		}
    	}	
    	insertShouts();
    }

    @Override
    public void onRestart() {
    	Log.d("LKShoutbox", "ShoutboxDisplay onPause() called");
    	super.onRestart();
    }
    @Override
    public void onPause() {
    	Log.d("LKShoutbox", "ShoutboxDisplay onPause() called");
    	Log.d("LKShoutbox", "Number of children of shout container: " + shoutcontainer.getChildCount() );
    	super.onPause();
    }
    @Override
    public void onResume() {
    	Log.d("LKShoutbox", "ShoutboxDisplay onResume() called");
    	Log.d("LKShoutbox", "Number of children of shout container: " + shoutcontainer.getChildCount() );
    	if(LKShoutManager.processedlast != 0) {
    		LKShoutManager.processedlast = 0;
//    		insertShouts();
    		debugShouts();
    	}
    	
    	super.onResume();
    }
    @Override
    public void onStop() {
    	Log.d("LKShoutbox", "ShoutboxDisplay onStop() called");
    	Log.d("LKShoutbox", "Number of children of shout container: " + shoutcontainer.getChildCount() );
    	
    	super.onStop();
    }
    

    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
      menu.add(1, 1, 1, "Options");
      menu.add(1, 2, 2, "Refresh");
      
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
    	
    	case 2:
    		Toast.makeText(this.getApplicationContext(), "Refreshing...", 1);
    		LKShoutManager.UpdateShouts(LKLogin.session_id, LKLogin.secure_hash);
    		break;
    	}
    	return true;
    }
}
