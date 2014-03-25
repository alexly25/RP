package com.alex.RP;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by alex on 24.03.14.
 */
public class Table implements View.OnClickListener {

    private final static String LOG = "Table";
    private Activity activity;
    private MyString selectedGroup;
    private BD bd;

    Table(final Activity activity, TableLayout tl, MyString selectedGroup) {

        this.activity = activity;
        this.selectedGroup = selectedGroup;

        bd = new BD(activity);
        bd.outTables();

        for (int i = 0; i < 8; i++) {

            TableRow tr = new TableRow(activity);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
            tr.setGravity(Gravity.CENTER);

            for (int j = 0; j < 8; j++) {

                final int value = ((i - 1) * 10) + j;

                TextView textView = new TextView(activity);

                if (i == 0) {
                    if (j == 0) {
                        textView.setText("№ пары");
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
                    textView.setText(getDayOfWeek(i - 1));
                } else {

                    String group = bd.get(true, value);
                    textView.setText(group);
                    textView.setId(value);
                    textView.setOnClickListener(this);

                }

                textView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border));
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
                tr.addView(textView);
            }

            tl.addView(tr);
        }

        bd.dbClose();
    }

    private String getDayOfWeek(int i) {
        String day = null;
        switch (i) {
            case 1:
                day = "ПН";
                break;
            case 2:
                day = "ВТ";
                break;
            case 3:
                day = "СР";
                break;
            case 4:
                day = "ЧТ";
                break;
            case 5:
                day = "ПТН";
                break;
            case 6:
                day = "СУБ";
                break;
        }
        return day;
    }

    private String getTimeStart(int i) {
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

    @Override
    public void onClick(View view) {
        Log.d(LOG, "onClick");
        String group = selectedGroup.getString();
        int id = view.getId();
        TextView tv = (TextView) view;
        BD bd = new BD(activity);

        bd.add(true, id, group);
        String group2 = bd.get(true, id);
        tv.setText(group2);
        bd.dbClose();
    }
}
