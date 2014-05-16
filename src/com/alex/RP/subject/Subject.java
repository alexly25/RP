package com.alex.rp.subject;

import java.io.Serializable;

/**
 * Created by alex on 11.05.2014.
 */
public class Subject implements Serializable {
    String name;

    public Subject(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
