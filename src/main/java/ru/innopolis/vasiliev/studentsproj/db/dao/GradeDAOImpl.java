package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManager;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.Grade;

import java.sql.*;

public class GradeDAOImpl implements GradeDAO {
    private static final ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    @Override
    public Grade getGrade(int studentId, int subjectId)throws SQLException {
        Connection connection = connectionManager.getConnection();
        Grade grade = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT grade FROM grades where user_id=? AND subject_id=?")) {
            statement.setInt(1, studentId);
            statement.setInt(2, subjectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    grade = Grade.valueOf(resultSet.getString("grade"));
                }
            }
        }
        connection.close();
        return grade;
    }

    @Override
    public boolean setGrade(int studentId, int subjectId, Grade grade)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE grades SET grade = ? WHERE subject_id = ? AND user_id=?")) {
            statement.setString(1, grade.toString());
            statement.setInt(2, subjectId);
            statement.setInt(3, studentId);
            i = statement.executeUpdate();
        }
        connection.close();
        return i > 0;
    }

    @Override
    public boolean deleteGrade(int studentId, int subjectId)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM grades WHERE user_id=? AND subject_id=?")) {
            statement.setInt(1,studentId);
            statement.setInt(2,subjectId);
            i = statement.executeUpdate();
        }
        connection.close();
        return i > 0;
    }
}
