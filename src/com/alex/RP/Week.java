package com.alex.RP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

/**
 * Created by alex on 23.03.14.
 */
public class Week extends FragmentActivity implements View.OnClickListener{

    private final static String LOG = "Week";
    private MyString selectedGroup;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);

        TableLayout tl = (TableLayout) findViewById(R.id.tl);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        Button btnSelectGroup = (Button) findViewById(R.id.select_group);
        btnSelectGroup.setOnClickListener(this);

        selectedGroup = new MyString("");
        new Table(this,tl,selectedGroup);

        registerForContextMenu(tl);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.m_i1:
                Log.d(LOG, "click m_i1");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()){
            case R.id.m_group:
                startActivity(new Intent(this,Groups.class));
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }


    @Override
    public void onClick(View view) {
        new GroupDialog().show(getSupportFragmentManager(),null);
    }

    protected void setSelectedGroup(String group){
        selectedGroup.setString(group);
        Log.d(LOG,"selected group: " + selectedGroup.getString());
    }
}