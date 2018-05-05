package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Subject;

import java.sql.SQLException;
import java.util.HashSet;

public interface SubjectDAO {
    public Subject getSubjectById(int id)throws SQLException;
    public Subject getSubjectByName(String name)throws SQLException;
    public HashSet<Subject> getSubjectListByUserId(int user_id)throws SQLException;
    public int addSubject(String name, int teacher_id)throws SQLException;
    public int addSubject(String name)throws SQLException;
    public boolean updateSubject(Subject subject)throws SQLException;
    public boolean deleteSubject(int id)throws SQLException;



}
