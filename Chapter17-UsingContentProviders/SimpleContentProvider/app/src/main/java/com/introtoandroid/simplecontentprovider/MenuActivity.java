package com.introtoandroid.simplecontentprovider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.SortedMap;
import java.util.TreeMap;

public abstract class MenuActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String DEBUG_TAG = "MenuActivity";
    private SortedMap<String, Object> actions = new TreeMap<>();

    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    private static String[] PERMISSIONS_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final int REQUEST_CALL_LOG = 1;
    private static String[] PERMISSIONS_CALL_LOG = {Manifest.permission.READ_CALL_LOG};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        ListView av = (ListView) findViewById(R.id.list);

        prepareMenu();
        String[] keys = actions.keySet().toArray(
                new String[actions.keySet().size()]);
        av.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, keys));

        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(SimpleContentProviderActivity.DEBUG_TAG, "pos: " + position + " , id: " + id);
                handleClick((ListView) parent, view, position, id);
            }
        });
    }

    public void addMenuItem(String label, Class<?> cls) {
        actions.put(label, new Intent(this, cls));
    }

    abstract void prepareMenu();

    protected void handleClick(ListView l, View v, int position, long id) {
        if (position == 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.i(DEBUG_TAG, "External Storage permissions not granted. Requesting permissions.");
                ActivityCompat
                        .requestPermissions(MenuActivity.this, PERMISSIONS_EXTERNAL_STORAGE,
                                REQUEST_EXTERNAL_STORAGE);
            } else {
                String key = (String) l.getItemAtPosition(position);
                startActivity((Intent) actions.get(key));
            }
        } else if (position == 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                        != PackageManager.PERMISSION_GRANTED) {
                Log.i(DEBUG_TAG, "Contact permissions not granted. Requesting permissions.");
                ActivityCompat
                        .requestPermissions(MenuActivity.this, PERMISSIONS_CALL_LOG,
                                REQUEST_CALL_LOG);
            } else {
                String key = (String) l.getItemAtPosition(position);
                startActivity((Intent) actions.get(key));
            }
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            Log.d(DEBUG_TAG, "Received response for external storage permissions request.");
            // External Storage permissions must be checked
            if (verifyPermissions(grantResults)) {
                // All required permissions granted, proceed as usual
                Log.d(DEBUG_TAG, "External storage permission was granted.");
                Toast.makeText(this, "External Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Log.d(DEBUG_TAG, "External Storage permission was denied.");
                Toast.makeText(this, "External Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == REQUEST_CALL_LOG) {
            Log.d(DEBUG_TAG, "Received response for permissions request.");

            // Call Log permission must be checked
            if (verifyPermissions(grantResults)) {
                // All required permissions granted, proceed as usual
                Log.d(DEBUG_TAG, "Call Log permission was granted.");
                Toast.makeText(this, "Call Log permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Log.d(DEBUG_TAG, "Call Log permission was denied.");
                Toast.makeText(this, "Call Log permission Denied",
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
