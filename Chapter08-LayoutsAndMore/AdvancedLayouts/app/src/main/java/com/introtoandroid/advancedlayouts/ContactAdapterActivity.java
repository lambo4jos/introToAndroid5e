package com.introtoandroid.advancedlayouts;


import android.support.v4.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ContactAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] requestedColumns = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };
        CursorLoader loader = new CursorLoader(this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                requestedColumns, null, null, null);
        Cursor contacts = loader.loadInBackground();

        setContentView(R.layout.contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.contact_item_simple,
                contacts,
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                },
                new int[]{
                        R.id.contact_item_simple_text
                }, 0);

        ListView av = (ListView) findViewById(R.id.contact_list_view);
        av.setAdapter(adapter);

        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent,
                    View view, int position, long id) {
                Cursor phone = (Cursor) parent.getItemAtPosition(position);

                TextView tv = ((TextView) view);
                String name = phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));

                String displayed = tv.getText().toString();
                if (displayed.compareTo(name) == 0) {
                    tv.setText(num);
                } else {
                    tv.setText(name);
                }
                Log.d(AdvancedLayoutsActivity.DEBUG_TAG, "Cursor pos: " +
                        phone.getPosition() + "== list pos: " + position);
                Log.d(AdvancedLayoutsActivity.DEBUG_TAG, "Cursor id: " +
                        phone.getString(phone.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone._ID)) +
                        "== list id: " + id);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}