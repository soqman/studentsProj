package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Grade;

import java.sql.SQLException;

public interface GradeDAO {
    Grade getGrade(int studentId, int subjectId)throws SQLException;
    boolean setGrade(int studentId, int subjectId, Grade grade)throws SQLException;
    boolean deleteGrade(int studentId, int subjectId)throws SQLException;
}
