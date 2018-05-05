package ru.innopolis.vasiliev.studentsproj.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManager;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.Student;
import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class StudentDAOImpl implements StudentDAO {
    final static Logger logger=LogManager.getLogger(SubjectDAOImpl.class);
    private static ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    private UserDAOImpl userDAO=new UserDAOImpl();
    @Override
    public HashSet<Student> getStudentsListBySubjectId(int subject_id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM grades where subject_id=?");
        statement.setInt(1, subject_id);
        ResultSet resultSet = statement.executeQuery();
        HashSet<Student> students=new HashSet<>();
        while (resultSet.next()) {
            User user = userDAO.getUserById(resultSet.getInt("user_id"));
            students.add(new Student(user.getUser_id(),user.getLogin(),user.getPasswordHash()));
        }
        return students;
    }

    @Override
    public HashSet<Student> getAllStudents() throws SQLException {
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM users where user_type=?");
        statement.setString(1, UserType.Student.toString());
        ResultSet resultSet = statement.executeQuery();
        HashSet<Student> students=new HashSet<>();
        while (resultSet.next()) {
            User user = userDAO.getUserById(resultSet.getInt("user_id"));
            students.add(new Student(user.getUser_id(),user.getLogin(),user.getPasswordHash()));
        }
        return students;
    }
}
