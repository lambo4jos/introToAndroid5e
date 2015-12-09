package com.introtoandroid.simplescrolling;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BothScrollActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Both Scroll");
        setContentView(R.layout.allscroll);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}