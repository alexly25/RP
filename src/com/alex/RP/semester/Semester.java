package com.alex.rp.semester;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alex on 11.05.2014.
 */
public class Semester implements Serializable {

    private Date start;
    private Date end;

    public Semester(Date start, Date end){
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
