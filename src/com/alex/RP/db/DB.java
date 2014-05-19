package com.alex.rp.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import com.alex.rp.group.Group;
import com.alex.rp.lesson.Lesson;
import com.alex.rp.semester.Semester;
import com.alex.rp.subject.Subject;
import com.alex.rp.week.Timetable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alex on 23.03.14.
 */
public class DB implements DBI {

    private static final String LOG = "DB";

    private SQLite sqLite;

    public DB(Context context) {
        this.sqLite = new SQLite(context);
    }

    //----------------------------------------------------------------- Add

    public void update(Semester semester){
        sqLite.updateTmp(semester);
    }

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

    @Override
    public boolean add(Semester semester) {

        Date start = semester.getStart();
        Date end = semester.getEnd();

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_START, start.getTime());
        cv.put(Vars.COLUMN_END, end.getTime());
        return sqLite.insert(Vars.TABLE_SEMESTER, cv);
    }

    @Override
    public boolean add(Timetable timetable) {
        Log.d(LOG, "add()");

        Lesson lesson = timetable.getLesson();

        int semesterId = timetable.getSemester().getId();
        boolean even = timetable.isEven();
        int day = timetable.getDay();
        int groupId = lesson.getGroup().getId();
        int subjectId = lesson.getSubject().getId();
        boolean lecture = lesson.isLecture();

        if (isExist(Vars.TABLE_TEMPLATE, Vars.COLUMN_DAY, new String[]{String.valueOf(day)})) {

            return sqLite.update(timetable);

        } else {

            ContentValues cv = new ContentValues();
            cv.put(Vars.COLUMN_SEMESTER, semesterId);
            cv.put(Vars.COLUMN_EVEN, even);
            cv.put(Vars.COLUMN_DAY, day);
            cv.put(Vars.COLUMN_GROUP, groupId);
            cv.put(Vars.COLUMN_SUBJECT, subjectId);
            cv.put(Vars.COLUMN_LECTURE, lecture);

            return sqLite.insert(Vars.TABLE_TEMPLATE, cv);
        }
    }

    private boolean isExist(String table, String selection, String[] values) {
        Log.d(LOG, "isExist");
        int count = sqLite.getCount(table, selection, values);
        return count > 0;
    }

    //--------------------------------------------------------------------------------- Get

    @Override
    public ArrayList<Group> getGroups() {
        return sqLite.getGroups();
    }

    @Override
    public ArrayList<Subject> getSubjects(){
        return sqLite.getSubjects();
    }

    @Override
    public ArrayList<Semester> getSemesters() {
        return sqLite.getSemesters();
    }

    @Override
    public ArrayList<Timetable> getTimetables() {
        return sqLite.getTimetables();
    }

    @Override
    public ArrayList<Timetable> getTimetables(Semester semester) {
        return sqLite.getTimetables(semester);
    }

    @Override
    public Semester getSemester(Date date) {
        return sqLite.getSemester(date);
    }


    //--------------------------------------------------------------------------------- Delete

    @Override
    public boolean delete(Group group) {

        return sqLite.delete(group) > 0;
    }

    @Override
    public boolean delete(Subject subject){
        return sqLite.delete(subject) > 0;
    }

    @Override
    public boolean delete(Semester semester) {
        return false;
    }

    @Override
    public boolean reset(Timetable timetable) {
        return false;
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
        ArrayList<String> arrayList4;
        ArrayList<String> arrayList5;
        ArrayList<String> arrayList6;

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_TEMPLATE + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_SEMESTER);
        arrayList2 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_EVEN);
        arrayList3 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_DAY);
        arrayList4 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_GROUP);
        arrayList5 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_SUBJECT);
        arrayList6 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_LECTURE);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i+1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i)
                    + "|" + arrayList3.get(i) + "|" + arrayList4.get(i) + "|" + arrayList5.get(i) + "|" + arrayList6.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_GROUP + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_NAME);
        arrayList2 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COLOR);
        arrayList3 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COMMERCE);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i+1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i) + "|" + arrayList3.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_SEMESTER + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_SEMESTER, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_SEMESTER, Vars.COLUMN_START);
        arrayList2 = sqLite.getColumn(Vars.TABLE_SEMESTER, Vars.COLUMN_END);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i+1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_SUBJECT + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_SUBJECT, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_SUBJECT, Vars.COLUMN_NAME);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i+1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i));
        }

    }

    public ArrayList<String> getColumn(String table, String column) {
        return sqLite.getColumn(table, column);
    }

/*
    @Override
    public Group getGroup(String name) {
        return sqLite.getGroup(name);
    }

    @Override
    public Subject getSubject(String name) {
        return sqLite.getSubject(name);
    }*/
}
