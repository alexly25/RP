package com.alex.rp.tables;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.alex.rp.R;
import com.alex.rp.group.Group;
import com.alex.rp.semester.Semester;

/**
 * Created by alex on 08.06.2014.
 */
public class GroupTable extends Table{

    private final static String LOG = "GroupTable";

    public GroupTable(Activity activity, TableLayout tableLayout, Semester semester) {
        super(activity, tableLayout, false);

        Log.d(LOG, "GroupTable");
        int[][] a = new int[2][4];
        a[0][0] = 20;
        a[0][1] = 18;
        a[0][2] = 14;
        a[0][3] = 10;
        a[1][0] = 5;
        a[1][1] = 15;
        a[1][2] = 15;
        a[1][3] = 20;

        for (int i = 0; i < 3; i++) {

            //Log.d(LOG, "i="+i);

            TableRow tr = new TableRow(activity);
            tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1));
            tr.setGravity(Gravity.CENTER);

            for (int j = 0; j < semester.getMouthLength(); j++) {

                //Log.d(LOG, "j="+j);

                TextView textView = getTextView("");

                if(i == 0){
                    if(j > 0) {
                        String mouth = semester.getMouth(j);
                        textView = getTextView(mouth);
                    }
                } else if(i == 1){
                    if(j == 0) {
                        textView = getTextView(" Теория ");
                    }
                } else if(i == 2){
                    if(j == 0) {
                        textView = getTextView(" Практика ");
                    }
                }
                if (i!=0 && j > 0){
                    Log.d(LOG,"i=" + i + " j=" + j);
                    textView = getTextView("" + a[i-1][j-1]);
                }

                tr.addView(textView);
            }

            TextView textView = getTextView("");

            if(i==0){
                textView = getTextView(" Всего ");
            } else if (i==1){
                textView = getTextView(" 74 ");
            } else if (i==2){
                textView = getTextView(" 55 ");
            }

            tr.addView(textView);

            tableLayout.addView(tr);
        }

        db.close();
    }

    private TextView getTextView(String text){
        TextView textView = new TextView(activity);
        textView.setText(text);
        textView.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border));
        textView.setHeight(30);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    @Override
    protected String getDateAndDay(int i) {
        return null;
    }
}
