package com.introtoandroid.simplepermissions;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;


public class SimpleGridFragment extends Fragment {
    public SimpleGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_grid, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CursorLoader loader = new CursorLoader(getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        Cursor contacts = loader.loadInBackground();
        ListAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.contact_grid_simple,
                contacts,
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                }, new int[]{
                R.id.scratch_text1, R.id.scratch_text2
        }, 0);

        int view_id = R.id.scratch_adapter_view;
        GridView av = (GridView) getActivity().findViewById(view_id);
        av.setAdapter(adapter);
        av.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Toast.makeText(getActivity(),
                        "Clicked _id=" + id, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
