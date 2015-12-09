package com.introtoandroid.simplemasterdetailflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class TopicListActivity extends AppCompatActivity
        implements TopicListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        if (findViewById(R.id.topic_detail_container) != null) {
            mTwoPane = true;
            ((TopicListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.topic_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(TopicDetailFragment.ARG_ITEM_ID, id);
            TopicDetailFragment fragment = new TopicDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.topic_detail_container, fragment)
                    .commit();
        } else {
            Intent detailIntent = new Intent(this, TopicDetailActivity.class);
            detailIntent.putExtra(TopicDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
