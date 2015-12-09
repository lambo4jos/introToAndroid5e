package com.sample.introtoandroid.simplefiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;


public class SimpleFilesActivity extends Activity {
    private static final String DEBUG_TAG = "SimpleFilesActivity Log";
    private static final String SOME_FILE_CONTENTS = "Sea turtles can be found all over the world, and yet all sea turtle species are on the endangered species listing. The loggerhead turtle is among these. ";
    private static final String MORE_FILE_CONTENTS = "In the Pacific, loggerhead turtles are born on the beaches of Japan. The baby turtles hatch from their shells at night, in hopes of avoiding being spotted by a predator and being eaten. They make their way toward to ocean, navigating by light. In the past, this the brighest light at night was the sea itself, but manmade lights can now disrupt and confuse these little turtles. Only a portion of the babies make it down the sand and into the ocean.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_files);

        // The guts of this example, working with files in various ways
        runFileAccessExample();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_files, menu);
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

    public void runFileAccessExample() {
        Log.i(DEBUG_TAG, "Begin File Example");
        String file1 = "file1.txt";

        // if the file exists, delete it
        if (Arrays.binarySearch(fileList(), file1) >= 0) {
            // Delete the old  file
            deleteFile(file1);
        }

        // write some text to a file, creating the file if necessary
        FileOutputStream fos;
        try {
            fos = openFileOutput(file1, MODE_PRIVATE);
            fos.write(SOME_FILE_CONTENTS.getBytes());
            fos.close();
            Log.i(DEBUG_TAG, "Wrote to File: " + file1);
        } catch (Exception e) {
            Log.i(DEBUG_TAG, "openFileOutput (new file) threw exception: "
                    + e.getMessage());
        }

        // Read back our whole file and print it as a single line string
        readAppFileAndLog(file1);
        // Read it again, only this time, 70 characters at a time
        readAppFileAndLogAsString(file1);

        // append some stuff to the file
        try {
            fos = openFileOutput(file1, MODE_APPEND);
            fos.write(MORE_FILE_CONTENTS.getBytes());
            fos.close();
            Log.i(DEBUG_TAG, "Appended File: " + file1);
        } catch (Exception e) {
            Log.i(DEBUG_TAG, "openFileOutput (append) threw exception: "
                    + e.getMessage());
        }

        // Read back our file again so we can see the appended text
        readAppFileAndLog(file1);

        Log.i(DEBUG_TAG, "INSPECTING APPLICATION FILE DIRECTORY at Context.getFilesDir()");
        File pathForAppFiles = getFilesDir();
        logFileDetails(pathForAppFiles);

        Log.i(DEBUG_TAG, "Listing Files in " + pathForAppFiles.getAbsolutePath());
        String[] fileList = pathForAppFiles.list();
        for(int i=0; i< fileList.length; i++)        {
            Log.i(DEBUG_TAG, "Filename " + i+": " + fileList[i] );
        }

        Log.i(DEBUG_TAG, "INSPECTING APPLICATION FILE DIRECTORY at Context.getCacheDir()");
        File pathCacheDir = getCacheDir();
        logFileDetails(pathCacheDir);

        String strCacheFileName = "myCacheFile.cache";
        File newCacheFile = new File(pathCacheDir, strCacheFileName);

        try {
            Log.i(DEBUG_TAG, "Creating a new file in the Cache Dir: " + strCacheFileName);
            final boolean newFile = newCacheFile.createNewFile();
            Log.i(DEBUG_TAG, "Created a new file in the Cache Dir: " + newFile);
            logFileDetails(newCacheFile);

            // Write to the file
            FileOutputStream foCache = new FileOutputStream(newCacheFile.getAbsolutePath());
            foCache.write(SOME_FILE_CONTENTS.getBytes());
            foCache.close();

            // Read what we wrote to the file back
            Log.i(DEBUG_TAG, "CACHED FILE CONTENTS:");
            readAnyFileAndLog(newCacheFile.getAbsolutePath());
        } catch (Exception e) {
            Log.i(DEBUG_TAG, "createNewFile threw exception: "
                    + e.getMessage());
        }

        Log.i(DEBUG_TAG, "Listing Files in " + pathCacheDir.getAbsolutePath());
        String[] fileListCache = pathCacheDir.list();
        for(int i=0; i< fileListCache.length; i++)        {
            Log.i(DEBUG_TAG, "Filename " + i+": " + fileListCache[i] );
        }

        Log.i(DEBUG_TAG, "Deleting file in the Cache Dir: " + strCacheFileName);
        final boolean cacheDelete = newCacheFile.delete();
        Log.i(DEBUG_TAG, "Deleted file in the Cache Dir: " + cacheDelete);

        Log.i(DEBUG_TAG, "Listing Files in " + pathCacheDir.getAbsolutePath());
        String[] fileListCacheRefeshed = pathCacheDir.list();
        for(int i=0; i< fileListCacheRefeshed.length; i++)        {
            Log.i(DEBUG_TAG, "Filename " + i+": " + fileListCacheRefeshed[i] );
        }

        // Back to the Context functions...
        Log.i(DEBUG_TAG, "Trying to delete the file we created: " + file1);
        if (deleteFile(file1)) {
            Log.i(DEBUG_TAG, "Deleted file: " + file1 + " successfully.");
        }

        Log.i(DEBUG_TAG, "End File Example");
    }

    // Log some file details
    public void logFileDetails(File file)	{
        if(file.isDirectory())        {
            Log.i(DEBUG_TAG, file.getAbsolutePath() + " is a DIRECTORY");
        }
        if(file.isFile())        {
            Log.i(DEBUG_TAG, file.getAbsolutePath() + " is a FILE");
        }
        if(file.isHidden())        {
            Log.i(DEBUG_TAG, file.getAbsolutePath() + " is HIDDEN");
        }
        if(file.exists())        {
            Log.i(DEBUG_TAG, file.getAbsolutePath() + " EXISTS");
        }
        if(file.canRead())        {
            Log.i(DEBUG_TAG, file.getAbsolutePath() + " is READABLE");
        }
        if(file.canWrite())        {
            Log.i(DEBUG_TAG, file.getAbsolutePath() + " is WRITEABLE");
        }
    }

    // Logs a file with newlines every 70 characters ( prints better in the logcat screen)
    // Typically, this kind of file operation might be wrapped in a thread
    // but for this example, we keep it super simple for readability
    // For a threading example, see the FileStreamOfConsciousness sample application
    //
    // This method only works for the application directory
    public void readAppFileAndLog(String filename) {
        FileInputStream fis;

        try {
            fis = openFileInput(filename);
            StringBuffer sBuffer = new StringBuffer();
            int chunkSize = 70;
            byte[] bf = new byte[chunkSize];

            // read 50 bytes at a time
            while ((fis.read(bf, 0, chunkSize)) != -1) {
                String str = new String(bf);
                sBuffer.append(str);
                sBuffer.append("\n");
                if(fis.available() < 50)
                {
                    Arrays.fill(bf, 0, chunkSize, (byte) ' '); // zero out our buffer so the next line only contains the remainder bytes
                }
            }
            fis.close();

            Log.i(DEBUG_TAG, "File Contents:\n" + sBuffer.toString());
            Log.i(DEBUG_TAG, "\nEOF");
        } catch (Exception e) {
            Log.i(DEBUG_TAG, "openFileInput threw exception: "+ e.getMessage());
        }

    }

    // Logs a file with newlines every 70 characters (prints better in the logcat screen)
    // Typically, this kind of file operation might be wrapped in a thread
    public void readAnyFileAndLog(String filename) {
        FileInputStream fis;

        try {
            fis = new FileInputStream(filename);
            StringBuffer sBuffer = new StringBuffer();
            int chunkSize = 70;
            byte[] bf = new byte[chunkSize];

            // read 50 bytes at a time
            while ((fis.read(bf, 0, chunkSize)) != -1) {
                String str = new String(bf);
                sBuffer.append(str);
                sBuffer.append("\n");
                if(fis.available() < 50)
                {
                    Arrays.fill(bf, 0, chunkSize, (byte) ' '); // zero out our buffer so the next line only contains the remainder bytes
                }
            }

            fis.close();

            Log.i(DEBUG_TAG, "File Contents:\n" + sBuffer.toString());
            Log.i(DEBUG_TAG, "\nEOF");
        } catch (Exception e) {
            Log.i(DEBUG_TAG, "openFileInput threw exception: "+ e.getMessage());
        }
    }

    // Logs a file as one long string
    // Typically, this kind of file operation might be wrapped in a thread
    public void readAppFileAndLogAsString(String filename) {
        FileInputStream fis;

        try {
            fis = openFileInput(filename);
            StringBuffer sBuffer = new StringBuffer();
            BufferedReader dataIO = new BufferedReader (new InputStreamReader(fis));
            String strLine;

            // read a line at a time
            while ((strLine = dataIO.readLine()) != null) {
                sBuffer.append(strLine);
                sBuffer.append("\n");
            }

            dataIO.close();
            fis.close();

            Log.i(DEBUG_TAG, "File Contents:\n" + sBuffer.toString());
            Log.i(DEBUG_TAG, "\nEOF");
        } catch (Exception e) {
            Log.i(DEBUG_TAG, "openFileInput threw exception: "+ e.getMessage());
        }
    }
}
