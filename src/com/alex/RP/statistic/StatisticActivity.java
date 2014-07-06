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
import android.widget.TextView;
import com.alex.rp.R;
import com.alex.rp.db.DB;
import com.alex.rp.db.Vars;
import com.alex.rp.group.Group;
import com.alex.rp.semester.Semester;

import java.util.ArrayList;

/**
 * Created by alex on 08.05.2014.
 */
public class StatisticActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private final static String LOG = "StatisticActivity";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> alGroups;
    private ListView lvGroup;
    private ArrayList<Group> groups;
    private Group editGroup;
    private Semester semester;
    private ArrayList<Group> alGroupStatistics;
    private TextView tvAll;
    private TextView tvBudget;
    private TextView tvVb;
    private TextView tvOther;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic);

        tvAll = (TextView)findViewById(R.id.tv_all);
        tvBudget = (TextView)findViewById(R.id.tv_budget);
        tvVb = (TextView)findViewById(R.id.tv_vb);
        tvOther = (TextView)findViewById(R.id.tv_other);

        lvGroup = (ListView) findViewById(R.id.list_group);
        lvGroup.setOnItemClickListener(this);

        semester = (Semester) getIntent().getSerializableExtra(Vars.FIELD_SEMESTER);

        update();
        setStatistics();
    }

    private void setStatistics() {

        DB db = new DB(this);

        for(Group group : alGroupStatistics){

            int practice = db.getHours(semester, group, false);
            group.setPractice(practice);

            int lecture = db.getHours(semester, group, true);
            group.setLecture(lecture);

            Log.d(LOG, "hours: " + group.getLecture() + " " + group.getPractice());
        }

        int budget = db.getHours(semester, false);
        int commerce = db.getHours(semester, true);
        int sum = budget + commerce;

        tvAll.setText("Всего: " + (sum + 12));
        tvBudget.setText("Бюджет: " + budget);
        tvVb.setText("В/б: " + commerce);
        tvOther.setText("Другие: 12");

        db.close();
    }

    public void update() {
        Log.d(LOG, "update");
        super.onRestart();

        alGroups = new ArrayList<String>();
        alGroupStatistics = new ArrayList<Group>();

        DB db = new DB(this);
        groups = db.getGroups();
        db.close();

        for (Group group : groups) {
            alGroupStatistics.add(group);
            alGroups.add(group.getName());
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alGroups);
        lvGroup.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.statistic, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                add();
                break;
            case R.id.action_deduct:
                deduct();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void add() {
        new OtherDialog().show("Добавить часы", getSupportFragmentManager());
    }

    private void deduct() {
        new OtherDialog().show("Вычесть часы", getSupportFragmentManager());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Group group = groups.get(i);

        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra(Vars.FIELD_SEMESTER, semester);
        intent.putExtra(Vars.FIELD_GROUP, group);
        startActivity(intent);
    }
}