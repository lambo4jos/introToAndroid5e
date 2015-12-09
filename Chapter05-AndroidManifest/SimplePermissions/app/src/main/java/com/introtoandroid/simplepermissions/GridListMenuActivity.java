package com.introtoandroid.simplepermissions;

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

public class GridListMenuActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String DEBUG_TAG = "GridListMenuActivity";
    private SortedMap<String, Object> actions = new TreeMap<>();

    private static final int REQUEST_CONTACTS = 1;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

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
                Log.d(GridListMenuActivity.DEBUG_TAG, "pos: " + position + " , id: " + id);
                handleClick((ListView) parent, view, position, id);
            }
        });
    }

    public void addMenuItem(String label, Class<?> cls) {
        actions.put(label, new Intent(this, cls));
    }

    void prepareMenu() {
        addMenuItem("1. Grid w/Adapter", SimpleGridFragmentActivity.class);
        addMenuItem("2. List w/Adapter", SimpleListFragmentActivity.class);
    }

    protected void handleClick(ListView l, View v, int position, long id) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(DEBUG_TAG, "Contact permissions not granted. Requesting permissions.");
            ActivityCompat
                    .requestPermissions(GridListMenuActivity.this, PERMISSIONS_CONTACT,
                            REQUEST_CONTACTS);
        } else {
            Log.i(DEBUG_TAG,
                    "Contact permissions granted. Displaying contacts.");
            String key = (String) l.getItemAtPosition(position);
            startActivity((Intent) actions.get(key));
        }
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