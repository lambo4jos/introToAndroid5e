package com.introtoandroid.simplecontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SimpleContactsActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String DEBUG_TAG = "SimpleContactsActivity";

    private static final int REQUEST_CONTACTS = 1;
    private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_contacts);
    }

    public void listContact(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(DEBUG_TAG, "Contact permissions not granted. Requesting permissions.");
            ActivityCompat
                    .requestPermissions(SimpleContactsActivity.this, PERMISSIONS_CONTACT,
                            REQUEST_CONTACTS);
        } else {
            String[] requestedColumns = {
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
            };
            CursorLoader loader = new CursorLoader(this,
                    ContactsContract.Data.CONTENT_URI,
                    requestedColumns, null, null, "display_name desc limit 1");
            Cursor contacts = loader.loadInBackground();

            int recordCount = contacts.getCount();
            Log.d(DEBUG_TAG, "Contacts Count: " + recordCount);

            if (recordCount > 0) {
                int nameIdx = contacts
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int phoneIdx = contacts
                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                contacts.moveToFirst();
                Log.d(DEBUG_TAG, "Name: " + contacts.getString(nameIdx));
                Log.d(DEBUG_TAG, "Phone: " + contacts.getString(phoneIdx));
            }
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
                Log.d(DEBUG_TAG, "Contact permissions were granted.");
                Toast.makeText(this, "Contact Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Log.d(DEBUG_TAG, "Contact permissions were denied.");
                Toast.makeText(this, "Contact Permission Denied",
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
