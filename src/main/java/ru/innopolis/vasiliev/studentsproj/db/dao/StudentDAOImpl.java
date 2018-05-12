package ru.innopolis.vasiliev.studentsproj.db.dao;

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
import java.util.Set;

public class StudentDAOImpl implements StudentDAO {
    private static final ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    private final UserDAOImpl userDAO=new UserDAOImpl();
    @Override
    public Set<Student> getStudentsListBySubjectId(int subjectId) throws SQLException {
        Connection connection = connectionManager.getConnection();
        HashSet<Student> students=new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM grades where subject_id=?")) {
            statement.setInt(1, subjectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = userDAO.getUserById(resultSet.getInt("user_id"));
                    students.add(new Student(user.getUserId(),user.getLogin(),user.getPasswordHash()));
                }
            }
        }
        return students;
    }

    @Override
    public Set<Student> getAllStudents() throws SQLException {
        Connection connection = connectionManager.getConnection();
        HashSet<Student> students=new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT user_id FROM users where user_type=?")) {
            statement.setString(1, UserType.Student.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = userDAO.getUserById(resultSet.getInt("user_id"));
                    students.add(new Student(user.getUserId(),user.getLogin(),user.getPasswordHash()));
                }
            }
        }
        return students;
    }
}
