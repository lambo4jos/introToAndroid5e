package com.introtoandroid.sample.simplehardware;

import android.view.Menu;
import android.view.MenuItem;

public class SimpleHardwareActivity extends MenuActivity {
    @Override
    void prepareMenu() {
        addMenuItem("1. Sensors Sample", SensorsActivity.class);
        addMenuItem("2. Battery Monitor", BatteryActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_hardware, menu);
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
