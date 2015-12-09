package com.introtoandroid.simplelayout;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RelativeLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Relative Layout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
