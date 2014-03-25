package com.alex.RP;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alex on 23.03.14.
 */
public class BD implements BDRP {

    private static final String LOG = "BD";

    private SQLite sqLite;

    public BD(Context context) {
        this.sqLite = new SQLite(context);
    }

    @Override
    public boolean add(boolean even, int id, String group) {
        Log.d(LOG, "add()");

        boolean result = false;

        if (get(even, id) != null) {
            result = sqLite.update(even, id, group);
        } else {
            int eveni = (even) ? 1 : 0;

            ContentValues cv = new ContentValues();
            cv.put(Vars.COLUMN_EVEN, eveni);Log.d(LOG, "ev " + eveni);
            cv.put(Vars.COLUMN_ID_PAIR, id);Log.d(LOG, "id " + id);
            cv.put(Vars.COLUMN_GROUP, group);Log.d(LOG, "gr " + group);

            result = sqLite.insert(Vars.TABLE_WEEK, cv);
        }

        return result;
    }

    @Override
    public boolean add(String group, int color) {
        Log.d(LOG, "add()");

        if (group == null || group.equals("")) {
            return false;
        }

        if (isExist(Vars.TABLE_GROUP, Vars.COLUMN_GROUP, new String[]{group})) {

            return sqLite.update(group, color);

        } else {

            ContentValues cv = new ContentValues();
            cv.put(Vars.COLUMN_GROUP, group);
            cv.put(Vars.COLUMN_COLOR, color);
            return sqLite.insert(Vars.TABLE_GROUP, cv);
        }

    }

    private boolean isExist(String table, String selection, String[] values) {

        int count = sqLite.getCount(table, selection, values);
        return count > 0;
    }

    @Override
    public String get(boolean even, int id) {
        return sqLite.getGroup(even, id);
    }

    public void dbClose() {
        sqLite.dbClose();
    }

    public void outTables(){
        Log.d(LOG, "outTables() ------------" + Vars.TABLE_WEEK + "---------------");
        ArrayList<String> arrayList = sqLite.getColumn(Vars.TABLE_WEEK, Vars.COLUMN_ID);
        ArrayList<String> arrayList1 = sqLite.getColumn(Vars.TABLE_WEEK, Vars.COLUMN_EVEN);
        ArrayList<String> arrayList2 = sqLite.getColumn(Vars.TABLE_WEEK, Vars.COLUMN_ID_PAIR);
        ArrayList<String> arrayList3 = sqLite.getColumn(Vars.TABLE_WEEK, Vars.COLUMN_GROUP);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "outTables() " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i) + "|" + arrayList3.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_GROUP + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_GROUP);
        arrayList2 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COLOR);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "outTables() " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i));
        }

    }

    public ArrayList<String> getColumn(String table, String column) {
        return sqLite.getColumn(table, column);
    }

}
