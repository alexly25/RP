package com.alex.rp.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import com.alex.rp.group.Group;
import com.alex.rp.subject.Subject;

import java.util.ArrayList;

/**
 * Created by alex on 23.03.14.
 */
public class DB implements DBI {

    private static final String LOG = "DB";

    private SQLite sqLite;

    public DB(Context context) {
        this.sqLite = new SQLite(context);
    }

    //----------------------------------------------------------------- Group

    @Override
    public boolean add(Group group) {
        Log.d(LOG, "add()");

        String name = group.getName();
        int color = group.getColor().getId();
        boolean commerce = group.isCommerce();

        if (name == null || name.equals("")) {
            return false;
        }

        if (isExist(Vars.TABLE_GROUP, Vars.COLUMN_NAME, new String[]{name})) {

            return sqLite.update(group);

        } else {

            ContentValues cv = new ContentValues();
            cv.put(Vars.COLUMN_NAME, name);
            cv.put(Vars.COLUMN_COLOR, color);
            cv.put(Vars.COLUMN_COMMERCE, commerce);
            return sqLite.insert(Vars.TABLE_GROUP, cv);
        }

    }

    @Override
    public boolean delete(Group group) {

        return sqLite.delete(group) > 0;
    }

    @Override
    public ArrayList<Group> getGroups() {
        return sqLite.getGroups();
    }


    private boolean isExist(String table, String selection, String[] values) {
        Log.d(LOG, "isExist");
        int count = sqLite.getCount(table, selection, values);
        return count > 0;
    }
/*

    @Override
    public String get(boolean even, int id) {
        return sqLite.getGroup(even, id);
    }
*/

    //--------------------------------------------------------------------------------- Subject

    @Override
    public boolean add(Subject subject) {
        Log.d(LOG, "add()");

        String name = subject.getName();

        if (name == null || name.equals("")) {
            return false;
        }

        if (isExist(Vars.TABLE_SUBJECT, Vars.COLUMN_NAME, new String[]{name})) {

            return false;
            //return sqLite.update(subject);

        } else {

            ContentValues cv = new ContentValues();
            cv.put(Vars.COLUMN_NAME, name);
            return sqLite.insert(Vars.TABLE_SUBJECT, cv);
        }

    }

    public ArrayList<Subject> getSubject(){
        return sqLite.getSubject();
    }

    public boolean delete(Subject subject){
        return sqLite.delete(subject) > 0;
    }

    //-------------------------------------------------------------------------------- Other

    public void close() {
        sqLite.dbClose();
    }

    public void outTables(){

        ArrayList<String> arrayList;
        ArrayList<String> arrayList1;
        ArrayList<String> arrayList2;
        ArrayList<String> arrayList3;
/*

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_TEMPLATE + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_EVEN);
        arrayList2 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_ID_PAIR);
        arrayList3 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_GROUP);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "outTables() " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i) + "|" + arrayList3.get(i));
        }
*/

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_GROUP + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_NAME);
        arrayList2 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COLOR);
        arrayList3 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COMMERCE);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "outTables() " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i) + "|" + arrayList3.get(i));
        }

    }

    public ArrayList<String> getColumn(String table, String column) {
        return sqLite.getColumn(table, column);
    }

}
