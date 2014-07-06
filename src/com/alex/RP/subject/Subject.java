package com.alex.rp.subject;

import java.io.Serializable;

/**
 * Created by alex on 11.05.2014.
 */
public class Subject implements Serializable {

    private int id;
    private String name;

    public Subject(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Subject(String name){
        id = -1;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
