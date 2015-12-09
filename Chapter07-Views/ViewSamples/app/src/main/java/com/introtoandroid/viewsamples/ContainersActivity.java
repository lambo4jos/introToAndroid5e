package com.introtoandroid.viewsamples;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.Toast;

public class ContainersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.containers);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button switch_button = (Button) findViewById(R.id.flip_button);
        final ImageSwitcher switcher = (ImageSwitcher) findViewById(R.id.img_switch);

        switch_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ContainersActivity.this, "Switching", Toast.LENGTH_SHORT).show();
                switcher.showNext();
            }
        });
    }
}