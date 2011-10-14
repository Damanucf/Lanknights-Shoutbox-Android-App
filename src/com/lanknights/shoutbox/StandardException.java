package com.lanknights.shoutbox;

import android.app.AlertDialog;
import android.content.Context;

public class StandardException extends Exception {
	
	private static final long serialVersionUID = 1;
	
	public StandardException() {
		super();
	}
	
	public StandardException(String message) {
		super(message);
	}
	
	public void alertDialog(Context context) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("ERROR");
		dialog.setMessage(this.toString());
		dialog.setNeutralButton("OK", null);
		dialog.create().show();
	}
}
