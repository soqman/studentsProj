package ru.innopolis.vasiliev.studentsproj.db.dao;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManager;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SubjectDAOImpl implements SubjectDAO {
    private static final String SUBJECT_ID = "subject_id";
    private static final ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    @Override
    public Subject getSubjectById(int id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        Subject subject = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT *FROM subjects where subject_id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    subject = new Subject(resultSet.getInt(SUBJECT_ID), resultSet.getString("name"), resultSet.getInt("teacher_id"));
                }
            }
        }
        connection.close();
        return subject;
    }

    @Override
    public Subject getSubjectByName(String name)throws SQLException {
        Connection connection = connectionManager.getConnection();
        Subject subject = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT *FROM subjects where name=?")) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    subject = new Subject(resultSet.getInt(SUBJECT_ID), resultSet.getString("name"), resultSet.getInt("teacher_id"));
                }
            }
        }
        connection.close();
        return subject;
    }

    @Override
    public Set<Subject> getSubjectListByStudentId(int studentId)throws SQLException {
        Connection connection = connectionManager.getConnection();
        HashSet<Subject> subjects=new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT subject_id FROM grades where user_id=?")) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    subjects.add(getSubjectById(resultSet.getInt(SUBJECT_ID)));
                }
            }
        }
        return subjects;
    }

    @Override
    public Set<Subject> getSubjectListByTeacherId(int teacherId)throws SQLException {
        Connection connection = connectionManager.getConnection();
        HashSet<Subject> subjects;
        try (PreparedStatement statement = connection.prepareStatement("SELECT subject_id FROM subjects where teacher_id=?")) {
            statement.setInt(1, teacherId);
            try (ResultSet resultSet = statement.executeQuery()) {
                subjects = new HashSet<>();
                while (resultSet.next()) {
                    subjects.add(getSubjectById(resultSet.getInt(SUBJECT_ID)));
                }
            }
        }
        return subjects;
    }

    @Override
    public Set<Subject> getAllSubjectsList() throws SQLException {
        Connection connection = connectionManager.getConnection();
        HashSet<Subject> subjects;
        try (PreparedStatement statement = connection.prepareStatement("SELECT subject_id FROM subjects")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                subjects = new HashSet<>();
                while (resultSet.next()) {
                    subjects.add(getSubjectById(resultSet.getInt(SUBJECT_ID)));
                }
            }
        }
        return subjects;
    }

    @Override
    public int addSubject(String name)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        int subjectId;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (subject_id, name, teacher_id) VALUES (DEFAULT, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setNull(2, Types.INTEGER);
            i = statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                subjectId = -1;
                if (resultSet.next()) {
                    subjectId = resultSet.getInt(1);
                }
            }
        }
        connection.close();
        return i>0?subjectId:-1;
    }

    @Override
    public int addSubject(String name, int teacherId)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        int subjectId;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (subject_id, name, teacher_id) VALUES (DEFAULT, ?, ?)",(Statement.RETURN_GENERATED_KEYS))) {
            statement.setString(1, name);
            statement.setInt(2, teacherId);
            i = statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                subjectId = -1;
                if (resultSet.next()) {
                    subjectId = resultSet.getInt(1);
                }
            }
        }
        connection.close();
        return i>0?subjectId:-1;
    }

    @Override
    public boolean updateSubject(Subject subject)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE subjects SET name = ?,teacher_id=? WHERE subject_id = ?")) {
            statement.setString(1, subject.getName());
            if (subject.getTeacherId() == 0) statement.setNull(2, Types.INTEGER);
            else statement.setInt(2, subject.getTeacherId());
            statement.setInt(3, subject.getSubjectId());
            i = statement.executeUpdate();
        }
        connection.close();
        return i > 0;
    }

    @Override
    public boolean deleteSubject(int id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM subjects WHERE subject_id =?")) {
            statement.setInt(1,id);
            i = statement.executeUpdate();
        }
        connection.close();
        return i > 0;
    }
}
