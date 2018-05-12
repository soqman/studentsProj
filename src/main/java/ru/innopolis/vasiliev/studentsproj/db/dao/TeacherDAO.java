package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.pojo.Teacher;
import java.sql.SQLException;
import java.util.Set;

interface TeacherDAO {
    Teacher getTeacherBySubjectId(int subjectId)throws SQLException;
    Set<Teacher> getAllTeachers()throws SQLException;
}
