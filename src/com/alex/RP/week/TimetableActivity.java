package com.alex.rp.week;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import com.alex.rp.MyString;
import com.alex.rp.R;
import com.alex.rp.db.DB;
import com.alex.rp.db.Vars;
import com.alex.rp.group.Groups;
import com.alex.rp.lesson.LessonActivity;
import com.alex.rp.semester.Semester;
import com.alex.rp.semester.Semesters;
import com.alex.rp.subject.Subjects;
import com.alex.rp.tables.Table;
import com.alex.rp.tables.TimetableTable;

import java.util.Date;

/**
 * Created by alex on 23.03.14.
 */
public class TimetableActivity extends ActionBarActivity implements View.OnClickListener{

    private final static String LOG = "TimetableActivity";
    private MyString selectedGroup;
    private TableLayout tl;
    private Date date;
    private Table table;
    private boolean even;

    public void onCreate(Bundle savedInstanceState) {

        Log.d(LOG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);

        date = new Date();

        tl = (TableLayout) findViewById(R.id.tl_week);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        table = new TimetableTable(this, tl, date, even);

    }

    @Override
    protected void onRestart() {
        Log.d(LOG, "onRestart()");
        super.onRestart();
        table = new TimetableTable(this, tl, date, even);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:
                back();
                break;
            case R.id.action_next:
                next();
                break;
            case R.id.action_statistic:
                startSemester(2);
                break;
            case R.id.action_semester:
                startSemester(1);
                break;
            case R.id.action_group:
                startActivity(new Intent(this, Groups.class));
                break;
            case R.id.action_subject:
                startActivity(new Intent(this, Subjects.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSemester(int next){
        Intent intent = new Intent(this, Semesters.class);
        intent.putExtra("next", next);
        startActivity(intent);
    }

    private void back(){
        Log.d(LOG, "back");

        long dateTime = date.getTime() - 604800000;
        date = new Date(dateTime);
        table = new TimetableTable(this, tl, date, even);
        //((TimetableTable)table).setDate(date);
    }

    private void next(){
        Log.d(LOG, "next");

        long dateTime = date.getTime() + 604800000;
        date = new Date(dateTime);
        table = new TimetableTable(this, tl, date, even);
        //((TimetableTable)table).setDate(date);
    }

    private Semester getSemester(){

        DB db = new DB(this);
        Semester result = db.getSemester(date);
        db.close();

        if (result == null) {
            return null;
        }

        return result;
    }

    /**
     * Метод сразатывающий при клики на дне недели. Вызывает окно добавления замены.
     * @param view
     */
    @Override
    public void onClick(View view) {

        Semester semester = getSemester();

        if(semester == null){
            return;
        }
/*
        int week = semester.getWeek(date);
        this.even = (week > 0 && week % 2 == 0) ? true : false;*/
        Log.d(LOG, "selected day: " + view.getId());
        Replacement replacement = new Replacement(date, view.getId());
        replacement.setEven(even);

        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra(Vars.FIELD_REPLACEMENT, replacement);
        intent.putExtra(Vars.FIELD_SWITCH, Vars.TABLE_REPLACEMENT);
        startActivity(intent);

    }

}