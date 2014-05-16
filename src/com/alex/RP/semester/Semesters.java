package com.alex.rp.semester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.alex.rp.R;
import com.alex.rp.db.DB;
import com.alex.rp.db.Vars;
import com.alex.rp.week.TemplateActivity;

import java.util.ArrayList;

/**
 * Created by alex on 11.05.2014.
 */
public class Semesters extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private final static String LOG = "Semesters";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> alSemesters;
    private ListView lvSemester;
    private ArrayList<Semester> semesters;
    private Semester selectedSemester;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        lvSemester = (ListView) findViewById(R.id.list);
        lvSemester.setOnItemClickListener(this);

        update();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_new:
                newSubject();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void newSubject() {
        startActivity(new Intent(this, SemesterActivity.class));
    }

    public void update() {
        Log.d(LOG, "update");
        super.onRestart();

        alSemesters = new ArrayList<String>();
        DB db = new DB(this);
        semesters = db.getSemesters();
        db.close();

        for (int i = 0; i < semesters.size(); i++) {
            String name = semesters.get(i).getStart().toString() + " " + semesters.get(i).getEnd().toString();
            alSemesters.add(name);
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alSemesters);
        lvSemester.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Semester semester = semesters.get(i);

        Intent intent = new Intent(this, TemplateActivity.class);
        intent.putExtra(Vars.FIELD_SEMESTER, semester);
        startActivity(intent);
    }

    public Semester getSelectedSemester() {
        return selectedSemester;
    }
}