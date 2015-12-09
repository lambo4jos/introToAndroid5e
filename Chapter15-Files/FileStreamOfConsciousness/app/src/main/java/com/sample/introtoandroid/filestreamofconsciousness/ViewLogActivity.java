package com.sample.introtoandroid.filestreamofconsciousness;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ViewLogActivity extends AppCompatActivity {
    public static final String LOG_FILENAME = "Chat_Log.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView log = (TextView) findViewById(R.id.TextViewCurrentLogFile);

        // Read the file, dump it into the TextView
        try {
            InputStream iFile = openFileInput(LOG_FILENAME);
            log.setText(inputStreamToString(iFile));
        } catch (Exception e) {
            log.setText("Couldn't read log file.");
        }

        // Handle Send Button
        final Button clearLog = (Button) findViewById(R.id.ButtonClearLog);
        clearLog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If the file exists, Delete the file
                if (java.util.Arrays.binarySearch(fileList(), LOG_FILENAME) != (-1)) {
                    deleteFile(LOG_FILENAME);
                }

                // Update the screen
                final TextView log = (TextView) findViewById(R.id.TextViewCurrentLogFile);
                log.setText(null);
            }
        });

    }

    // Converts an input stream to a string so we can stick it in the TextView
    // Typically, this sort of possibly lengthy operation would be wrapped in a thread,
    // and perhaps the UI would show a progress indicator as the file loaded into memory
    // but for this example, we keep it super simple for readability, since our chat log is tiny
    // To see an example of using another thread to offload this sort of operation,
    // see the FileStreamOfConsciousness.java file, specifically the logChatMessage() method
    public String inputStreamToString(InputStream is) throws IOException {
        StringBuffer sBuffer = new StringBuffer();
        BufferedReader dataIO = new BufferedReader(new InputStreamReader(is));
        String strLine;

        while ((strLine = dataIO.readLine()) != null) {
            sBuffer.append(strLine);
            sBuffer.append("\n");
        }

        dataIO.close();
        is.close();

        return sBuffer.toString();
    }
}
