package com.alex.rp.week;

import com.alex.rp.lesson.Lesson;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Date;

/**
 * Created by alex on 21.06.2014.
 */
public class Replacement implements Serializable {

    private int id;
    private Date date;
    private int day;
    private Lesson lesson;
    private boolean even;

    public Replacement(Date date, int day) {
        this.date = date;
        this.day = day;
    }

    public Replacement(Date date, int day, Lesson lesson) {
        this.date = date;
        this.day = day;
        this.lesson = lesson;
    }

    public Replacement(int id, Date date, int day, Lesson lesson) {
        this.id = id;
        this.date = date;
        this.day = day;
        this.lesson = lesson;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public boolean isEven() {
        return even;
    }

    public void setEven(boolean even) {
        this.even = even;
    }

    public String toString(){

        String even = (this.even)?"чтн. нед.":"нечетн. нед.";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getWeek(day/10 + 1)).append(" ").append(day%10).append(" пара ").append(even);
        //return new StringBuilder().append(lesson.getGroup().getName()).append(", ").append(lesson.getSubject().getName()).append(", ").append(day).append(", ").append(semester.getStart().toString()).toString();
        return stringBuilder.toString();
    }

    private String getWeek(int num) {
        if(num == 8){
            num=1;
        }
        DateFormatSymbols dfs = new DateFormatSymbols();
        return  dfs.getWeekdays()[num];
    }
}
