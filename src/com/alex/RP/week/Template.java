package com.alex.rp.week;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import com.alex.rp.MyString;
import com.alex.rp.R;
import com.alex.rp.group.GroupDialog;
import com.alex.rp.subject.SubjectDialog;
import com.alex.rp.tables.Table;
import com.alex.rp.tables.TemplateTable;

import java.util.Date;

/**
 * Created by alex on 08.05.2014.
 */
public class Template extends ActionBarActivity {

    private final static String LOG = "Template";
    private MyString selectedGroup;
    private TableLayout tl;
    private Date date;
    private Table table;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template);

        tl = (TableLayout) findViewById(R.id.tl_template);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        date = new Date();

        selectedGroup = new MyString("");
        table = new TemplateTable(this, tl, selectedGroup, date);
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
                //reset();
                break;
            case R.id.action_semester:
                break;
            case R.id.action_new_group:
                new GroupDialog().show(getSupportFragmentManager(), null);
                break;
            case R.id.action_new_subject:
                new SubjectDialog().show(getSupportFragmentManager(), null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}