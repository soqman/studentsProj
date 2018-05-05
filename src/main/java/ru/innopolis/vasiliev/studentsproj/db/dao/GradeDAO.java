package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Grade;

import java.sql.SQLException;

public interface GradeDAO {
    public Grade getGrade(int student_id, int subject_id)throws SQLException;
    public boolean setGrade(int student_id, int subject_id, Grade grade)throws SQLException;
    public boolean deleteGrade(int student_id, int subject_id)throws SQLException;
}
