package com.alex.rp.week;

import com.alex.rp.lesson.Lesson;
import com.alex.rp.semester.Semester;

import java.io.Serializable;

/**
 * Created by alex on 12.05.2014.
 */
public class Timetable implements Serializable {

    private Semester semester;
    private boolean even;
    private int day;
    private Lesson lesson;

    public Timetable(Semester semester){
        this.semester = semester;
    }

    public Timetable(Semester semester, boolean even, int day, Lesson lesson){
        this.semester = semester;
        this.even = even;
        this.day = day;
        this.lesson = lesson;
    }

    public Semester getSemester() {
        return semester;
    }

    public boolean isEven() {
        return even;
    }

    public int getDay() {
        return day;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setEven(boolean even) {
        this.even = even;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String toString(){
        return new StringBuilder().append(lesson.getGroup().getName()).append(", ").append(lesson.getSubject().getName()).append(", ").append(day).append(", ").append(semester.getStart().toString()).toString();
    }
}
