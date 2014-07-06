package com.alex.rp.statistic;

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
import com.alex.rp.semester.Semester;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by alex on 06.06.2014.
 */
public class Statistics extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private final static String LOG = "Semesters";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> alSemesters;
    private ListView lvSemester;
    private ArrayList<Semester> semesters;


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

    public void update() {
        Log.d(LOG, "update");
        super.onRestart();

        alSemesters = new ArrayList<String>();

        DB db = new DB(this);
        semesters = db.getSemesters();
        db.close();

        for (Semester semester : semesters) {

            StringBuilder stringBuilder = getInterval(semester);
            alSemesters.add(stringBuilder.toString());
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alSemesters);
        lvSemester.setAdapter(arrayAdapter);
    }

    private StringBuilder getInterval(Semester semester) {

        StringBuilder result = new StringBuilder();
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(semester.getStart());
        result.append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(".")
                .append(calendar.get(Calendar.MONTH) + 1)
                .append(".")
                .append(calendar.get(Calendar.YEAR))
                .append(" - ");
        //Log.d(LOG, "" + calendar.getTime().toString());

        calendar.setTime(semester.getEnd());
        result.append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(".")
                .append(calendar.get(Calendar.MONTH) + 1)
                .append(".")
                .append(calendar.get(Calendar.YEAR));

        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Semester semester = semesters.get(i);

        Intent intent = new Intent(this, StatisticActivity.class);
        intent.putExtra(Vars.FIELD_SEMESTER, semester);
        startActivity(intent);
    }

}
