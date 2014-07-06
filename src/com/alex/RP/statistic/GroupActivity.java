package com.alex.rp.statistic;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import com.alex.rp.MyString;
import com.alex.rp.R;
import com.alex.rp.db.Vars;
import com.alex.rp.group.Group;
import com.alex.rp.semester.Semester;
import com.alex.rp.tables.GroupTable;
import com.alex.rp.tables.Table;

import java.util.Date;

/**
 * Created by alex on 08.06.2014.
 */
public class GroupActivity extends ActionBarActivity {

    private final static String LOG = "GroupActivity";
    private TableLayout tl;
    private Date date;
    private Table table;
    private Semester semester;
    private Group group;

    public void onCreate(Bundle savedInstanceState) {

        Log.d(LOG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);

        semester = (Semester) getIntent().getSerializableExtra(Vars.FIELD_SEMESTER);
        group = (Group) getIntent().getSerializableExtra(Vars.FIELD_GROUP);

        tl = (TableLayout) findViewById(R.id.tl_week);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        table = new GroupTable(this, tl, semester);

    }

}
