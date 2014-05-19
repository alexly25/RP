package com.alex.rp.semester;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alex on 11.05.2014.
 */
public class Semester implements Serializable {

    private int id;
    private Date start;
    private Date end;

    public Semester(int id, Date start, Date end){
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Semester(Date start, Date end){
        id = -1;
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }
}
