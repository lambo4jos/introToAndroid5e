package com.introtoandroid.advancedlayouts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AdaptersActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String[] menu = {"ContactAdapter", "ListAdapterSample"};
    private static final String DEBUG_TAG = "AdaptersActivity";
    private Map<String, Object> actions = new HashMap<>();

    private static final int REQUEST_CONTACTS = 0;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actions.put(menu[0], new Intent(this, ContactAdapterActivity.class));
        actions.put(menu[1], new Intent(this, ListAdapterSampleActivity.class));

        setContentView(R.layout.menu_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView av = (ListView) findViewById(R.id.menu_list);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);

        av.setAdapter(adapter);
        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                if (ActivityCompat.checkSelfPermission(AdaptersActivity.this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    Log.i(DEBUG_TAG, "Contact permissions not granted. Requesting permissions.");
                    ActivityCompat
                            .requestPermissions(AdaptersActivity.this, PERMISSIONS_CONTACT,
                                    REQUEST_CONTACTS);
                } else {
                    Log.i(DEBUG_TAG,
                            "Contact permissions granted. Displaying contacts.");
                    String key = (String) parent.getItemAtPosition(position);
                    startActivity((Intent) actions.get(key));
                }
//                String text = (String) parent.getItemAtPosition(position);
//                startActivity((Intent) actions.get(text));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
            Log.d(DEBUG_TAG, "Received response for contact permissions request.");

            // All Contact permissions must be checked
            if (verifyPermissions(grantResults)) {
                // All required permissions granted, proceed as usual
                Log.d(DEBUG_TAG, "Contacts permissions were granted.");
                Toast.makeText(this, "Contacts Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Log.d(DEBUG_TAG, "Contacts permissions were denied.");
                Toast.makeText(this, "Contacts Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // One result must be present
        if (grantResults.length < 1) {
            return false;
        }

        // Verify each required permission is granted
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}