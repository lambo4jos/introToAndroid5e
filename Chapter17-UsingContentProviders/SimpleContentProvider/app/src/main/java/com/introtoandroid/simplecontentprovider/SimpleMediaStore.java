package com.introtoandroid.simplecontentprovider;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;

public class SimpleMediaStore extends Activity {
    private static final String DEBUG_TAG = "SimpleMediaStore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_content_provider);
        try {
            String[] requestedColumns = {
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DURATION
            };
            CursorLoader loader = new CursorLoader(this,
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    requestedColumns, null, null, null);
            Cursor cur = loader.loadInBackground();

            Log.d(DEBUG_TAG, "Audio files: " + cur.getCount());
            Log.d(DEBUG_TAG, "Columns: " + cur.getColumnCount());
            // String[] columns = cur.getColumnNames();
            int name = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int size = cur.getColumnIndex(MediaStore.Audio.Media.DURATION);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                Log.d(DEBUG_TAG, "Title" + cur.getString(name));
                Log.d(DEBUG_TAG, "Length: " + cur.getInt(size) / 1000 + " seconds");
                cur.moveToNext();
            }
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Failed: ", e);
        }
    }
}
