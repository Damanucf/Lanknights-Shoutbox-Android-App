package com.lanknights.shoutbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.Toast;


//import android.util.Log;

public class LKShoutManager {
	//stores shouts format: {{int ID, string name(w/ formatting), string shout, string ID}}
	
	public static ArrayList<Shout> processedshouts = new ArrayList<Shout>();
	public static int processedlast = 0;
	
	public static void UpdateShouts(String session_id, String secure_hash) {
    	HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://www.lanknights.net/index.php?s=" + session_id + "&app=shoutbox&module=ajax&section=coreAjax&secure_key=" + secure_hash + "&type=getShouts&lastid=" + LKShoutManager.processedlast + "&global=1");
        //member_id is apparently only necessary after the first request. Otherwise it returns "nopermission\n"
        httpget.setHeader("Cookie", "member_id=" + LKLogin.member_id + "; pass_hash=" + LKLogin.pass_hash + "; session_id=" + session_id + "; anonlogin=-1;");

		try {
			HttpResponse retrieve = httpclient.execute(httpget);
			
			BufferedReader r = new BufferedReader(new InputStreamReader(retrieve.getEntity().getContent()));
			StringBuilder composited = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				composited.append(line + "\n");
			}
			
			Pattern parsepat = Pattern.compile("(?s)<tr class=\'row2\' id=\'shout-row-(\\d{5})\'>\\n\\t<td align=\'right\' valign=\'top\' nowrap=\'nowrap\' width=\'1%\'>\\n\\t\\t.*?\\n\\t\\t\\t<a href=\'http://www.lanknights.net/user/.*?/\' title=\'.*?\'>(.*?)</a>&nbsp;<a href=\'http://www.lanknights.net/user/.*?/\' class=\'__user __id\\d*\' title=\'View Profile\'><img src=\'http://www.lanknights.net/public/style_images/cethin/user_popup.png\' alt=\'Icon\' /></a>\\n\\t\\t:\\n\\t</td>\\n\\t<td id=\"shoutbody\" valign=\'top\' width=\'99%\'>\\n\\t\\t<span class=\'right shoutbox_time\' title=\'\'>.*?</span>\\n\\t\\t\\n\\t\\t\\t(.*?)\\n\\t\\t\\n\\t</td>", Pattern.DOTALL);
			ArrayList<Shout> listtest = getMatches(parsepat, composited.toString());
//			processedshouts.addAll(0, listtest);
			
			Log.d("LKShoutbox", "Updateshouts found new shouts: " + listtest.size());

			LKShoutboxDisplay.layoutShouts(listtest);
//			LKShoutManager.processedlast = LKShoutManager.processedshouts.get(0).ID;

		} catch (PatternSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
	}
	
	public static ArrayList<Shout> downloadParseShouts(String session_id, String secure_hash) {
		Log.d("LKShoutbox", "Downloading and parsing shouts...");
    	HttpClient httpclient = new DefaultHttpClient();
    	//&global=1 changes the output it gives us slightly. 
        HttpGet httpget = new HttpGet("http://www.lanknights.net/index.php?s=" + session_id + "&app=shoutbox&module=ajax&section=coreAjax&secure_key=" + secure_hash + "&type=getShouts&lastid=0&global=1");
        httpget.setHeader("Cookie", "member_id= " + LKLogin.member_id + "; pass_hash=" + LKLogin.pass_hash + "; session_id=" + session_id + "; anonlogin=-1;");

        try {
			HttpResponse retrieve = httpclient.execute(httpget);
			
        	BufferedReader r = new BufferedReader(new InputStreamReader(retrieve.getEntity().getContent()));
        	StringBuilder composited = new StringBuilder();
        	String line;
        	while ((line = r.readLine()) != null) {
        		composited.append(line + "\n");
        	}
        	
        	Log.d("LKShoutbox", "dparseshouts http get length: " + composited.length());
        	
        	//regexp will match: group(1) = rowID
        	//					 group(2) = username w/ link/formatting
        	//					 group(3) = shout text
        	Pattern parsepat = Pattern.compile("(?s)<tr class=\'row2\' id=\'shout-row-(\\d{5})\'>\\n\\t<td align=\'right\' valign=\'top\' nowrap=\'nowrap\' width=\'1%\'>\\n\\t\\t.*?\\n\\t\\t\\t<a href=\'http://www.lanknights.net/user/.*?/\' title=\'.*?\'>(.*?)</a>&nbsp;<a href=\'http://www.lanknights.net/user/.*?/\' class=\'__user __id\\d*\' title=\'View Profile\'><img src=\'http://www.lanknights.net/public/style_images/cethin/user_popup.png\' alt=\'Icon\' /></a>\\n\\t\\t:\\n\\t</td>\\n\\t<td id=\"shoutbody\" valign=\'top\' width=\'99%\'>\\n\\t\\t<span class=\'right shoutbox_time\' title=\'\'>.*?</span>\\n\\t\\t\\n\\t\\t\\t(.*?)\\n\\t\\t\\n\\t</td>", Pattern.DOTALL);
//        	Matcher parsemat = parsepat.matcher(composited.toString());
//        	parsemat.find();
//        	Log.i("hurr", parsemat.group(1));
//        	Log.i("hurr", parsemat.group(2) + " " + parsemat.group(3));
        	ArrayList<Shout> listtest = getMatches(parsepat, composited.toString());
        	
        	Log.d("LKShoutbox", "Returning listtest, length: " + listtest.size());
        	
        	httpclient.getConnectionManager().shutdown();
        	return listtest;
        	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Log.d("LKShoutbox", "Returning null?");
        return null;

	}
	public static void GetShouts(String session_id, String secure_hash) {
        new DownloadParseTask().execute(session_id, secure_hash);
	}
	
	//credit to "puredangertech" for this utility, modified to support the repetetive 3 matches I perform.
	public static ArrayList<Shout> getMatches(Pattern pattern, String text) {
		ArrayList<Shout> matches = new ArrayList<Shout>();
		Matcher m = pattern.matcher(text);
//		int match= 0;
		while(m.find()){
//			Log.i("FUCKING WORK", "a: " + m.start());
			matches.add(new Shout(Integer.parseInt(m.group(1)), m.group(2), m.group(3)));
//			match++;
		}
		return matches;
	}

	public static boolean postShout(String text, String session_id, String secure_hash) {

    	HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.lanknights.net/index.php?s=" + session_id + "&app=shoutbox&module=ajax&section=coreAjax&secure_key=" + secure_hash + "&type=submit");
        httppost.setHeader("Cookie", "meber_id=" + LKLogin.member_id + "; pass_hash=" + LKLogin.pass_hash + "; session_id=" + session_id + "; anonlogin=-1;");
        
        try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("shout", text));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
        	BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        	StringBuilder composited = new StringBuilder();
        	String line;
        	while ((line = r.readLine()) != null) {
        		composited.append(line + "\n");
        	}
        	int shit = composited.length();
        	
        	Log.d("LKShoutbox", shit + " ");
        	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        httpclient.getConnectionManager().shutdown();
		return true;
	}

}