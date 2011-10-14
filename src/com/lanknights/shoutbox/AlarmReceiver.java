package com.lanknights.shoutbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String DEBUG_TAG = "AlarmReceiver";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(DEBUG_TAG, "Recurring alarm; requesting shouts update. Last ID Proc'd: " + LKShoutManager.processedlast);
        
        if(!(LKShoutManager.processedlast == 0)) {
    		Toast.makeText(LKShoutboxDisplay.shoutcontainer.getContext(), "Checking for new shouts!", Toast.LENGTH_LONG);

        	LKShoutManager.UpdateShouts(LKLogin.session_id, LKLogin.secure_hash);
        }
        
    }
}
