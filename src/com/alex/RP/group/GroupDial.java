package com.alex.rp.group;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    private RadioGroup rgColor;
    private RadioGroup rgCommerce;
    private FragmentActivity activity;
    private Group group;
    private Groups groups;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            activity = getActivity();
            groups = ((Groups) activity);
            group = groups.getSelectedGroup();
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.new_group, null);
            etNameGroup = (EditText) v.findViewById(R.id.et_name_group);
            rgColor = (RadioGroup) v.findViewById(R.id.rg_color);
            rgCommerce = (RadioGroup) v.findViewById(R.id.rg_commerce);

            builder.setView(v)
                    .setTitle("Информация")
                    .setNegativeButton("Удалить", this)
                    .setPositiveButton("Изменить", this)
                    .setNeutralButton("Отмена", this);

            init();

        } catch (Exception e) {
            Log.d(LOG, "!!!!!onCreteDialog " + e.toString());
        }

        return builder.create();
    }

    private void init() {

        etNameGroup.setText(group.getName());
        etNameGroup.setEnabled(false);

        for (int i = 0; i < rgColor.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) rgColor.getChildAt(i);
            if (radioButton.getCurrentTextColor() == group.getColor().getId()) {
                radioButton.setChecked(true);
            }
        }

        if (group.isCommerce()) {
            ((RadioButton) v.findViewById(R.id.rbCommerce)).setChecked(true);
        } else {
            ((RadioButton) v.findViewById(R.id.rbBudget)).setChecked(true);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i == Dialog.BUTTON_POSITIVE) {// Изменяем группу

            int idSelectedColor = rgColor.getCheckedRadioButtonId();
            RadioButton rbSelected = (RadioButton) v.findViewById(idSelectedColor);
            int color = rbSelected.getCurrentTextColor();

            int idSelectedCommerce = rgCommerce.getCheckedRadioButtonId();
            boolean commerce = (idSelectedCommerce == R.id.rbCommerce) ? true : false;

            Group newGroup = new Group(group.getName(), color, commerce);

            DB db = new DB(activity);
            db.add(newGroup);
            db.close();

            groups.update();

        } else if (i == Dialog.BUTTON_NEGATIVE) {// Удаляем группу

            DB db = new DB(activity);
            db.delete(group);
            db.close();

            groups.update();
        }
    }
}