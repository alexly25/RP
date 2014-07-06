package com.alex.rp.db;

import com.alex.rp.group.Group;
import com.alex.rp.semester.Semester;
import com.alex.rp.subject.Subject;
import com.alex.rp.week.Replacement;
import com.alex.rp.week.Template;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alex on 23.03.14.
 */
public interface DBI {
    public boolean add(Group group);
    public boolean add(Subject subject);
    public boolean add(Semester semester);
    public boolean add(Template template);
    public boolean add(Replacement replacement);
    public boolean delete(Group group);
    public boolean delete(Subject subject);
    public boolean delete(Semester semester);
    public boolean reset(Template template);
    public ArrayList<Group> getGroups();
    public ArrayList<Subject> getSubjects();
    public ArrayList<Semester> getSemesters();
    public ArrayList<Template> getTemplates();
    public ArrayList<Template> getTemplates(Semester semester);
    public ArrayList<Replacement> getReplacements(Semester semester);
    public Replacement getReplacement(Date date);
    public Semester getSemester(Date date);
    public int getHours(Semester semester, Group group, boolean lecture);
    public int getHours(Semester semester, boolean commerce);
    //public int getLecture(Semester semester, Group group);
    //public String getStatistic(Semester semester, String key);
    /*public Group getGroup(String name);
    public Subject getSubject(String name);*/
}
