package com.alex.rp.tables;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.alex.rp.Lesson;
import com.alex.rp.MyString;
import com.alex.rp.R;

import java.util.Date;

/**
 * Created by alex on 02.05.2014.
 */
public class TemplateTable extends Table {

    private final static String LOG = "TemplateTable";

    public TemplateTable(Activity activity, TableLayout tl, MyString selectedGroup, Date date) {
        super(activity, tl, selectedGroup, date);

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

                    //String group = DB.get(true, value);
                    //textView.setText(group);
                    textView.setId(value);
                    textView.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {

        activity.startActivity(new Intent(activity, Lesson.class));

/*
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
                *//*
        String group = selectedGroup.getString();
        int id = view.getId();
        TextView tv = (TextView) view;
        DB DB = new DB(activity);

        DB.add(true, id, group);
        String group2 = DB.get(true, id);
        tv.setText(group2);
        DB.close();*//*
                activity.startActivity(new Intent(activity, Lesson.class));
                break;
        }*/
    }
}
