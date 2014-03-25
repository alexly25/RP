package com.alex.RP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alex on 23.03.14.
 */
public class SQLite extends SQLiteOpenHelper {

    private static final String LOG = "SQLite";
    private Context context = null;
    SQLiteDatabase db = null;
    Cursor c = null;

    public SQLite(Context context) {
        super(context, "Days", null, 1);
        this.context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG, "onCreate() create tables");

        db.execSQL("create table " + Vars.TABLE_WEEK + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_EVEN + " boolean not null,"
                + Vars.COLUMN_ID_PAIR + " integer not null unique,"
                + Vars.COLUMN_GROUP + " text not null,"
                + "FOREIGN KEY (" + Vars.COLUMN_GROUP + ") REFERENCES " + Vars.TABLE_GROUP + "(" + Vars.COLUMN_GROUP + ")"
                + ");");

        db.execSQL("create table " + Vars.TABLE_GROUP + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_GROUP + " text not null unique,"
                + Vars.COLUMN_COLOR + " integer not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean insert(String table, ContentValues cv) {

        Log.d(LOG, "insert");

        boolean result = false;

        try {

            if (db.insert(table, null, cv) != -1) {
                Log.d(LOG, "true");
                result = true;
            }


        } catch (Exception e) {
            Log.d(LOG, "insert " + e.toString());
        }

        return result;
    }

    public boolean update(boolean even, int id, String group) {

        Log.d(LOG, "update");

        int eveni = (even) ? 1 : 0;
        int result = 0;

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_GROUP, group);

        try {

            result = db.update(Vars.TABLE_WEEK, cv, Vars.COLUMN_EVEN + " = " + eveni +
                    " and " + Vars.COLUMN_ID_PAIR + " = " + id, null);

        } catch (Exception e) {

        }

        return (result > 0) ? true : false;
    }

    public boolean update(String group, int color) {
        Log.d(LOG, "update");

        int result = 0;

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_COLOR, color);

        try {

            result = db.update(Vars.TABLE_GROUP, cv, Vars.COLUMN_GROUP+ " = ?", new String[]{group});

        } catch (Exception e) {

        }

        return (result > 0) ? true : false;
    }

    public int getCount(String table, String selection, String[] values) {

        Log.d(LOG, "getCount");

        int count = 0;

        try {

            c = db.query(table, null, selection + " = ?", values, null, null, null);

            count = c.getCount();

        } catch (Exception e) {

        } finally {

            c.close();
        }

        return count;
    }

    public String getGroup(boolean even, int id) {

        //Log.d(LOG, "get");

        String group = null;
        int eveni = (even) ? 1 : 0;

        try {

            c = db.query(Vars.TABLE_WEEK, new String[]{Vars.COLUMN_GROUP}, Vars.COLUMN_EVEN + " = " + eveni +
                    " and " + Vars.COLUMN_ID_PAIR + " = " + id, null, null, null, null);

            if (c.moveToFirst()) {

                group = c.getString(0);

            } else {
                //Log.d(LOG, "group " + id + " is note: " + group);
            }

        } catch (Exception e) {

        } finally {

            c.close();
        }


        return group;
    }

    public ArrayList<String> getColumn(String table, String column) {
        //Log.d(LOG, "getColumn");

        ArrayList<String> arrayList = new ArrayList<String>();

        try {

            c = db.query(table, new String[]{column}, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    arrayList.add(c.getString(0));

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getColumn() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getColumn() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }
        return arrayList;
    }

    public void dbClose() {
        db.close();
    }
}
