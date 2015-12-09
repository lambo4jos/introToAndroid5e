package com.introtoandroid.simplecontentprovider;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.content.CursorLoader;
import android.util.Log;

public class SimpleCallLog extends Activity {
    private static final String DEBUG_TAG = "SimpleCallLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_content_provider);
        try {
            String[] requestedColumns = {
                    CallLog.Calls.CACHED_NUMBER_LABEL,
                    CallLog.Calls.DURATION
            };
            CursorLoader loader = new CursorLoader(this,
                    CallLog.Calls.CONTENT_URI,
                    requestedColumns,
                    CallLog.Calls.CACHED_NUMBER_LABEL + " = ?",
                    new String[]{"HourlyClient123"},
                    null);
            Cursor calls = loader.loadInBackground();

            Log.d(DEBUG_TAG, "Call count: " + calls.getCount());
            int durIdx = calls.getColumnIndex(CallLog.Calls.DURATION);
            int totalDuration = 0;
            calls.moveToFirst();
            while (!calls.isAfterLast()) {
                Log.d(DEBUG_TAG, "Duration: " + calls.getInt(durIdx));
                totalDuration += calls.getInt(durIdx);
                calls.moveToNext();
            }
            Log.d(DEBUG_TAG, "HourlyClient123 Total Call Duration: " + totalDuration);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Failed: ", e);
        }
    }
}
