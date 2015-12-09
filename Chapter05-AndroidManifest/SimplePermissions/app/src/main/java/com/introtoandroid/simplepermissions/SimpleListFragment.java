package com.introtoandroid.simplepermissions;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class SimpleListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    public static final String DEBUG_TAG = "SimpleListFragment";

    public SimpleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] requestedColumns = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };
        CursorLoader loader = new CursorLoader(getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                requestedColumns, null, null, null);
        Cursor contacts = loader.loadInBackground();

        ListAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.contact_list_simple,
                contacts,
                new String[] {
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                },
                new int[] {
                        R.id.contact_item_simple_text
                }, 0);

        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

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
        Log.d(DEBUG_TAG, "Cursor pos: " +
                phone.getPosition() + "== list pos: " + position);
        Log.d(DEBUG_TAG, "Cursor id: " +
                phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone._ID)) +
                "== list id: " + id);
    }
}
