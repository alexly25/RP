package com.alex.rp.tables;

import android.app.Activity;
import android.widget.TableLayout;
import android.widget.TextView;
import com.alex.rp.db.DB;
import com.alex.rp.week.Template;

import java.util.ArrayList;

/**
 * Created by alex on 24.03.14.
 */
public abstract class Table {

    private final static String LOG = "Table";
    protected Activity activity;
    protected DB db;
    protected TextView[] arrTv;
    protected TextView[] arrTvBorder2;// Массив TV содержащий столбцы сегоднешнего дня
    protected ArrayList<Template> alTemplates;
    protected boolean even;


    Table(final Activity activity, TableLayout tl, boolean even) {

        this.activity = activity;
        this.even = even;
        this.alTemplates = new ArrayList<Template>();

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
