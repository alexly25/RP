package com.alex.rp.tables;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.alex.rp.db.Vars;
import com.alex.rp.lesson.LessonActivity;
import com.alex.rp.R;
import com.alex.rp.semester.Semester;
import com.alex.rp.week.TemplateActivity;
import com.alex.rp.week.Timetable;

import java.util.ArrayList;

/**
 * Created by alex on 02.05.2014.
 */
public class TemplateTable extends Table {

    private final static String LOG = "TemplateTable";
    private Semester semester;

    public TemplateTable(Activity activity, TableLayout tl, Semester semester, boolean even) {
        super(activity, tl, even);
        this.semester = semester;
        alTimetables = db.getTimetables(semester);

        for (int i = 0; i < 8; i++) {

            TableRow tr = new TableRow(activity);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
            tr.setGravity(Gravity.CENTER);

            for (int j = 0; j < 8; j++) {

                final int value = ((i - 1) * 10) + j;

                TextView textView = new TextView(activity);

                if (i == 0) {
                    if (j == 0) {
                        //Date date = new Date();
                        //textView.setText(date.getDay() + "");
                        //textView.setId(value);
                    } else {
                        textView.setText("" + j);
                    }
                } else if (i == 1) {
                    if (j == 0) {
                        textView.setText("Время");
                    } else {
                        textView.setText(getTimeStart(j));
                    }
                } else if (i > 1 && j == 0) {
                    textView.setText(getDateAndDay(i - 1));
                    arrTv[i - 2] = textView;
                } else {

                    Timetable timetable = getTimetable(value);

                    if(timetable != null){
                        textView.setText(timetable.getLesson().getGroup().getName());
                    }

                    textView.setId(value);
                    textView.setOnClickListener((TemplateActivity) activity);

                }

                textView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border));
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tr.addView(textView);
            }

            tl.addView(tr);
        }

        db.close();
    }

    private Timetable getTimetable(int day) {

        for(int i=0; i<alTimetables.size(); i++){
            Timetable timetable = alTimetables.get(i);
            if(timetable.getDay() == day && timetable.isEven() == even){
                return timetable;
            }
        }

        return null;
    }

    @Override
    protected String getDateAndDay(int i) {

        String dateAndDay = null;

        switch (i) {
            case 1:
                dateAndDay = "ПН";
                break;
            case 2:
                dateAndDay = "ВТ";
                break;
            case 3:
                dateAndDay = "СР";
                break;
            case 4:
                dateAndDay = "ЧТ";
                break;
            case 5:
                dateAndDay = "ПТ";
                break;
            case 6:
                dateAndDay = "СБ";
                break;
        }
        return dateAndDay;
    }
/*
    @Override
    public void onClick(View view) {

        Timetable timetable = new Timetable(semester);
        timetable.setDay(view.getId());

        Intent intent = new Intent(activity, LessonActivity.class);
        intent.putExtra(Vars.FIELD_TIMETABLE, timetable);
        activity.startActivity(intent);

*//*
        switch (view.getId()) {
            case R.id.btn_back_week:
                Log.d(LOG, "Не четная");

                btnBackWeek.setEnabled(false);
                btnNextWeek.setEnabled(true);

                break;
            case R.id.btn_next_week:
                Log.d(LOG, "Четная");

                btnBackWeek.setEnabled(true);
                btnNextWeek.setEnabled(false);

                break;
            default:
                *//**//*
        String group = selectedGroup.getString();
        int id = view.getId();
        TextView tv = (TextView) view;
        DB DB = new DB(activity);

        DB.add(true, id, group);
        String group2 = DB.get(true, id);
        tv.setText(group2);
        DB.close();
                activity.startActivity(new Intent(activity, LessonActivity.class));
                break;
        }*//*
    }*/
}
