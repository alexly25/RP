package com.alex.rp.tables;

import android.app.Activity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import com.alex.rp.db.DB;
import com.alex.rp.MyString;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by alex on 24.03.14.
 */
public abstract class Table implements View.OnClickListener{

    private final static String LOG = "Table";
    protected Activity activity;
    protected MyString selectedGroup;
    protected DB db;
    protected Date date;
    protected TextView[] arrTv;
    protected TextView[] arrTvBorder2;// Массив TV содержащий столбцы сегоднешнего дня
    protected Calendar calendar;


    Table(final Activity activity, TableLayout tl, MyString selectedGroup, Date date) {

        this.activity = activity;
        this.selectedGroup = selectedGroup;
        this.date = date;
        calendar = new GregorianCalendar();
        calendar.setTime(date);

        db = new DB(activity);
        db.outTables();
        arrTv = new TextView[6];
        arrTvBorder2 = new TextView[8];
        tl.removeAllViews();

    }

    protected abstract String getDateAndDay(int i);

    protected String getTimeStart(int i) {
        String time = null;
        switch (i) {
            case 1:
                time = "8:00";
                break;
            case 2:
                time = "9:40";
                break;
            case 3:
                time = "11:40";
                break;
            case 4:
                time = "13:20";
                break;
            case 5:
                time = "15:00";
                break;
            case 6:
                time = "16:40";
                break;
            case 7:
                time = "18:20";
                break;
        }

        return time;
    }

}
