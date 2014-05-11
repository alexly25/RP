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
import com.alex.rp.group.Groups;
import com.alex.rp.statistic.Statistic;
import com.alex.rp.subject.Subjects;
import com.alex.rp.tables.Table;
import com.alex.rp.tables.WeekTable;
import com.alex.rp.week.Template;

import java.util.Date;

/**
 * Created by alex on 23.03.14.
 */
public class Timetable extends ActionBarActivity {

    private final static String LOG = "Timetable";
    private MyString selectedGroup;
    private TableLayout tl;
    private Date date;
    private Table table;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);

        date = new Date();

        tl = (TableLayout) findViewById(R.id.tl_week);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        selectedGroup = new MyString("");
        table = new WeekTable(this, tl, selectedGroup, date);


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
                startActivity(new Intent(this, Statistic.class));
                break;
            case R.id.action_template:
                startActivity(new Intent(this, Template.class));
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

    private void back(){
        Log.d(LOG, "back");

        long dateTime = date.getTime() - 604800000;
        date = new Date(dateTime);
        ((WeekTable)table).setDate(date);
    }

    private void next(){
        Log.d(LOG, "next");

        long dateTime = date.getTime() + 604800000;
        date = new Date(dateTime);
        ((WeekTable)table).setDate(date);
    }

/*
    @Override
    public void onClick(View view) {

        long dateTime;

        switch (view.getId()) {
            case R.id.btn_new_group:
                new GroupDial().show(getSupportFragmentManager(), null);
                break;
        }


    }*/

    public void setSelectedGroup(String group) {
        selectedGroup.setString(group);

        Log.d(LOG, "selected group: " + selectedGroup.getString());
    }
}