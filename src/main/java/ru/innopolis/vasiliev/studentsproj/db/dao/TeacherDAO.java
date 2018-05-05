package ru.innopolis.vasiliev.studentsproj.db.dao;


import ru.innopolis.vasiliev.studentsproj.pojo.Teacher;

import java.sql.SQLException;
import java.util.HashSet;

public interface TeacherDAO {
    public Teacher getTeacherBySubjectId(int subject_id)throws SQLException;
    public HashSet<Teacher> getAllTeachers()throws SQLException;;
}
