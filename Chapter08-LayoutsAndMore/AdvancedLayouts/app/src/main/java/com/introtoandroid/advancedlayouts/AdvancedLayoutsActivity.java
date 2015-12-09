package com.introtoandroid.advancedlayouts;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.SortedMap;
import java.util.TreeMap;

public class AdvancedLayoutsActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "AdvancedLayoutsActivity";

    private SortedMap<String, Object> actions = new TreeMap<>();

    void prepareMenu() {
        addMenuItem("1. Basic Layout", BasicLayoutActivity.class);
        addMenuItem("2. List Layout", MyListActivity.class);
        addMenuItem("3. GridView", GridLayoutActivity.class);
        addMenuItem("4. Adapters", AdaptersActivity.class);
        addMenuItem("5. Styles", StyleSamplesActivity.class);
        addMenuItem("6. Grid, List (Fragment)", GridListMenuActivity.class);
        addMenuItem("7. Dialog", DialogActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        prepareMenu();

        String[] keys = actions.keySet().toArray(
                new String[actions.keySet().size()]);

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keys);

        ListView av = (ListView) findViewById(R.id.menu_list);
        av.setAdapter(adapter);
        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                String key = (String) parent.getItemAtPosition(position);
                startActivity((Intent) actions.get(key));
            }
        });
    }

    public void addMenuItem(String label, Class<?> cls) {
        actions.put(label, new Intent(this, cls));
    }
}
