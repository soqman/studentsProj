package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Student;

import java.sql.SQLException;
import java.util.HashSet;

public interface StudentDAO {
    public HashSet<Student> getStudentsListBySubjectId(int subject_id)throws SQLException;
    public HashSet<Student> getAllStudents()throws SQLException;
}
