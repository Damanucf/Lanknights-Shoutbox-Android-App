package com.lanknights.shoutbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LKLogin {
	
	public static String session_id;
	public static String secure_hash;
	public static String pass_hash;
	public static String member_id;
	
	public static void ExecuteLogin(String username, String password) {
    	//yell at me about how dumb and repetetive this segment is
		
    	DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.lanknights.net/index.php?app=core&module=global&section=login&do=process");
        
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("referer", "http://www.lanknights.net/index.php?"));
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("rememberMe", "1"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            
            Header cookie = response.getFirstHeader("Set-Cookie");
//            HeaderElement[] exploreMe = cookie.getElements();
            
//            Log.i(null, "Session: " + cookie.getElements()[0].getValue());
            for(Cookie element : httpclient.getCookieStore().getCookies()) {
            	Log.d("LKShoutbox", "Name: " + element.getName() + " Value: " + element.getValue());
            }
            session_id = cookie.getElements()[0].getValue();
            pass_hash = httpclient.getCookieStore().getCookies().get(1).getValue();
            member_id = httpclient.getCookieStore().getCookies().get(0).getValue();
            
        } catch (ClientProtocolException e) {
            // TODO alertidalog error here, or throw exception up stack...
//        	Toast.makeText(context, "ERROR: ClientProtocolException(who knows what causes this)", duration).show();
        } catch (IOException e) {
            // TODO alertidalog error here, or throw exception up stack...
//        	Toast.makeText(context, "ERROR: There was an IOException in HTTPPost!(you probably don't have internet enabled)", duration).show();
        }
        
        HttpGet mainpage = new HttpGet("http://www.lanknights.net/index");
        mainpage.addHeader("Cookie", "session_id=" + session_id + ";");
        
        try {
        	HttpResponse getinfo = httpclient.execute(mainpage);
        	
        	BufferedReader r = new BufferedReader(new InputStreamReader(getinfo.getEntity().getContent()));
        	String line;
        	Pattern securehashpattern = Pattern.compile("ipb.vars\\[\'secure_hash\'\\] \t\t= \'(.*?)\';");

        	while ((line = r.readLine()) != null) {
        	    if(line.contains("secure_hash")) {
        	    	Matcher securehashmatcher = securehashpattern.matcher(line);
        	    	securehashmatcher.find();
        	    	secure_hash = securehashmatcher.group(1);
        	    }
        	}

        } catch (ClientProtocolException e) {
            // TODO alertidalog error here, or throw exception up stack...
//        	Toast.makeText(context, "ERROR: ClientProtocolException", duration).show();
        } catch (IOException e) {
            // TODO alertidalog error here, or throw exception up stack...
//        	Toast.makeText(context, "ERROR: There was an IOException in HTTPPost!", duration).show();
        }
        httpclient.getConnectionManager().shutdown();
	}
}