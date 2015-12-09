package com.introtoandroid.advancedlayouts;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MyListActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] items = {"Basic Layout", "List Layout", "Grid View"};

        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.textview, items);

        ListView av = (ListView) findViewById(R.id.menu_list);
        av.setAdapter(adapter);
        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Log.d(AdvancedLayoutsActivity.DEBUG_TAG, "pos: " + position + " , id: " + id);
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),
                                BasicLayoutActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        TextView tv = (TextView) view;
                        tv.setText("Changed");
                        break;
                    case 2:
                        String original = (String) parent
                                .getItemAtPosition(position);
                        Log.d(AdvancedLayoutsActivity.DEBUG_TAG, "original string: "
                                + original);
                        ((TextView) view).setText("Updated");
                        break;
                }
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