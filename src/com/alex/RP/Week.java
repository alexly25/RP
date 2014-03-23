package com.alex.RP;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * Created by alex on 23.03.14.
 */
public class Week extends Activity implements View.OnClickListener {

    private final static String LOG = "RP";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);

        TableLayout tl = (TableLayout) findViewById(R.id.tl);
        tl.setStretchAllColumns(true);
        tl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        //tl.setGravity(Gravity.CENTER);

        registerForContextMenu(tl);

        for (int i = 0; i < 8; i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
            tr.setGravity(Gravity.CENTER);

            for (int j = 0; j < 8; j++) {

                int value = (i * 10) + j;

                TextView textView = new TextView(this);

                if (i == 0) {
                    if (j == 0) {
                        textView.setText("№ пары");
                    } else {
                        textView.setText(""+j);
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
                    textView.setText(" " + value);
                }
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
                textView.setId(value);
                tr.addView(textView);
            }

            tl.addView(tr);
        }
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
        TextView textView = (TextView) view;
        Log.d(LOG, "view " + view.getId());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.m_i1:
                Log.d(LOG, "click m_i1");
                return true;
        }
        return super.onContextItemSelected(item);
    }
}