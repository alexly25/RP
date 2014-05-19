package com.alex.rp.tables;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.alex.rp.MyString;
import com.alex.rp.R;
import com.alex.rp.semester.Semester;
import com.alex.rp.week.Timetable;
import com.alex.rp.week.TimetableActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by alex on 02.05.2014.
 */
public class TimetableTable extends Table {

    private final static String LOG = "TimetableTable";
    Date date;
    protected Calendar calendar;

    public TimetableTable(Activity activity, TableLayout tl, Date date, boolean even) {
        super(activity, tl, even);

        //db.update(new Semester(new Date(date.getTime() - 604800000), new Date(date.getTime() + 604800000)));

        this.date = date;
        calendar = new GregorianCalendar();
        calendar.setTime(date);
        Semester semester = db.getSemester(date);
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
                        //textView.setText(calendar.DAY_OF_WEEK + "");
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

                    Timetable timetable = null;

                    if((timetable = getTimetable(value)) != null){
                        textView.setText(timetable.getLesson().getGroup().getName());
                    }

                    textView.setId(value);
                    textView.setOnClickListener((TimetableActivity)activity);

                }

                Calendar nowCalendar = Calendar.getInstance();
                if (i == (nowCalendar.get(nowCalendar.DAY_OF_WEEK)) && nowCalendar.get(nowCalendar.DAY_OF_WEEK) != 7) {
                    textView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border2));
                    arrTvBorder2[i] = textView;
                } else {
                    textView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border));
                }
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tr.addView(textView);
            }

            tl.addView(tr);
        }

        db.close();

    }

    private Timetable getTimetable(int day) {

        if(alTimetables == null){
            return null;
        }

        for(int i=0; i<alTimetables.size(); i++){
            Timetable timetable = alTimetables.get(i);
            if(timetable.getDay() == day){
                return timetable;
            }
        }

        return null;
    }

    protected String getDateAndDay(int i) {
        String dateAndDay = null;
        String date = null;
        int day = calendar.get(calendar.DAY_OF_WEEK) - 1;


        if (i == day) {
            date = calendar.get(calendar.DAY_OF_MONTH) + "";
        } else {
            int raz = i - day;
            Date otherDate = new Date(this.date.getTime() + (raz * 86400000));
            Calendar otherCalendar = new GregorianCalendar();
            otherCalendar.setTime(otherDate);
            date = otherCalendar.get(otherCalendar.DAY_OF_MONTH) + "";
        }

        switch (i) {
            case 1:
                dateAndDay = "ПН/" + date;
                break;
            case 2:
                dateAndDay = "ВТ/" + date;
                break;
            case 3:
                dateAndDay = "СР/" + date;
                break;
            case 4:
                dateAndDay = "ЧТ/" + date;
                break;
            case 5:
                dateAndDay = "ПТ/" + date;
                break;
            case 6:
                dateAndDay = "СБ/" + date;
                break;
        }
        return dateAndDay;
    }
/*

    @Override
    public void onClick(View view) {
        Log.d(LOG, "onClick");
*/
/*
        String group = selectedGroup.getString();
        int id = view.getId();
        TextView tv = (TextView) view;
        DB DB = new DB(activity);

        DB.add(true, id, group);
        String group2 = DB.get(true, id);
        tv.setText(group2);
        DB.close();*//*


        long dateTime;
*/
/*
        switch (view.getId()) {
            case R.id.btn_back_week:
                Log.d(LOG, "back");

                dateTime = date.getTime() - 604800000;
                date = new Date(dateTime);
                setDate(date);

                break;
            case R.id.btn_next_week:
                Log.d(LOG, "next");

                dateTime = date.getTime() + 604800000;
                date = new Date(dateTime);
                setDate(date);

                break;
        }*//*

    }
*/

    public void setDate(Date date) {
        this.date = date;
        calendar.setTime(date);
        for (int i = 0; i < 6; i++) {
            arrTv[i].setText(getDateAndDay(i + 1));
        }

        /*for(int i=0; i<8; i++){

            Log.d(LOG, "i: " + i);

            Date nowDate = new Date();

            if ((nowDate.getYear() == date.getYear()
                    && nowDate.getMonth() == date.getMonth()
                    && nowDate.getDate() == date.getDate())) {
                arrTvBorder2[i].setBackgroundDrawable(d2);
            } else {
                TextView tv = arrTvBorder2[i];
                tv.setBackgroundDrawable(d);
            }
        }*/
    }
}
