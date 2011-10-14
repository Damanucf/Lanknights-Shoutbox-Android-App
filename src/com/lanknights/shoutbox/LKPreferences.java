package com.lanknights.shoutbox;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.method.DigitsKeyListener;

public class LKPreferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.lkpreferences);
        EditTextPreference myEditText = (EditTextPreference)findPreference("numShoutsPref");
        myEditText.getEditText().setKeyListener(DigitsKeyListener.getInstance(false,true));
    }

}
