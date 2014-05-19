package com.alex.rp.db;

import com.alex.rp.group.Group;
import com.alex.rp.semester.Semester;
import com.alex.rp.subject.Subject;
import com.alex.rp.week.Timetable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alex on 23.03.14.
 */
public interface DBI {
    public boolean add(Group group);
    public boolean add(Subject subject);
    public boolean add(Semester semester);
    public boolean add(Timetable timetable);
    public boolean delete(Group group);
    public boolean delete(Subject subject);
    public boolean delete(Semester semester);
    public boolean reset(Timetable timetable);
    public ArrayList<Group> getGroups();
    public ArrayList<Subject> getSubjects();
    public ArrayList<Semester> getSemesters();
    public ArrayList<Timetable> getTimetables();
    public ArrayList<Timetable> getTimetables(Semester semester);
    public Semester getSemester(Date date);
    /*public Group getGroup(String name);
    public Subject getSubject(String name);*/
}
