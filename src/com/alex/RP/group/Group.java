package com.alex.rp.group;

import com.alex.rp.db.Color;

import java.io.Serializable;

/**
 * Created by alex on 10.05.2014.
 */
public class Group implements Serializable {

    private int id;
    private String name;
    private Color color;
    private boolean commerce;

    public Group(int id, String name, int color, boolean commerce){
        this.id = id;
        this.name = name;
        this.color = new Color(color);
        this.commerce = commerce;
    }

    public Group(String name, int color, boolean commerce){
        id = -1;
        this.name = name;
        this.color = new Color(color);
        this.commerce = commerce;
    }

    public Group(String name, String color, boolean commerce){
        this.name = name;
        this.color = new Color(color);
        this.commerce = commerce;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isCommerce() {
        return commerce;
    }

    public int getId() {
        return id;
    }

}
