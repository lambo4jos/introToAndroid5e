package com.introtoandroid.samelayout;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProgrammaticLayoutActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView text1 = new TextView(this);
        text1.setText(R.string.string1);

        TextView text2 = new TextView(this);
        text2.setText(R.string.string2);
        text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
        
        /*
         * Convert DP to PX
         * Our DP padding setting from our dimen resource
         * is in DP but setPadding requires pixels
         */
        int pixelDimen = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics());

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(pixelDimen, pixelDimen,
                pixelDimen, pixelDimen);
        ll.addView(text1);
        ll.addView(text2);

        setContentView(ll);
    }

}