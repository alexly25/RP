package com.alex.RP;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

/**
 * Created by alex on 24.03.14.
 */
public class NewGroup extends DialogFragment implements DialogInterface.OnClickListener {

    private final static String LOG = "NewGroup";
    private View v;
    private EditText etNameGroup;
    private RadioGroup radioGroup;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.new_group, null);
            etNameGroup = (EditText) v.findViewById(R.id.editText);
            radioGroup = (RadioGroup) v.findViewById(R.id.radio_group);

            final ScrollView scrollView = (ScrollView) v.findViewById(R.id.scrollView);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, 30);
                }
            });

            builder.setView(v)
                    .setTitle("Новая группа")
                    .setPositiveButton("OK", this)
                    .setNegativeButton("Отмена", this);

        } catch (Exception e) {
            Log.d(LOG, "!!!!!onCreteDialog " + e.toString());
        }

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        String group = etNameGroup.getText().toString();

        if (i == Dialog.BUTTON_POSITIVE && !group.equals("")) {

            BD bd = new BD(getActivity());

            int idSelectedColor = radioGroup.getCheckedRadioButtonId();
            RadioButton rbSelected = (RadioButton) v.findViewById(idSelectedColor);
            int color = rbSelected.getCurrentTextColor();

            bd.add(group, color);

            bd.dbClose();

            Groups groups = (Groups) getActivity();
            groups.restart();

        }
    }
}