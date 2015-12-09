package com.introtoandroid.viewsamples;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class ViewSamplesActivity extends AppCompatActivity {
    protected Map<String, Object> actions = new HashMap<>();

    public final static String debugTag = "ViewSamples";

    void prepareMenu() {
        addMenuItem("Forms", FormsActivity.class);
        addMenuItem("Indicators", IndicatorsActivity.class);
        addMenuItem("Containers", ContainersActivity.class);
        addMenuItem("Text Display", TextDisplayActivity.class);
        addMenuItem("Events", EventsActivity.class);
        addMenuItem("Video", VideoViewActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        prepareMenu();
        String[] keys = actions.keySet().toArray(new String[actions.keySet().size()]);

        ListView av = (ListView) findViewById(R.id.menu_list);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keys);

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
