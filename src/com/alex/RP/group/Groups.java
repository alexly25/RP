package com.alex.rp.group;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.alex.rp.R;
import com.alex.rp.db.DB;

import java.util.ArrayList;

/**
 * Created by alex on 24.03.14.
 */
public class Groups extends ActionBarActivity {

    private final static String LOG = "Groups";
    private GroupAdapter groupAdapter;
    private ListView lvGroup;
    private ArrayList<Group> groups;

    public void onCreate(Bundle savedInstanceState) {
        //Log.d(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        lvGroup = (ListView) findViewById(R.id.list);

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
            case R.id.action_delete:
                deleteGroup();
                break;
            case R.id.action_edit:
                editGroup();
                break;
            case R.id.action_new:
                newGroup();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteGroup() {
        DB db = new DB(this);

        for(int i=0; i<groupAdapter.getCount(); i++){
            if(groupAdapter.isChecked(i)) {
                Group group = groups.get(i);
                db.delete(group);
            }
        }

        db.close();

        update();
    }

    private void editGroup() {

        int count = 0;
        Group group = null;

        for(int i=0; i<groupAdapter.getCount(); i++){

            if(groupAdapter.isChecked(i)){
                count++;
                group = groupAdapter.getItem(i);
            }
        }

        if(count != 1){
            return;
        }

        new GroupDial().show(group, getSupportFragmentManager(), null);
    }

    private void newGroup() {
        new GroupDialog().show(getSupportFragmentManager(), null);
    }

    public void update() {

        super.onRestart();

        DB db = new DB(this);
        groups = db.getGroups();
        db.close();

        groupAdapter = new GroupAdapter(this, groups);

        lvGroup.setAdapter(groupAdapter);
    }

}