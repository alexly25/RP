package com.alex.rp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.alex.rp.group.Group;
import com.alex.rp.semester.Semester;
import com.alex.rp.subject.Subject;

import java.util.ArrayList;
import java.util.Date;

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
/*

        db.execSQL("create table " + Vars.TABLE_TEMPLATE + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_EVEN + " boolean not null,"
                + Vars.COLUMN_ID_PAIR + " integer not null unique,"
                + Vars.COLUMN_GROUP + " text not null,"
                + "FOREIGN KEY (" + Vars.COLUMN_GROUP + ") REFERENCES " + Vars.TABLE_GROUP + "(" + Vars.COLUMN_GROUP + ")"
                + ");");
*/

        db.execSQL("create table " + Vars.TABLE_GROUP + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_NAME + " text not null unique,"
                + Vars.COLUMN_COLOR + " int not null,"
                + Vars.COLUMN_COMMERCE + " boolean not null);");

        db.execSQL("create table " + Vars.TABLE_SUBJECT + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_NAME + " text not null unique);");

        db.execSQL("create table " + Vars.TABLE_SEMESTER + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_START + " date not null unique,"
                + Vars.COLUMN_END + " date not null unique);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //------------------------------------------------------------------------------- Group

    public int delete(Group group){
        Log.d(LOG, "delete");
        int deleted = 0;

        String name = group.getName();

        try {

            deleted = db.delete(Vars.TABLE_GROUP, Vars.COLUMN_NAME + " = ?", new String[]{name});

        } catch (Exception e) {

            Log.d(LOG, "!!!!!deleteNote() catch error: " + e.toString());

        }

        return deleted;
    }

    public int delete(Subject subject){
        Log.d(LOG, "delete");
        int deleted = 0;

        String name = subject.getName();

        try {

            deleted = db.delete(Vars.TABLE_SUBJECT, Vars.COLUMN_NAME + " = ?", new String[]{name});

        } catch (Exception e) {

            Log.d(LOG, "!!!!!deleteNote() catch error: " + e.toString());

        }

        return deleted;
    }

    public boolean update(Group group) {
        Log.d(LOG, "update");

        String name = group.getName();
        int color = group.getColor().getId();
        boolean commerce = group.isCommerce();

        int result = 0;

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_COLOR, color);
        cv.put(Vars.COLUMN_COMMERCE, commerce);

        try {

            result = db.update(Vars.TABLE_GROUP, cv, Vars.COLUMN_NAME + " = ?", new String[]{name});

        } catch (Exception e) {

        }

        return (result > 0) ? true : false;
    }

    public ArrayList<Group> getGroups() {
        Log.d(LOG, "getGroups");

        ArrayList<Group> result = new ArrayList<Group>();

        try {

            c = db.query(Vars.TABLE_GROUP, null, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    //boolean commerce = (c.getInt(3) == 1) ? true : false;
                    //Log.d(LOG, c.getString(1)+" "+ c.getInt(2)+" "+ commerce);
                    String name = c.getString(1);
                    int color = c.getInt(2);
                    boolean commerce = (c.getInt(3) == 1) ? true : false;
                    Group group = new Group(name, color, commerce);
                    result.add(group);

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getGroups() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getGroups() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    public ArrayList<Subject> getSubject() {
        Log.d(LOG, "getSubjects");

        ArrayList<Subject> result = new ArrayList<Subject>();

        try {

            c = db.query(Vars.TABLE_SUBJECT, null, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    String name = c.getString(1);
                    Subject subject = new Subject(name);
                    result.add(subject);

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getSubjects() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getSubjects() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    public ArrayList<Semester> getSemester(){
        Log.d(LOG, "getSemester");

        ArrayList<Semester> result = new ArrayList<Semester>();

        try {

            c = db.query(Vars.TABLE_SEMESTER, null, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    Date start = new Date(c.getLong(1));
                    Date end = new Date(c.getLong(2));
                    Semester semester = new Semester(start,end);
                    result.add(semester);

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getSemester() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getSemester() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    //-------------------------------------------------------------------------------- Other

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

    public boolean insert(String table, ContentValues cv) {

        Log.d(LOG, "insert");

        boolean result = false;

        try {

            if (db.insert(table, null, cv) != -1) {
                Log.d(LOG, "inserted");
                result = true;
            }


        } catch (Exception e) {
            Log.d(LOG, "insert error: " + e.toString());
        }

        return result;
    }

    public void dbClose() {
        db.close();
    }
}
