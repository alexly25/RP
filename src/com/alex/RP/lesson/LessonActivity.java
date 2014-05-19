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
    private Group selectedGroup;
    private Subject selectedSubject;


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

        Lesson lesson = getLesson();

        if(lesson != null){
            timetable.setLesson(lesson);

            db = new DB(this);
            if (db.add(timetable)){
                db.close();
                finish();
            }
            db.close();
        }
    }

    private Lesson getLesson(){

        if (selectedGroup == null) {
            Toast.makeText(this, "Выберите группу", Toast.LENGTH_LONG).show();
            return null;
        }

        if (selectedSubject == null) {
            Toast.makeText(this, "Выберите предмет", Toast.LENGTH_LONG).show();
            return null;
        }

        int idSelectedType = rgType.getCheckedRadioButtonId();
        boolean lecture = (idSelectedType == R.id.rb_lecture) ? true : false;

        return new Lesson(selectedGroup, selectedSubject, lecture);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String name = (String) ((TextView) view).getText();

        if (lvGroup == (ListView) adapterView) {
            selectedGroup = getGroup(name);
        } else if (lvSubject == (ListView) adapterView) {
            selectedSubject = getSubject(name);
        }

        resetColor(adapterView);
        resetSelected(adapterView);
    }

    private void resetColor(AdapterView<?> adapterView) {

        for (int i = 0; i < adapterView.getChildCount(); i++) {
            TextView textView = (TextView) adapterView.getChildAt(i);
            textView.setBackgroundColor(android.graphics.Color.WHITE);

        }
    }

    private void resetSelected(AdapterView<?> adapterView) {

        String name = null;

        if (lvGroup == (ListView) adapterView && selectedGroup != null) {
            name = selectedGroup.getName();
        } else if (lvSubject == (ListView) adapterView && selectedSubject != null) {
            name = selectedSubject.getName();
        }

        for (int i = 0; i < adapterView.getChildCount(); i++) {
            TextView textView = (TextView) adapterView.getChildAt(i);
            if (textView.getText().equals(name)) {
                textView.setBackgroundColor(android.graphics.Color.BLUE);
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
        resetSelected(absListView);
    }

    private Group getGroup(String name) {

        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            if (group.getName().equals(name)) {
                return group;
            }
        }

        return null;
    }

    private Subject getSubject(String name) {

        for (int i = 0; i < subjects.size(); i++) {
            Subject subject = subjects.get(i);
            if (subject.getName().equals(name)) {
                return subject;
            }
        }

        return null;
    }
}