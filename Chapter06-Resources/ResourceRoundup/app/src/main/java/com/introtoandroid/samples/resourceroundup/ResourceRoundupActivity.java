package com.introtoandroid.samples.resourceroundup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ResourceRoundupActivity extends Activity {

    /** Called when the activity is first created. */
    private static final String DEBUG_TAG= "ResourceRoundup Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_roundup);

        // SOME SIMPLE RESOURCE HANDLING (a string, a bold styled string, a color, a dimension and a simple drawable
        String myResourceString = getResources().getString(R.string.hello);
        CharSequence myResourceStringRich = getResources().getText(R.string.boldhello);
        int myResourceColor = ContextCompat.getColor(this, R.color.prettyTextColor);
        float myResourceDimension = getResources().getDimension(R.dimen.textPointSize);
        ColorDrawable myResourceDrawable = (ColorDrawable) ContextCompat.getDrawable(this, R.drawable.redDrawable);

        Log.d(DEBUG_TAG, "myResourceString value is: " + myResourceString);
        Log.d(DEBUG_TAG, "myResourceStringRich value is: " + myResourceStringRich.toString());
        Log.d(DEBUG_TAG, "myResourceColor value is: " + myResourceColor);
        Log.d(DEBUG_TAG, "myResourceDimension value is: " + myResourceDimension);
        if (myResourceDrawable != null) {
            Log.d(DEBUG_TAG, "myResourceDrawable value is: " + myResourceDrawable.toString()); // Not very exciting, I know.
        }

        // SOME STRING-ARRAY HANDLING
        String[] aFlavors = getResources().getStringArray(R.array.flavors);
        for(int i=0; i<aFlavors.length; i++) {
            Log.d(DEBUG_TAG, "aFlavors["+ i + "] value is: "+ aFlavors[i]);
        }

        // SOME FORMAT STRING HANDLING
        // You can use htmlEncode to make sure your string is escaped properly. Of course, "Won" is fine as is... But if it had been "You've won!", you'd be glad you called this.
        String escapedWin = TextUtils.htmlEncode("Won");
        Log.d(DEBUG_TAG, "escapedWin value is: " + escapedWin);

        // First, the simple format string
        String mySimpleWinString = getResources().getString(R.string.simpleformatString);
        Log.d(DEBUG_TAG, "mySimpleWinString value is: " + mySimpleWinString);
        String resultsText1 = String.format(mySimpleWinString, 5, 5, escapedWin);
        Log.d(DEBUG_TAG, "resultsText1 (simple) value is: " + resultsText1);

        // Second, the styled format string
        String resultsTextFormat = getResources().getString(R.string.formatStringWithTwoNumbersAndAString);
        Log.d(DEBUG_TAG, "resultsTextFormat value is: " + resultsTextFormat);
        String resultsText = String.format(resultsTextFormat, 5, 5, escapedWin);
        Log.d(DEBUG_TAG, "resultsText value is: " + resultsText);
        CharSequence styledResults = Html.fromHtml(resultsText);
        Log.d(DEBUG_TAG, "styledResults value is: " + styledResults);

        // SOME WIDGET/VIEW/IMAGE HANDLING
        // Grab the ImageView and display the flag graphic on the main layout ImageView
        ImageView flagImageView = (ImageView)findViewById(R.id.ImageView01);
        flagImageView.setImageResource(R.drawable.flag);

        // Grab the Bitmap flag graphic and check out some stuff about it
        BitmapDrawable bitmapFlag = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.flag);
        if (bitmapFlag != null) {
            Log.d(DEBUG_TAG, "bitmapFlag is HEIGHT: " + bitmapFlag.getIntrinsicHeight() + " and WIDTH: " + bitmapFlag.getIntrinsicWidth());
        }

        // Grab a Nine-patch graphic and check out some stuff about it
        NinePatchDrawable stretchy = (NinePatchDrawable) ContextCompat.getDrawable(this, R.drawable.pyramid);
        if (stretchy != null) {
            Log.d(DEBUG_TAG, "stretchy is HEIGHT: " + stretchy.getIntrinsicHeight() + " and WIDTH: " + stretchy.getIntrinsicWidth());
        }

        // SOME ANIMATION HANDLING
        // Make the flag (png) spin using our spin.xml animation resource
        Animation an =  AnimationUtils.loadAnimation(this, R.anim.spin);
        flagImageView.startAnimation(an);

        // Make the red oval drawable shape resource pulse using our pulse.xml animation resource
        ImageView redOval = (ImageView)findViewById(R.id.ImageView02);
        Animation an2 =  AnimationUtils.loadAnimation(this, R.anim.pulse);
        redOval.startAnimation(an2);

        // SOME XML HANDLING
        XmlResourceParser myPets = getResources().getXml(R.xml.my_pets);

        try {
            inspectPetsXml(myPets);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "InspectPetsXml broke.", e);
        }

        // SOME RAW FILE HANDLING
        InputStream iFile = getResources().openRawResource(R.raw.limerick);
        try {
            String strFile = inputStreamToString(iFile);
            Log.d(DEBUG_TAG, "Here's the limerick from the Input Stream:\n"+ strFile);

        } catch (Exception e) {
            Log.e(DEBUG_TAG, "InputStreamToString broke.", e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.string.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Churns through a specific XML file, and prints all my pet elements and their attributes
    public static void inspectPetsXml(XmlResourceParser myPets) throws XmlPullParserException, IOException    {
        // Let's inspect my pets a little
        int eventType = -1;
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if(eventType == XmlResourceParser.START_DOCUMENT) {
                Log.d(DEBUG_TAG, "Document Start");
            } else if(eventType == XmlResourceParser.START_TAG) {

                // Get the name of the tag (eg pets or pet)
                String strName = myPets.getName();

                if(strName.equals("pet"))
                {
                    Log.d(DEBUG_TAG, "Found a PET");
                    Log.d(DEBUG_TAG, "Name: "+myPets.getAttributeValue(null, "name"));
                    Log.d(DEBUG_TAG, "Species: "+myPets.getAttributeValue(null, "type"));
                }
            }
            eventType = myPets.next();
        }

        Log.d(DEBUG_TAG, "Document End");
    }

    // Converts an input stream to a string so we can print it using logcat debug logging.
    public String inputStreamToString(InputStream is) throws IOException    {
        StringBuffer sBuffer = new StringBuffer();
        BufferedReader dataIO = new BufferedReader( new InputStreamReader(is));
        String strLine;

        while((strLine=dataIO.readLine()) != null){
            sBuffer.append(strLine);
            sBuffer.append("\n");
        }

        dataIO.close();
        is.close();

        return sBuffer.toString();
    }
}
