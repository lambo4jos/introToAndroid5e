package com.introtoandroid.samples.simplepreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MoreSimplePreferencesActivity extends SuperSimplePreferencesActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set some prefs for this specific activity
        SharedPreferences settingsActivity = getPreferences(MODE_PRIVATE);
        if(!settingsActivity.contains(PREFERENCE_STRING_NAME)) {
            SharedPreferences.Editor prefEditor = settingsActivity.edit();
            prefEditor.putString(PREFERENCE_STRING_NAME, this.getLocalClassName());
            prefEditor.putLong("SomeLong", java.lang.Long.MIN_VALUE);
            prefEditor.apply();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    Class<?> GetTargetClass() {
        // Where the "Go to other activity" action will send us
        return SimplePreferencesActivity.class;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more_simple_preferences, menu);
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
