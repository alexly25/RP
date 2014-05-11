package com.alex.rp.db;

import com.alex.rp.group.Group;
import com.alex.rp.subject.Subject;

import java.util.ArrayList;

/**
 * Created by alex on 23.03.14.
 */
public interface DBI {
    public boolean add(Group group);
    public boolean add(Subject subject);
    public boolean delete(Group group);
    public boolean delete(Subject subject);
    public ArrayList<Group> getGroups();
    public ArrayList<Subject> getSubject();
}
