package com.alex.rp.group;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.alex.rp.R;/*
import com.alex.rp.db.Color;*/
import com.alex.rp.db.DB;

/**
 * Created by alex on 24.03.14.
 */
public class GroupDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private final static String LOG = "GroupDialog";
    private View v;
    private EditText etNameGroup;
    private EditText etColor;
    private RadioGroup rgCommerce;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.group, null);
            etNameGroup = (EditText) v.findViewById(R.id.et_name_group);
            etColor = (EditText) v.findViewById(R.id.et_color);
            rgCommerce = (RadioGroup) v.findViewById(R.id.rg_commerce);

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

        String nameGroup = etNameGroup.getText().toString();

        if (i == Dialog.BUTTON_POSITIVE && !nameGroup.equals("")) {

            String sColor = etColor.getText().toString();
            int color = Color.parseColor("#" + sColor);

            int idSelectedCommerce = rgCommerce.getCheckedRadioButtonId();
            boolean commerce = (idSelectedCommerce == R.id.rbCommerce) ? true : false;

            Group group = new Group(nameGroup, color, commerce);

            DB db = new DB(getActivity());
            db.add(group);
            db.close();

            if(getActivity().getClass() == Groups.class){
                Groups groups = (Groups) getActivity();
                groups.update();
            }
        }
    }
}