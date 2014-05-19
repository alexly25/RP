package com.alex.rp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.DatePicker;
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

        db.execSQL("create table " + Vars.TABLE_TEMPLATE + " ("
                + Vars.COLUMN_ID + " integer not null primary key autoincrement,"
                + Vars.COLUMN_SEMESTER + " integer not null,"
                + Vars.COLUMN_EVEN + " boolean not null,"
                + Vars.COLUMN_DAY + " integer not null unique,"
                + Vars.COLUMN_GROUP + " integer not null,"
                + Vars.COLUMN_SUBJECT + " integer not null,"
                + Vars.COLUMN_LECTURE + " boolean not null,"
                + "FOREIGN KEY (" + Vars.COLUMN_SEMESTER + ") REFERENCES " + Vars.TABLE_SEMESTER + "(" + Vars.COLUMN_ID + ")"
                + "FOREIGN KEY (" + Vars.COLUMN_GROUP + ") REFERENCES " + Vars.TABLE_GROUP + "(" + Vars.COLUMN_ID + ")"
                + "FOREIGN KEY (" + Vars.COLUMN_SUBJECT + ") REFERENCES " + Vars.TABLE_SUBJECT + "(" + Vars.COLUMN_ID + ")"
                + ");");

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

    public int delete(Group group) {
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

    public int delete(Subject subject) {
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

    public boolean updateTmp(Semester semester) {
        Log.d(LOG, "update");

        Date start = semester.getStart();
        Date end = semester.getEnd();

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_START, start.getTime());
        cv.put(Vars.COLUMN_END, end.getTime());

        int result = 0;

        try {

            result = db.update(Vars.TABLE_SEMESTER, cv, Vars.COLUMN_ID + " = ?", new String[]{"1"});

        } catch (Exception e) {

        }

        return (result > 0) ? true : false;
    }

    public boolean update(Timetable timetable) {
        Log.d(LOG, "update");

        Lesson lesson = timetable.getLesson();

        int semesterId = timetable.getSemester().getId();
        boolean even = timetable.isEven();
        int day = timetable.getDay();
        int groupId = lesson.getGroup().getId();
        int subjectId = lesson.getSubject().getId();
        boolean lecture = lesson.isLecture();

        int result = 0;

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_SEMESTER, semesterId);
        cv.put(Vars.COLUMN_EVEN, even);
        cv.put(Vars.COLUMN_GROUP, groupId);
        cv.put(Vars.COLUMN_SUBJECT, subjectId);
        cv.put(Vars.COLUMN_LECTURE, lecture);

        try {

            result = db.update(Vars.TABLE_TEMPLATE, cv, Vars.COLUMN_DAY + " = ?", new String[]{String.valueOf(day)});

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

                    int id = c.getInt(0);
                    String name = c.getString(1);
                    int color = c.getInt(2);
                    boolean commerce = (c.getInt(3) == 1) ? true : false;
                    Group group = new Group(id, name, color, commerce);
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

    public ArrayList<Subject> getSubjects() {
        Log.d(LOG, "getSubjects");

        ArrayList<Subject> result = new ArrayList<Subject>();

        try {

            c = db.query(Vars.TABLE_SUBJECT, null, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    int id = c.getInt(0);
                    String name = c.getString(1);
                    Subject subject = new Subject(id, name);
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

    public ArrayList<Semester> getSemesters() {
        Log.d(LOG, "getSemesters");

        ArrayList<Semester> result = new ArrayList<Semester>();

        try {

            c = db.query(Vars.TABLE_SEMESTER, null, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    int id = c.getInt(0);
                    Date start = new Date(c.getLong(1));
                    Date end = new Date(c.getLong(2));
                    Semester semester = new Semester(id, start, end);
                    result.add(semester);

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getSemesters() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getSemesters() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    public ArrayList<Timetable> getTimetables() {

        Log.d(LOG, "getTimetables()");

        ArrayList<Timetable> result = new ArrayList<Timetable>();

        try {

            c = db.query(Vars.TABLE_TEMPLATE, null, null, null, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    int semesterId = c.getInt(1);
                    boolean even = (c.getInt(2) == 1) ? true : false;
                    int day = c.getInt(3);
                    int groupId = c.getInt(4);
                    int subjectId = c.getInt(5);
                    boolean lecture = (c.getInt(6) == 1) ? true : false;

                    Semester semester = getSemester(semesterId);
                    Group group = getGroup(groupId);
                    Subject subject = getSubject(subjectId);

                    Lesson lesson = new Lesson(group, subject, lecture);

                    Timetable timetable = new Timetable(semester, even, day, lesson);
                    result.add(timetable);

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getTimetables() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getTimetables() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    public ArrayList<Timetable> getTimetables(Semester semester) {

        Log.d(LOG, "getTimetables()");

        ArrayList<Timetable> result = new ArrayList<Timetable>();

        try {

            int id = getId(semester);

            if (id == -1) {
                return null;
            }

            c = db.query(Vars.TABLE_TEMPLATE, null, Vars.COLUMN_SEMESTER + " = ?", new String[]{String.valueOf(id)}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.moveToFirst()) {

                do {

                    boolean even = (c.getInt(2) == 1) ? true : false;
                    int day = c.getInt(3);
                    int groupId = c.getInt(4);
                    int subjectId = c.getInt(5);
                    boolean lecture = (c.getInt(6) == 1) ? true : false;

                    Group group = getGroup(groupId);
                    Subject subject = getSubject(subjectId);

                    Lesson lesson = new Lesson(group, subject, lecture);

                    Timetable timetable = new Timetable(semester, even, day, lesson);
                    result.add(timetable);

                } while (c.moveToNext());

            } else {
                Log.d(LOG, "getTimetables() 0 rows");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getTimetables() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    private int getId(Semester semester) {

        int result = -1;
        Cursor c = null;

        try {

            Date start = semester.getStart();

            c = db.query(Vars.TABLE_SEMESTER, null, Vars.COLUMN_START + " = ?", new String[]{String.valueOf(start.getTime())}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.getCount() == 1) {
                c.moveToFirst();
                result = c.getInt(0);

            } else {
                Log.d(LOG, "getId() != 1");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getId() catch error: " + e.toString());
            return -1;

        } finally {
            c.close();
        }

        return result;
    }

    private Subject getSubject(int id) {

        Subject result = null;
        Cursor c = null;

        try {

            c = db.query(Vars.TABLE_SUBJECT, null, Vars.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.getCount() == 1) {
                c.moveToFirst();
                result = new Subject(id, c.getString(1));

            } else {
                Log.d(LOG, "getSubject() != 1");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getSubject() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    private Group getGroup(int id) {

        Group result = null;
        Cursor c = null;

        try {

            c = db.query(Vars.TABLE_GROUP, null, Vars.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.getCount() == 1) {
                c.moveToFirst();
                String name = c.getString(1);
                int color = c.getInt(2);
                boolean commerce = (c.getInt(3) == 1) ? true : false;
                result = new Group(id, name, color, commerce);

            } else {
                Log.d(LOG, "getGroup() != 1");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getGroup() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    private Semester getSemester(int id) {

        Semester result = null;
        Cursor c = null;

        try {

            c = db.query(Vars.TABLE_SEMESTER, null, Vars.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.getCount() == 1) {
                Log.d(LOG, "count = " + c.getCount());
                c.moveToFirst();
                Date start = new Date(c.getLong(1));
                Date end = new Date(c.getLong(2));

                result = new Semester(id, start, end);

            } else {
                Log.d(LOG, "getSemester() != 1");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getSemester() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return result;
    }

    public Semester getSemester(Date date) {
        Semester result = null;

        long time = date.getTime();
        Log.d(LOG, "time = " + time);

        try {

            c = db.rawQuery("Select * from " + Vars.TABLE_SEMESTER +
                    " where " + Vars.COLUMN_START + " <= " + time +
                    " and " + Vars.COLUMN_END + " >= " + time, null);

            if (c.getCount() == 1) {
                c.moveToFirst();

                int id = c.getInt(0);
                Date start = new Date(c.getLong(1));
                Date end = new Date(c.getLong(2));

                result = new Semester(id, start, end);

            } else {
                Log.d(LOG, "getSemester() != 1; count = " + c.getCount());
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




    /*
    public Group getGroup(String name) {
        Group group = null;

        try {

            c = db.query(Vars.TABLE_GROUP, null, Vars.COLUMN_NAME + " = ?", new String[]{name}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.getCount() == 1) {

                    int color = c.getInt(2);
                    boolean commerce = (c.getInt(3) == 1) ? true : false;
                    group = new Group(name, color, commerce);

            } else {
                Log.d(LOG, "getGroup() != 1");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getGroup() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return group;
    }

    public Subject getSubject(String name) {
        Subject subject = null;

        try {

            c = db.query(Vars.TABLE_SUBJECT, null, Vars.COLUMN_NAME + " = ?", new String[]{name}, null, null, null); // делаем запрос всех данных из таблицы tableName

            if (c.getCount() == 1) {

                subject = new Subject(c.getString(1));

            } else {
                Log.d(LOG, "getSubject() != 1");
            }

        } catch (Exception e) {

            Log.d(LOG, "!!!!!getSubject() catch error: " + e.toString());
            return null;

        } finally {
            c.close();
        }

        return subject;
    }*/

}
