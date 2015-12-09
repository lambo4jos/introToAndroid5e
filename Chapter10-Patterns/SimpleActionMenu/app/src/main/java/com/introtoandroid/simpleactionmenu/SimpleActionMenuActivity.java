package com.introtoandroid.simpleactionmenu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SimpleActionMenuActivity extends AppCompatActivity {
    private Boolean isHidden = false;
    private Button hideActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_action_menu);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;

        hideActionBar = (Button) findViewById(R.id.hide);
        hideActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHidden) {
                    ab.hide();
                    hideActionBar.setText(R.string.show_action_bar);
                } else {
                    ab.show();
                    hideActionBar.setText(R.string.hide_action_bar);
                }
                isHidden = !isHidden;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast.makeText(this, "Add Clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_close:
                finish();
                return true;
            case R.id.menu_help:
                Toast.makeText(this, "Help Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
