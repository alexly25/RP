package com.alex.rp.group;


import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by alex on 10.05.2014.
 */
public class Group implements Serializable {

    private int id;
    private String name;
    //private Color color;
    private int color;
    private boolean commerce;
    private int practice;
    private int lecture;

    public Group(int id, String name, int color, boolean commerce){
        this.id = id;
        this.name = name;
        this.color = color;
        this.commerce = commerce;
        practice = 0;
        lecture = 0;
    }

    public Group(String name, int color, boolean commerce){
        id = -1;
        this.name = name;
        this.color = color;
        this.commerce = commerce;
        practice = 0;
        lecture = 0;
    }

    public int getPractice() {
        return practice;
    }

    public void setPractice(int practice) {
        this.practice = practice;
    }

    public int getLecture() {
        return lecture;
    }

    public void setLecture(int lecture) {
        this.lecture = lecture;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public boolean isCommerce() {
        return commerce;
    }

    public int getId() {
        return id;
    }

}
