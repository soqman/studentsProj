package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Subject;

import java.sql.SQLException;
import java.util.Set;

public interface SubjectDAO {
    Subject getSubjectById(int id)throws SQLException;
    Subject getSubjectByName(String name)throws SQLException;
    Set<Subject> getSubjectListByStudentId(int studentId)throws SQLException;
    Set<Subject> getSubjectListByTeacherId(int teacherId)throws SQLException;
    Set<Subject> getAllSubjectsList()throws SQLException;
    int addSubject(String name, int teacherId)throws SQLException;
    int addSubject(String name)throws SQLException;
    boolean updateSubject(Subject subject)throws SQLException;
    boolean deleteSubject(int id)throws SQLException;



}
