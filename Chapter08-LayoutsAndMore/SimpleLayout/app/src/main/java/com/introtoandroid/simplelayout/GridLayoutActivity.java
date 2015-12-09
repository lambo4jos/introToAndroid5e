package com.introtoandroid.simplelayout;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GridLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Grid Layout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}