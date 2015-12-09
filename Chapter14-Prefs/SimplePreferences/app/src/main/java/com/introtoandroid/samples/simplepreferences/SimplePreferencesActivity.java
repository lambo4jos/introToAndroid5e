package com.introtoandroid.samples.simplepreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SimplePreferencesActivity extends SuperSimplePreferencesActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Set some prefs for this specific activity
        SharedPreferences settingsActivity = getPreferences(MODE_PRIVATE);
        if(!settingsActivity.contains(PREFERENCE_STRING_NAME)) {
            // Set some new prefs
            SharedPreferences.Editor prefEditor = settingsActivity.edit();
            prefEditor.putBoolean("Boolean_Pref", false);
            prefEditor.putFloat("Float_Pref", java.lang.Float.NEGATIVE_INFINITY);
            prefEditor.putInt("Int_Pref", java.lang.Integer.MIN_VALUE);
            prefEditor.putString(PREFERENCE_STRING_NAME, this.getLocalClassName());
            prefEditor.apply();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    Class<?> GetTargetClass() {
        // Where the "Go to other activity" action will send us
        return MoreSimplePreferencesActivity.class;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
