package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Student;
import java.sql.SQLException;
import java.util.Set;

interface StudentDAO {
    Set<Student> getStudentsListBySubjectId(int subjectId)throws SQLException;
    Set<Student> getAllStudents()throws SQLException;
}
