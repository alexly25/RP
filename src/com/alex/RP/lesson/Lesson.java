package com.alex.rp.lesson;

import com.alex.rp.group.Group;
import com.alex.rp.subject.Subject;

import java.io.Serializable;

/**
 * Created by alex on 12.05.2014.
 */
public class Lesson implements Serializable {

    //private int id;
    private Group group;
    private Subject subject;
    private boolean lecture;
/*
    public Lesson(int id, Group group, Subject subject, boolean lecture){
        this.id = id;
        this.group = group;
        this.subject = subject;
        this.lecture = lecture;
    }
    */
    public Lesson(Group group, Subject subject, boolean lecture){
        //id = -1;
        this.group = group;
        this.subject = subject;
        this.lecture = lecture;
    }

    public Group getGroup() {
        return group;
    }

    public Subject getSubject() {
        return subject;
    }

    public boolean isLecture() {
        return lecture;
    }
/*
    public int getId() {
        return id;
    }*/
}
