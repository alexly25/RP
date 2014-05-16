package com.alex.rp.group;

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

import java.util.ArrayList;

/**
 * Created by alex on 24.03.14.
 */
public class Groups extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private final static String LOG = "Groups";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> alGroups;
    private ListView lvGroup;
    private ArrayList<Group> groups;
    private Group selectedGroup;

    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        lvGroup = (ListView) findViewById(R.id.list);
        lvGroup.setOnItemClickListener(this);

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
                newGroup();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void newGroup() {
        new GroupDialog().show(getSupportFragmentManager(), null);
    }

    public void update() {
        Log.d(LOG, "update");
        super.onRestart();

        alGroups = new ArrayList<String>();
        DB db = new DB(this);
        groups = db.getGroups();
        db.close();

        for (int i = 0; i < groups.size(); i++) {
            Group group = groups.get(i);
            String s = group.getName() + " " + group.getColor().getName() + " " + group.isCommerce();
            alGroups.add(s);
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alGroups);
        lvGroup.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedGroup = groups.get(i);
        new GroupDial().show(getSupportFragmentManager(), null);
        //Log.d(LOG, group.getName());
    }

    protected Group getSelectedGroup() {
        return selectedGroup;
    }
}