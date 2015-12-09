package com.introtoandroid.viewsamples;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TextDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_display);
        TextView text = (TextView) findViewById(R.id.TextView02);
        registerForContextMenu(text);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (((TextView) v).getLinksClickable()) {
            menu.add("Disable Clickability");
        } else {
            menu.add("Enable Clickability");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        TextView text = (TextView) findViewById(R.id.TextView02);
        if (text.getLinksClickable()) {
            //text.setLinksClickable(false);
            text.setMovementMethod(null);
        } else {
            text.setLinksClickable(true);
            text.setMovementMethod(new android.text.method.LinkMovementMethod());
        }
        return true;
    }
}