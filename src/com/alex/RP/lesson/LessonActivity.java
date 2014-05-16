package com.alex.rp.lesson;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.alex.rp.R;
import com.alex.rp.db.Color;
import com.alex.rp.db.DB;
import com.alex.rp.db.Vars;
import com.alex.rp.group.Group;
import com.alex.rp.subject.Subject;
import com.alex.rp.week.Timetable;

import java.util.ArrayList;

/**
 * Created by alex on 04.05.2014.
 */
public class LessonActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private final static String LOG = "LessonActivity";
    private ListView lvGroup;
    private ListView lvSubject;
    private RadioGroup rgType;
    private Timetable timetable;
    private DB db;
    private ArrayList arrayList;
    private ArrayList<Group> groups;
    private ArrayList<Subject> subjects;
    private ArrayAdapter<String> adGroups;
    private ArrayAdapter<String> adSubject;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson);

        timetable = (Timetable) getIntent().getSerializableExtra(Vars.FIELD_TIMETABLE);
        rgType = (RadioGroup) findViewById(R.id.rg_lesson_type);
        lvGroup = (ListView) findViewById(R.id.lv_groups);
        lvSubject = (ListView) findViewById(R.id.lv_subject);
        lvGroup.setOnItemClickListener(this);
        lvSubject.setOnItemClickListener(this);
        lvGroup.setOnScrollListener(this);
        lvSubject.setOnScrollListener(this);

        db = new DB(this);
        initGroups();
        initSubject();
        db.close();
    }

    private void initGroups() {
        arrayList = new ArrayList<String>();
        groups = db.getGroups();

        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            String s = group.getName();
            arrayList.add(s);
        }


        adGroups = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lvGroup.setAdapter(adGroups);
    }

    private void initSubject() {
        arrayList = new ArrayList<String>();
        subjects = db.getSubjects();

        for (int i = 0; i < subjects.size(); i++) {
            String name = subjects.get(i).getName();
            arrayList.add(name);
        }


        adSubject = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        lvSubject.setAdapter(adSubject);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.accept, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_accept:
                addTimetable();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTimetable() {
        //Log.d(LOG, )
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        resetSelected(adapterView);
        view.setSelected(true);

        resetColor(adapterView);
        TextView textView = (TextView) view;
        textView.setBackgroundColor(android.graphics.Color.BLUE);
/*
        resetColor(adapterView);
        //Log.d(LOG,"adapterView.getAdapter().getClass() " + ((ListView)adapterView).get);
        Log.d(LOG,"get " + ((TextView)adapterView.getChildAt(0)).getText());

        TextView textView = (TextView) view;
        textView.setBackgroundColor(android.graphics.Color.BLUE);*/
    }

    private void resetSelected(AdapterView<?> adapterView) {

        for (int i = 0; i < adapterView.getChildCount(); i++) {
            TextView textView = (TextView) adapterView.getChildAt(i);
            textView.setBackgroundColor(android.graphics.Color.WHITE);
        }
    }

    private void resetColor(AdapterView<?> adapterView) {

        for (int i = 0; i < adapterView.getChildCount(); i++) {
            TextView textView = (TextView) adapterView.getChildAt(i);
            if (!textView.isSelected()) {
                textView.setBackgroundColor(android.graphics.Color.WHITE);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        //Log.d(LOG, "scrool2");
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        resetColor(absListView);
    }
}