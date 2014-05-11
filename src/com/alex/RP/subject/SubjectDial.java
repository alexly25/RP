package com.alex.rp.subject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.alex.rp.R;
import com.alex.rp.db.DB;
import com.alex.rp.group.Group;
import com.alex.rp.group.Groups;

/**
 * Created by alex on 25.03.14.
 */
public class SubjectDial extends DialogFragment implements DialogInterface.OnClickListener {

    private final static String LOG = "GroupDial";
    private View v;
    private FragmentActivity activity;
    private Subject subject;
    private Subjects subjects;
    private TextView tvSubject;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            activity = getActivity();
            subjects = ((Subjects) activity);
            subject = subjects.getSelectedSubject();
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.info_subject, null);
            tvSubject = (TextView) v.findViewById(R.id.tv_name_subject);

            builder.setView(v)
                    .setTitle("Удаление")
                    .setNegativeButton("Удалить", this)
                    .setNeutralButton("Отмена", this);

            tvSubject.setText("Удалить \"" + subject.getName() + "\"?");

        } catch (Exception e) {
            Log.d(LOG, "!!!!!onCreteDialog " + e.toString());
        }

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i == Dialog.BUTTON_NEGATIVE) {// Удаляем группу

            DB db = new DB(activity);
            db.delete(subject);
            db.close();

            subjects.update();
        }
    }
}