package com.alex.rp.semester;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by alex on 11.05.2014.
 */
public class Semester implements Serializable {

    private final static String LOG = "Semester";

    private int id;
    private Date startWeek; // пнд недели с которой начинаются семестр
    private Date endWeek; // вск недели на которой заканчивается семестр
    private Date firstDay;
    private Date lastDay;
    private int mouthLength;

    public Semester(int id, Date firstDay, Date lastDay) {
        this.id = id;
        this.firstDay = firstDay;
        this.lastDay = new Date(lastDay.getTime() + 86400000);
        setStartWeek();
        setEndWeek();
        countMouth();
    }

    public Semester(Date firstDay, Date lastDay) {
        id = -1;
        this.firstDay = firstDay;
        this.lastDay = new Date(lastDay.getTime() + 86400000);Log.d(LOG,"Semester last="+lastDay.toString());
        setStartWeek();
        setEndWeek();
        countMouth();
    }

    private void setStartWeek() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(firstDay);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        startWeek = new Date(firstDay.getTime() - ((dayOfWeek-2) * 86400000));
    }

    private void setEndWeek() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastDay);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if(dayOfWeek == 1){
            dayOfWeek=8;
        }

        endWeek = new Date(lastDay.getTime() + ((8-dayOfWeek) * 86400000));
    }

    private void countMouth() {

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(startWeek);
        int start = calendar.get(calendar.MONTH);

        calendar.setTime(endWeek);
        int end = calendar.get(calendar.MONTH);

        mouthLength = end - start;
    }

    public Date getStart() {
        return startWeek;
    }

    public Date getEnd() {
        return endWeek;
    }

    public Date getFirst() {
        return firstDay;
    }

    public Date getLastDay() {Log.d(LOG,"getLast last="+lastDay.toString());
        return lastDay;
    }

    public int getId() {
        return id;
    }
/*

    private Calendar getWeek(int number) {

        if (number < 1) {
            return null;
        }

        Date dateFirst = new Date(first.getTime() + ((number - 1) * 604800000));
        Calendar result = new GregorianCalendar();
        result.setTime(dateFirst);

        if (end.getTime() < result.getTime().getTime()) {
            return null;
        }

        return result;
    }
*/

    /**
     * Метод возвращает номер недели
     *
     * @param date
     * @return
     */
    public int getWeek(Date date) {
        int result = -1;

        Date strtWeek = new Date(startWeek.getTime());
        Date nextWeek = new Date(startWeek.getTime() + 604800000);
        long time = date.getTime();

        for (int i = 1; strtWeek.getTime() < lastDay.getTime(); i++) {

            if (time >= strtWeek.getTime() && time < nextWeek.getTime()) {
                return i;
            }

            strtWeek = new Date(strtWeek.getTime() + 604800000);
            nextWeek = new Date(nextWeek.getTime() + 604800000);
        }

        //Log.d(LOG, "date="+date.toString() + " week="+result);

        return result;
    }

    public int getWeekCount() {
        long size = (endWeek.getTime() - startWeek.getTime()) / 604800000;
        return (int) size;
    }

    public int getMouthLength() {
        return mouthLength;
    }

    public String getMouth(int i) {

        StringBuilder result = new StringBuilder("");
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(startWeek);
        int first = calendar.get(calendar.MONTH);

        switch (i) {
            case 1:
                result.append(mouth(first));
                break;
            case 2:
                result.append(mouth(first + 1));
                break;
            case 3:
                result.append(mouth(first + 2));
                break;
            case 4:
                result.append(mouth(first + 3));
                break;
            case 5:
                result.append(mouth(first + 4));
                break;
            case 6:
                result.append(mouth(first + 5));
                break;
            case 7:
                result.append(mouth(first + 6));
                break;
            case 8:
                result.append(mouth(first + 7));
                break;
            case 9:
                result.append(mouth(first + 8));
                break;
        }

        return result.toString();
    }

    private String mouth(int num) {

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getShortMonths();

        return months[num % 12];
    }
}
