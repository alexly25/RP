package com.alex.rp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.alex.rp.group.Group;
import com.alex.rp.lesson.Lesson;
import com.alex.rp.semester.Semester;
import com.alex.rp.subject.Subject;
import com.alex.rp.week.Replacement;
import com.alex.rp.week.Template;

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

    public void update(Semester semester) {
        sqLite.updateTmp(semester);
    }

    @Override
    public boolean add(Group group) {
        Log.d(LOG, "add()");

        String name = group.getName();
        int color = group.getColor();
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
        Date first = semester.getFirst();
        Date last = semester.getLastDay();

        Log.d(LOG,"start="+start.toString());
        Log.d(LOG,"end="+end.toString());
        Log.d(LOG,"first="+first.toString());
        Log.d(LOG,"last="+last.toString());
        Log.d(LOG,"start="+start.getTime());
        Log.d(LOG,"end="+end.getTime());
        Log.d(LOG,"first="+first.getTime());
        Log.d(LOG,"last="+last.getTime());

        ContentValues cv = new ContentValues();
        cv.put(Vars.COLUMN_START, first.getTime());
        cv.put(Vars.COLUMN_END, last.getTime());/*
        cv.put(Vars.COLUMN_FIRST, first.getTime());*/
        return sqLite.insert(Vars.TABLE_SEMESTER, cv);
    }

    @Override
    public boolean add(Template template) {
        Log.d(LOG, "add(Template template)");

        Lesson lesson = template.getLesson();

        int semesterId = template.getSemester().getId();
        boolean even = template.isEven();
        int day = template.getDay();
        int groupId = lesson.getGroup().getId();
        int subjectId = lesson.getSubject().getId();
        boolean lecture = lesson.isLecture();

        if (isExist(Vars.TABLE_TEMPLATE, Vars.COLUMN_DAY, new String[]{String.valueOf(day)})
                && isExist(Vars.TABLE_TEMPLATE, Vars.COLUMN_EVEN, new String[]{String.valueOf(even)})) {
            Log.d(LOG, "isExist");
            return sqLite.update(template);

        } else {
            Log.d(LOG, "else");
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

    @Override
    public boolean add(Replacement replacement){
        Log.d(LOG, "add(Replacement replacement)");

        Lesson lesson = replacement.getLesson();

        Date date = replacement.getDate();
        int day = replacement.getDay();
        int groupId = lesson.getGroup().getId();
        int subjectId = lesson.getSubject().getId();
        boolean lecture = lesson.isLecture();

        if (isExist(Vars.TABLE_REPLACEMENT, Vars.COLUMN_DAY, new String[]{String.valueOf(date.getTime())})) {
            Log.d(LOG, "isExist");
            return sqLite.update(replacement);

        } else {
            Log.d(LOG, "else");
            ContentValues cv = new ContentValues();
            cv.put(Vars.COLUMN_DATE, date.getTime());
            cv.put(Vars.COLUMN_DAY, day);
            cv.put(Vars.COLUMN_GROUP, groupId);
            cv.put(Vars.COLUMN_SUBJECT, subjectId);
            cv.put(Vars.COLUMN_LECTURE, lecture);

            return sqLite.insert(Vars.TABLE_REPLACEMENT, cv);
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

        Log.d(LOG, "getGroups");

        ArrayList<Group> result = new ArrayList<Group>();

        Cursor c = sqLite.getTable(Vars.TABLE_GROUP);

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

        c.close();

        return result;

    }

    @Override
    public ArrayList<Subject> getSubjects() {

        Log.d(LOG, "getSubjects");

        ArrayList<Subject> result = new ArrayList<Subject>();

        Cursor c = sqLite.getTable(Vars.TABLE_SUBJECT);

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

        c.close();

        return result;

    }

    @Override
    public ArrayList<Semester> getSemesters() {

        Log.d(LOG, "getSemesters");

        ArrayList<Semester> result = new ArrayList<Semester>();

        Cursor c = sqLite.getTable(Vars.TABLE_SEMESTER);

        if (c.moveToFirst()) {

            do {

                int id = c.getInt(0);
                Date start = new Date(c.getLong(1));
                Date end = new Date(c.getLong(2));
                //Date first = new Date(c.getLong(3));
                Semester semester = new Semester(id, start, end);
                result.add(semester);

            } while (c.moveToNext());

        } else {
            Log.d(LOG, "getSemesters() 0 rows");
        }

        c.close();

        return result;
    }

    @Override
    public ArrayList<Template> getTemplates() {

        Log.d(LOG, "getTemplates()");

        ArrayList<Template> result = new ArrayList<Template>();

        Cursor c = sqLite.getTable(Vars.TABLE_TEMPLATE);

        if (c.moveToFirst()) {

            do {

                int semesterId = c.getInt(1);
                boolean even = (c.getInt(2) == 1) ? true : false;
                int day = c.getInt(3);
                int groupId = c.getInt(4);
                int subjectId = c.getInt(5);
                boolean lecture = (c.getInt(6) == 1) ? true : false;

                Semester semester = sqLite.getSemester(semesterId);
                Group group = sqLite.getGroup(groupId);
                Subject subject = sqLite.getSubject(subjectId);

                Lesson lesson = new Lesson(group, subject, lecture);

                Template template = new Template(semester, even, day, lesson);
                result.add(template);

            } while (c.moveToNext());

        } else {
            Log.d(LOG, "getTemplates() 0 rows");
        }

        c.close();

        return result;

    }

    @Override
    public ArrayList<Template> getTemplates(Semester semester) {

        Log.d(LOG, "getTemplates(Semester semester)");

        int id = sqLite.getId(semester);

        if (id == -1) {
            return null;
        }

        ArrayList<Template> result = new ArrayList<Template>();

        Cursor c = sqLite.getTable(Vars.TABLE_TEMPLATE, Vars.COLUMN_SEMESTER + " = ?", new String[]{String.valueOf(id)}); // делаем запрос всех данных из таблицы tableName

        if (c.moveToFirst()) {

            do {

                boolean even = (c.getInt(2) == 1) ? true : false;
                int day = c.getInt(3);
                int groupId = c.getInt(4);
                int subjectId = c.getInt(5);
                boolean lecture = (c.getInt(6) == 1) ? true : false;

                Group group = sqLite.getGroup(groupId);
                Subject subject = sqLite.getSubject(subjectId);

                Lesson lesson = new Lesson(group, subject, lecture);

                Template template = new Template(semester, even, day, lesson);
                result.add(template);

            } while (c.moveToNext());

        } else {
            Log.d(LOG, "getTemplates() 0 rows");
        }

        c.close();

        return result;

    }

    @Override
    public ArrayList<Replacement> getReplacements(Semester semester) {
        Log.d(LOG, "getReplacements(Semester semester)");

        Date dateStart = semester.getStart();
        Date dateEnd = semester.getEnd();
        long start = dateStart.getTime();
        long end = dateEnd.getTime();

        ArrayList<Replacement> result = new ArrayList<Replacement>();

        Cursor c = sqLite.query("Select * from " + Vars.TABLE_REPLACEMENT +
                        " where " + Vars.COLUMN_DATE + " <= " + end +
                " and " + Vars.COLUMN_DATE + " >= " + start); //getTable(Vars.TABLE_REPLACEMENT, Vars.COLUMN_SEMESTER + " = ?", new String[]{String.valueOf(id)}); // делаем запрос всех данных из таблицы tableName

        if (c.moveToFirst()) {

            do {

                int id = c.getInt(0);
                Date date = new Date(c.getLong(1));
                int day = c.getInt(2);
                int groupId = c.getInt(3);
                int subjectId = c.getInt(4);
                boolean lecture = (c.getInt(5) == 1) ? true : false;

                Group group = sqLite.getGroup(groupId);
                Subject subject = sqLite.getSubject(subjectId);

                Lesson lesson = new Lesson(group, subject, lecture);

                Replacement replacement = new Replacement(id, date, day, lesson);
                result.add(replacement);

            } while (c.moveToNext());

        } else {
            Log.d(LOG, "getReplacements() 0 rows");
        }

        c.close();

        return result;
    }

    @Override
    public Replacement getReplacement(Date date) {

        //Log.d(LOG, "getReplacement() " + date.getTime());

        Replacement result = null;

        Cursor c = sqLite.getTable(Vars.TABLE_REPLACEMENT, Vars.COLUMN_DAY + " = ?", new String[]{String.valueOf(date.getTime())});

        if (c.moveToFirst()) {

            int id = c.getInt(0);
            int day = c.getInt(2);
            int groupId = c.getInt(3);
            int subjectId = c.getInt(4);
            boolean lecture = (c.getInt(5) == 1) ? true : false;

            Group group = sqLite.getGroup(groupId);
            Subject subject = sqLite.getSubject(subjectId);

            Lesson lesson = new Lesson(group, subject, lecture);

            result = new Replacement(id, date, day, lesson);

        } else {
            //Log.d(LOG, "getReplacement() 0 rows");
        }

        c.close();

        return result;
    }

    @Override
    public Semester getSemester(Date date) {
        return sqLite.getSemester(date);
    }

    @Override
    public int getHours(Semester semester, Group group, boolean lecture) {

        int result = 0;

        int semesterId = semester.getId();
        int groupId = group.getId();
        int intLecture = (lecture) ? 1 : 0;
        int countEven = sqLite.getHours(semesterId, groupId, intLecture, 1);
        int countNotEven = sqLite.getHours(semesterId, groupId, intLecture, 0);

        if (semester.getWeekCount() % 2 == 0) {
            result = (semester.getWeekCount() / 2) * (countEven + countNotEven);
        } else {
            result = (semester.getWeekCount() / 2) * (countEven + countNotEven) + countEven;
        }

        return result;
    }

    @Override
    public int getHours(Semester semester, boolean commerce) {

        int result = 0;

        int semesterId = semester.getId();// получаем id семестра
        int intCommerce = (commerce) ? 1 : 0;// переводим коммерцию(бюджет) в int

        int countEven = sqLite.getHours(semesterId, intCommerce, 1);// получаем количество четных часов в коммерческой(бюджетной) группе
        int countNotEven = sqLite.getHours(semesterId, intCommerce, 0);// получаем количество нечетных часов в коммерческой(бюджетной) группе

        if (semester.getWeekCount() % 2 == 0) {//если четное количество недель в семестре
            Log.d(LOG,"even");
            result = (semester.getWeekCount() / 2) * (countEven + countNotEven);
        } else {
            Log.d(LOG,"not even");
            result = (semester.getWeekCount() / 2) * (countEven + countNotEven) + countEven;
        }

        return result;
    }

    /*

    @Override
    public int getLecture(Semester semester, Group group) {
        return 0;
    }
*/

    /*

    @Override
    public String getStatistic(Semester semester, String key) {

        String result = null;

        if (key.equals(Vars.VALUE_ALL)) {

        } else if (key.equals(Vars.VALUE_BUDGET)) {

        } else if (key.equals(Vars.VALUE_NOT_BUDGET)) {

        } else if (key.equals(Vars.VALUE_OTHER)) {

        }

        return result;
    }
*/


    //--------------------------------------------------------------------------------- Delete

    @Override
    public boolean delete(Group group) {

        if (sqLite.delete(group) > 0) {
            sqLite.deleteTemplate(group);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Subject subject) {

        if (sqLite.delete(subject) > 0) {
            sqLite.deleteTemplate(subject);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(Semester semester) {
        return false;
    }

    @Override
    public boolean reset(Template template) {
        return false;
    }


    //-------------------------------------------------------------------------------- Other

    public void close() {
        sqLite.dbClose();
    }

    public void outTables() {

        ArrayList<String> arrayList;
        ArrayList<String> arrayList1;
        ArrayList<String> arrayList2;
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayList<String> arrayList5;
        ArrayList<String> arrayList6;

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_REPLACEMENT + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_REPLACEMENT, Vars.COLUMN_ID);
        arrayList2 = sqLite.getColumn(Vars.TABLE_REPLACEMENT, Vars.COLUMN_DATE);
        arrayList3 = sqLite.getColumn(Vars.TABLE_REPLACEMENT, Vars.COLUMN_DAY);
        arrayList4 = sqLite.getColumn(Vars.TABLE_REPLACEMENT, Vars.COLUMN_GROUP);
        arrayList5 = sqLite.getColumn(Vars.TABLE_REPLACEMENT, Vars.COLUMN_SUBJECT);
        arrayList6 = sqLite.getColumn(Vars.TABLE_REPLACEMENT, Vars.COLUMN_LECTURE);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i + 1) + ": " + arrayList.get(i) + "|" + arrayList2.get(i) + "|"
                    + "|" + arrayList3.get(i) + "|" + arrayList4.get(i) + "|" + arrayList5.get(i) + "|" + arrayList6.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_TEMPLATE + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_SEMESTER);
        arrayList2 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_EVEN);
        arrayList3 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_DAY);
        arrayList4 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_GROUP);
        arrayList5 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_SUBJECT);
        arrayList6 = sqLite.getColumn(Vars.TABLE_TEMPLATE, Vars.COLUMN_LECTURE);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i + 1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i)
                    + "|" + arrayList3.get(i) + "|" + arrayList4.get(i) + "|" + arrayList5.get(i) + "|" + arrayList6.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_GROUP + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_NAME);
        arrayList2 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COLOR);
        arrayList3 = sqLite.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COMMERCE);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i + 1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i) + "|" + arrayList3.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_SEMESTER + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_SEMESTER, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_SEMESTER, Vars.COLUMN_START);
        arrayList2 = sqLite.getColumn(Vars.TABLE_SEMESTER, Vars.COLUMN_END);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i + 1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i) + "|" + arrayList2.get(i));
        }

        Log.d(LOG, "outTables() ------------" + Vars.TABLE_SUBJECT + "---------------");
        arrayList = sqLite.getColumn(Vars.TABLE_SUBJECT, Vars.COLUMN_ID);
        arrayList1 = sqLite.getColumn(Vars.TABLE_SUBJECT, Vars.COLUMN_NAME);
        for (int i = 0; i < arrayList.size(); i++) {
            Log.d(LOG, "row " + (i + 1) + ": " + arrayList.get(i) + "|" + arrayList1.get(i));
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
