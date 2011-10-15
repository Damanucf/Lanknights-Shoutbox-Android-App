package com.lanknights.shoutbox;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Shout implements Parcelable {
	public int ID;
	public String username;
	public String text;
	
	Shout(int sID, String sUsername, String sText) {
		ID = sID;
		username = sUsername;
		text = sText;
	}
	
	public Shout(Parcel in) {
		readFromParcel(in);
	}
	
	public static final Parcelable.Creator<Shout> CREATOR = new Parcelable.Creator<Shout>() {
		public Shout createFromParcel(Parcel in) {
			return new Shout(in);
		}
		
		public Shout[] newArray(int size) {
			return new Shout[size];
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(ID);
		out.writeString(username);
		out.writeString(text);
	}

	public void readFromParcel(Parcel in) {
		ID = in.readInt();
		username = in.readString();
		text = in.readString();
	}
}

