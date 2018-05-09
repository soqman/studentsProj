package ru.innopolis.vasiliev.studentsproj.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManager;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;

import java.sql.*;
import java.util.HashSet;

public class SubjectDAOImpl implements SubjectDAO {
    final static Logger logger=LogManager.getLogger(SubjectDAOImpl.class);
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    @Override
    public Subject getSubjectById(int id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT *FROM subjects where subject_id=?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Subject subject = null;
        while (resultSet.next()) {
            subject = new Subject(resultSet.getInt("subject_id"), resultSet.getString("name"), resultSet.getInt("teacher_id"));
        }
        connection.close();
        return subject;
    }

    @Override
    public Subject getSubjectByName(String name)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT *FROM subjects where name=?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        Subject subject = null;
        while (resultSet.next()) {
            subject = new Subject(resultSet.getInt("subject_id"), resultSet.getString("name"), resultSet.getInt("teacher_id"));
        }
        connection.close();
        return subject;
    }

    @Override
    public HashSet<Subject> getSubjectListByStudentId(int student_id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT subject_id FROM grades where user_id=?");
        statement.setInt(1, student_id);
        ResultSet resultSet = statement.executeQuery();
        HashSet<Subject> subjects=new HashSet<>();
        while (resultSet.next()) {
            subjects.add(getSubjectById(resultSet.getInt("subject_id")));
        }
        return subjects;
    }

    @Override
    public HashSet<Subject> getSubjectListByTeacherId(int teacher_id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT subject_id FROM subjects where teacher_id=?");
        statement.setInt(1, teacher_id);
        ResultSet resultSet = statement.executeQuery();
        HashSet<Subject> subjects=new HashSet<>();
        while (resultSet.next()) {
            subjects.add(getSubjectById(resultSet.getInt("subject_id")));
        }
        return subjects;
    }

    @Override
    public HashSet<Subject> getAllSubjectsList() throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT subject_id FROM subjects");
        ResultSet resultSet = statement.executeQuery();
        HashSet<Subject> subjects=new HashSet<>();
        while (resultSet.next()) {
            subjects.add(getSubjectById(resultSet.getInt("subject_id")));
        }
        return subjects;
    }

    @Override
    public int addSubject(String name)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (subject_id, name, teacher_id) VALUES (DEFAULT, ?, ?)",Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setNull(2,Types.INTEGER);
        int i =statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        int subject_id=-1;
        if (resultSet.next()) {
            subject_id=resultSet.getInt(1);
        }
        connection.close();
        return i>0?subject_id:-1;
    }

    @Override
    public int addSubject(String name, int teacher_id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (subject_id, name, teacher_id) VALUES (DEFAULT, ?, ?),Statement.RETURN_GENERATED_KEYS");
        statement.setString(1, name);
        statement.setInt(2,teacher_id);
        int i =statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        int subject_id=-1;
        if (resultSet.next()) {
            subject_id=resultSet.getInt(1);
        }
        connection.close();
        return i>0?subject_id:-1;
    }

    @Override
    public boolean updateSubject(Subject subject)throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE subjects SET name = ?,teacher_id=? WHERE subject_id = ?");
        statement.setString(1, subject.getName());
        if(subject.getTeacherId()==0)statement.setNull(2,Types.INTEGER);
        else statement.setInt(2,subject.getTeacherId());
        statement.setInt(3,subject.getSubjectId());
        int i =statement.executeUpdate();
        connection.close();
        return i>0?true:false;
    }

    @Override
    public boolean deleteSubject(int id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        Statement statement = connection.createStatement();
        int i =statement.executeUpdate("DELETE FROM subjects WHERE subject_id = "+id);
        connection.close();
        return i>0?true:false;
    }
}
