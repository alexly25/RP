package com.alex.rp.group;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.alex.rp.R;
import com.alex.rp.db.DB;

/**
 * Created by alex on 25.03.14.
 */
public class GroupDial extends DialogFragment implements DialogInterface.OnClickListener {

    private final static String LOG = "GroupDial";
    private View v;
    private EditText etNameGroup;
    private EditText rgColor;
    private RadioGroup rgCommerce;
    private FragmentActivity activity;
    private Group groupSelected;
    private Groups groups;

    public void show(Group groupSelected, android.support.v4.app.FragmentManager manager, java.lang.String tag){
        super.show(manager,null);
        this.groupSelected = groupSelected;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            activity = getActivity();
            groups = ((Groups) activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.group, null);
            etNameGroup = (EditText) v.findViewById(R.id.et_name_group);
            rgColor = (EditText) v.findViewById(R.id.et_color);
            rgCommerce = (RadioGroup) v.findViewById(R.id.rg_commerce);

            builder.setView(v)
                    .setTitle("Информация")
                    .setPositiveButton("Изменить", this)
                    .setNeutralButton("Отмена", this);

            init();

        } catch (Exception e) {
            Log.d(LOG, "!!!!!onCreteDialog " + e.toString());
        }

        return builder.create();
    }

    private void init() {

        etNameGroup.setText(groupSelected.getName());
        etNameGroup.setEnabled(false);

        rgColor.setText(groupSelected.getColor());
        //rgColor.setText("");

        if (groupSelected.isCommerce()) {
            ((RadioButton) v.findViewById(R.id.rbCommerce)).setChecked(true);
        } else {
            ((RadioButton) v.findViewById(R.id.rbBudget)).setChecked(true);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i == Dialog.BUTTON_POSITIVE) {// Изменяем группу

            int color = Integer.valueOf(rgColor.getText().toString());

            int idSelectedCommerce = rgCommerce.getCheckedRadioButtonId();
            boolean commerce = (idSelectedCommerce == R.id.rbCommerce) ? true : false;

            Group newGroup = new Group(groupSelected.getName(), color, commerce);

            DB db = new DB(activity);
            db.add(newGroup);
            db.close();

            groups.update();

        }
    }
}