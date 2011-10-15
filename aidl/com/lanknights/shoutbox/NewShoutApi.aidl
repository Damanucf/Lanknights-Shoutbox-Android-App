package com.lanknights.shoutbox;

import com.lanknights.shoutbox.LKLogin;
import com.lanknights.shoutbox.LKShoutManager;
import com.lanknights.shoutbox.ShoutParcel;
import com.lanknights.shoutbox.NewShoutListener;

interface NewShoutApi {

	void setLastProcessed(in int proc);

	void setLKLogin(in LKLogin login);
	
	LKShoutManager getCurrentMan();

	void getShoutsNow();
	
	ShoutParcel getLatestShouts();
	
	void addListener(NewShoutListener listener);
	
	void removeListener(NewShoutListener listener);
}