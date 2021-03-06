package com.alex.rp.subject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.alex.rp.R;
import com.alex.rp.db.DB;

/**
 * Created by alex on 09.05.2014.
 */
public class SubjectDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private final static String LOG = "SubjectDialog";
    private View v;
    private EditText etNameSubject;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(LOG, "onCreateDialog");

        AlertDialog.Builder builder = null;

        try {

            builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = getActivity().getLayoutInflater();
            v = inflater.inflate(R.layout.subject, null);
            etNameSubject = (EditText) v.findViewById(R.id.et_name_subject);

            builder.setView(v)
                    .setTitle("Новый предмет")
                    .setPositiveButton("OK", this)
                    .setNegativeButton("Отмена", this);

        } catch (Exception e) {
            Log.d(LOG, "!!!!!onCreteDialog " + e.toString());
        }

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        String subjectName = etNameSubject.getText().toString();

        if (i == Dialog.BUTTON_POSITIVE && !subjectName.equals("")) {

            Subject subject = new Subject(subjectName);

            DB db = new DB(getActivity());
            db.add(subject);
            db.close();

            if(getActivity().getClass() == Subjects.class) {
                Subjects subjects = (Subjects) getActivity();
                subjects.update();
            }
        }
    }

}