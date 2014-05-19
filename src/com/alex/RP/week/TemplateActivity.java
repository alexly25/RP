package com.alex.rp.week;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import com.alex.rp.MyString;
import com.alex.rp.R;
import com.alex.rp.db.Vars;
import com.alex.rp.group.GroupDialog;
import com.alex.rp.lesson.LessonActivity;
import com.alex.rp.semester.Semester;
import com.alex.rp.subject.SubjectDialog;
import com.alex.rp.tables.Table;
import com.alex.rp.tables.TemplateTable;

/**
 * Created by alex on 08.05.2014.
 */
public class TemplateActivity extends ActionBarActivity implements View.OnClickListener{

    private final static String LOG = "TemplateActivity";
    private MyString selectedGroup;
    private TableLayout tl;
    private Table table;
    private Semester semester;
    private boolean even;

    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template);

        tl = (TableLayout) findViewById(R.id.tl_template);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        semester = (Semester) getIntent().getSerializableExtra(Vars.FIELD_SEMESTER);
        even = false;

        table = new TemplateTable(this, tl, semester, even);
    }

    @Override
    protected void onRestart() {
        Log.d(LOG, "onRestart()");
        super.onRestart();
        table = new TemplateTable(this, tl, semester, even);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.template, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_reset:
                reset();
                break;
/*            case R.id.action_template:
                startActivity(new Intent(this, Semesters.class));
                break;*/
            case R.id.action_new_group:
                new GroupDialog().show(getSupportFragmentManager(), null);
                break;
            case R.id.action_new_subject:
                new SubjectDialog().show(getSupportFragmentManager(), null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void reset() {
        even = !even;

        table = new TemplateTable(this, tl, semester, even);
    }


    @Override
    public void onClick(View view) {
        Timetable timetable = new Timetable(semester);
        timetable.setDay(view.getId());
        timetable.setEven(even);

        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra(Vars.FIELD_TIMETABLE, timetable);
        startActivity(intent);
    }
}