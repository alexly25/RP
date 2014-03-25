package com.alex.RP;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by alex on 24.03.14.
 */
public class Groups extends FragmentActivity implements AdapterView.OnItemLongClickListener {

    private final static String LOG = "Groups";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> alGroups;
    private ListView lvGroup;

    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        Button btnNewGroup = (Button) findViewById(R.id.btn_new_group);
        btnNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewGroup().show(getSupportFragmentManager(), null);
            }
        });

        lvGroup = (ListView) findViewById(R.id.lv_groups);
        lvGroup.setOnItemLongClickListener(this);

        restart();

    }

    public void restart(){
        Log.d(LOG, "restart");
        super.onRestart();

        BD bd = new BD(this);
        alGroups = new ArrayList<String>();
        ArrayList<String> alG = bd.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_GROUP);
        ArrayList<String> alC = bd.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_COLOR);
        bd.dbClose();

        for (int i = 0; i < alG.size(); i++) {
            String s = alG.get(i) + " " + toColor(Integer.valueOf(alC.get(i)));
            alGroups.add(s);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alGroups);
        lvGroup.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        return false;
    }

    private String toColor(int id){
        String color = null;

        switch (id){
            case Color.BLUE:
                color = "Синий";
                break;
            case Color.GREEN:
                color = "Зеленый";
                break;
            case Color.RED:
                color = "Красный";
                break;
        }

        return color;
    }
}