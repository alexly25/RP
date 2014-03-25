package com.alex.RP;

/**
 * Created by alex on 23.03.14.
 */
public interface BDRP {
    public boolean add(boolean even, int id, String group);
    public boolean add(String group, int color);
    public String get(boolean even, int id);
}
