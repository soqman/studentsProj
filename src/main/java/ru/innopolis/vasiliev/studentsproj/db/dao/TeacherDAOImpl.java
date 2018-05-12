package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManager;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.Teacher;
import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TeacherDAOImpl implements TeacherDAO {
    private static final ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    private final UserDAOImpl userDAO=new UserDAOImpl();
    @Override
    public Teacher getTeacherBySubjectId(int subjectId)throws SQLException {
        Connection connection = connectionManager.getConnection();
        User user;
        try (PreparedStatement statement = connection.prepareStatement("SELECT teacher_id FROM subjects where subject_id=?")) {
            statement.setInt(1, subjectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                user = null;
                while (resultSet.next()) {
                    user = userDAO.getUserById(resultSet.getInt("teacher_id"));
                }
            }
        }
        Teacher teacher = null;
        if (user != null) {
            teacher = new Teacher(user.getUserId(),user.getLogin(),user.getPasswordHash());
        }
        return teacher;
    }

    @Override
    public Set<Teacher> getAllTeachers() throws SQLException{
        Connection connection = connectionManager.getConnection();
        HashSet<Teacher> teachers;
        try (PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM users where user_type=?")) {
            statement.setString(1, UserType.Teacher.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                teachers = new HashSet<>();
                while (resultSet.next()) {
                    User user = userDAO.getUserById(resultSet.getInt("user_id"));
                    teachers.add(new Teacher(user.getUserId(), user.getLogin(), user.getPasswordHash()));
                }
            }
        }
        return teachers;
    }
}
