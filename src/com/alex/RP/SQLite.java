package com.alex.RP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                + Vars.COLUMN_ID_PAIR + " integer not null,"
                + Vars.COLUMN_GROUP + " text not null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean insert(boolean even, int id, String group) {

        int eveni = (even) ? 1 : 0;
        boolean result = false;

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_EVEN, eveni);
        cv.put(Vars.COLUMN_ID_PAIR, id);
        cv.put(Vars.COLUMN_GROUP, group);

        try {

            if (db.insert(Vars.TABLE_WEEK, null, cv) != -1) {
                result = true;
            }


        } catch (Exception e) {

        } finally {

            db.close();
        }

        return result;
    }

    public boolean update(boolean even, int id, String group) {

        int eveni = (even) ? 1 : 0;
        int result = 0;

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_GROUP, group);

        try {

            result = db.update(Vars.TABLE_WEEK, cv, Vars.COLUMN_EVEN + " = " + eveni +
                    " and " + Vars.COLUMN_ID_PAIR + " = " + id, null);

        } catch (Exception e) {

        } finally {

            db.close();
        }

        return (result > 0) ? true : false;
    }

    public int getCount() {

        int count = 0;

        try {

            c = db.rawQuery("Select * from " + Vars.TABLE_WEEK, null);

            count = c.getCount();

        } catch (Exception e) {

        } finally {

            c.close();
        }

        return count;
    }

    public String getGroup(boolean even, int id) {

        String group = null;
        int eveni = (even) ? 1 : 0;

        try {

            c = db.query(Vars.TABLE_WEEK, new String[]{Vars.COLUMN_GROUP}, Vars.COLUMN_EVEN + " = " + eveni +
                    " and " + Vars.COLUMN_ID_PAIR + " = " + id, null, null, null, null);

            if (c.moveToFirst()) {

                group = c.getString(0);

            } else {
                Log.d(LOG, "getIdTag() 0 rows");
            }

        } catch (Exception e) {

        } finally {

            c.close();
        }


        return group;
    }
}
