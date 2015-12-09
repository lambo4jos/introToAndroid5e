package com.introtoandroid.simplescrolling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class SimpleScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_scrolling);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simple_scrolling, menu);
        menu.findItem(R.id.noscroll_menu_item)
                .setIntent(new Intent(this, NoScrollActivity.class));
        menu.findItem(R.id.verticalscroll_menu_item)
                .setIntent(new Intent(this, VerticalScrollActivity.class));
        menu.findItem(R.id.horizontalscroll_menu_item)
                .setIntent(new Intent(this, HorizontalScrollActivity.class));
        menu.findItem(R.id.bothscroll_menu_item)
                .setIntent(new Intent(this, BothScrollActivity.class));
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(item.getIntent());
        super.onOptionsItemSelected(item);
        return true;
    }

}
