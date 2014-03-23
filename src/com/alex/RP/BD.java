package com.alex.RP;

import android.content.Context;
import android.util.Log;

/**
 * Created by alex on 23.03.14.
 */
public class BD implements BDRP {

    private static final String LOG = "RP";

    //SQLiteDatabase db = null;
    //Cursor c = null;

    private SQLite sqLite;
    Context context = null;

    public BD(Context context/*SQLite sqLite*/) {
        this.sqLite = new SQLite(context);
        //this.context = context;
    }

    private boolean isEmpty() {
        int count = new SQLite(context).getCount();
        if (count == 42) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addGroup(boolean even, int id, String group) {
        Log.d(LOG, "addGroup()");

        boolean result = false;

        if (getGroup(even, id) != null) {
            result = sqLite.update(even, id, group);
        } else {
            result = sqLite.insert(even, id, group);
        }

        return result;
    }

    @Override
    public String getGroup(boolean even, int id) {
        return sqLite.getGroup(even, id);
    }


}
