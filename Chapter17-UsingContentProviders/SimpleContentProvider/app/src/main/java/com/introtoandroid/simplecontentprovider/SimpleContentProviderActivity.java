package com.introtoandroid.simplecontentprovider;

public class SimpleContentProviderActivity extends MenuActivity {
    public static final String DEBUG_TAG = "SimpleContentProvider";

    @Override
    void prepareMenu() {
        addMenuItem("1. MediaStore", SimpleMediaStore.class);
        addMenuItem("2. CallLog", SimpleCallLog.class);
    }
}
