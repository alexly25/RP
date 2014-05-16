package com.alex.rp.db;

import java.io.Serializable;

/**
 * Created by alex on 11.05.2014.
 */
public class Color implements Serializable {

    private int id;
    private String name;

    public Color(int id){
        this.id = id;
        this.name = toName();
    }

    public Color(String name){
        this.name = name;
        this.id = toId();
    }

    private int toId() {

        int id = -1;

        if(name.equals("Синий")) {
            id = android.graphics.Color.BLUE;
        }else if(name.equals("Зеленый")){
                id = android.graphics.Color.GREEN;
        }else if(name.equals("Красный")){
                id = android.graphics.Color.RED;
        }

        return id;
    }

    private String toName() {
        String name = null;

        switch (id) {
            case android.graphics.Color.BLUE:
                name = "Синий";
                break;
            case android.graphics.Color.GREEN:
                name = "Зеленый";
                break;
            case android.graphics.Color.RED:
                name = "Красный";
                break;
        }

        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
