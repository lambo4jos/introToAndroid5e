package com.introtoandroid.simplemasterdetailflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.introtoandroid.simplemasterdetailflow.news.NewsContent;


public class TopicDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";
    private NewsContent.NewsItem mItem;

    public TopicDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = NewsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.topic_detail)).setText(mItem.content);
        }

        return rootView;
    }
}
