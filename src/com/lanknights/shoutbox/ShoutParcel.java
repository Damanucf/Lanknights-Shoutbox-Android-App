package com.lanknights.shoutbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


//This is necessary because apparently AIDL does not support arrays.(Shout[])
public class ShoutParcel implements Parcelable {
	public Shout[] shouts;

	public ShoutParcel() {
	}
	
	public ShoutParcel(Parcel in) {
		readFromParcel(in);
	}
	
	public static final Parcelable.Creator<ShoutParcel> CREATOR = new Parcelable.Creator<ShoutParcel>() {
		public ShoutParcel createFromParcel(Parcel in) {
			return new ShoutParcel(in);
		}
		
		public ShoutParcel[] newArray(int size) {
			return new ShoutParcel[size];
		}
	};
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel out, int flags) {
		out.writeList(Arrays.asList(shouts));
	}
	
	public void readFromParcel(Parcel in) {
		List<Shout> tempList = new ArrayList<Shout>();
		in.readList(tempList, Shout.class.getClassLoader());
		shouts = tempList.toArray(new Shout[tempList.size()]); //USE THIS FOR ANY LIST -> OBJ ARRAY CONVERSION
		
	}
}
