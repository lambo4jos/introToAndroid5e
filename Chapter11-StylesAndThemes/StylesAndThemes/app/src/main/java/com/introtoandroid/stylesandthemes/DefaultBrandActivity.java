package com.introtoandroid.stylesandthemes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class DefaultBrandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styles_and_themes);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);

        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setSelection(editText.getText().length());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar bottomBar = (Toolbar) findViewById(R.id.bottom_bar);
        assert bottomBar != null;

        ImageButton mapButton = (ImageButton) bottomBar.findViewById(R.id.map_button);
        if (mapButton != null) {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Map Button", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ImageButton emailButton = (ImageButton) bottomBar.findViewById(R.id.email_button);
        if (emailButton != null) {
            emailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Email Button", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ImageButton infoButton = (ImageButton) bottomBar.findViewById(R.id.info_button);
        if (infoButton != null) {
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Info Button", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_styles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_green) {
            Intent intent = new Intent(this, GreenBrandActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_orange) {
            Intent intent = new Intent(this, OrangeBrandActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
