package com.alex.RP;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 25.03.14.
 */
public class GroupDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    private final static String LOG = "GroupDialog";
    private View v;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    ArrayList<String> alGroups;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.list_group, null);

            listView = (ListView) v.findViewById(R.id.list_group);
            listView.setOnItemClickListener(this);

            BD bd = new BD(getActivity());
            alGroups = bd.getColumn(Vars.TABLE_GROUP, Vars.COLUMN_GROUP);
            bd.dbClose();

            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, alGroups);
            listView.setAdapter(arrayAdapter);

            builder.setView(v)
                    .setTitle("Выберите группу")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

        } catch (Exception e) {
            Log.d(LOG, "!!!!!onCreteDialog " + e.toString());
        }

        return builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView tvSelectTag = (TextView) view.findViewById(android.R.id.text1);
        String selectedGroup = tvSelectTag.getText().toString();

        Week mainActivity = (Week) getActivity();
        mainActivity.setSelectedGroup(selectedGroup);

        getDialog().dismiss();

    }
}