package com.introtoandroid.advancedlayouts;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;


public class SimpleListFragment extends ListFragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String DEBUG_TAG = "SimpleListFragment";

    public SimpleListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simple_list, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Advanced Layouts");
        toolbar.setTitleTextColor(Color.WHITE);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.v(DEBUG_TAG, "onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public static <T> void initLoader(final int loaderId, final Bundle args, final LoaderManager.LoaderCallbacks<T> callbacks,
                                      final LoaderManager loaderManager) {
        final Loader<T> loader = loaderManager.getLoader(loaderId);
        if (loader != null && loader.isReset()) {
            loaderManager.restartLoader(loaderId, args, callbacks);
        } else {
            loaderManager.initLoader(loaderId, args, callbacks);
        }
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
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                },
                new int[]{
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
        Log.v(DEBUG_TAG, "Cursor pos: " +
                phone.getPosition() + "== list pos: " + position);
        Log.v(DEBUG_TAG, "Cursor id: " +
                phone.getString(phone.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone._ID)) +
                "== list id: " + id);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
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
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                },
                new int[]{
                        R.id.contact_item_simple_text
                }, 0);

        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);
        Log.v(DEBUG_TAG, "First contact loaded: ");

        initLoader(loader.getId(), bundle, this, getLoaderManager());

//        loader.startLoading();
//        getLoaderManager().getLoader(loader.getId()).startLoading();
        return loader;
    }


    /**
     * Dislays either the name of the first contact or a message.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.v(DEBUG_TAG, "First contact loaded: ");


        if (cursor != null) {
            final int totalCount = cursor.getCount();
            if (totalCount > 0) {
                cursor.moveToFirst();
                String name = cursor
                        .getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                mMessageText.setText(
//                        getResources().getString(R.string.contacts_string, totalCount, name));

                ListAdapter adapter = new SimpleCursorAdapter(getActivity(),
                        R.layout.contact_list_simple,
                        cursor,
                        new String[]{
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                        },
                        new int[]{
                                R.id.contact_item_simple_text
                        }, 0);

                setListAdapter(adapter);

                getListView().setOnItemClickListener(this);
                Log.v(DEBUG_TAG, "First contact loaded: ");
                Log.v(DEBUG_TAG, "Total number of contacts: " + totalCount);
            } else {
                Log.v(DEBUG_TAG, "List of contacts is empty.");
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Implement
        loader.forceLoad();
    }
}
