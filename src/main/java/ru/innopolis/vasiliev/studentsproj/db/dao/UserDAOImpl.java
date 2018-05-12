package ru.innopolis.vasiliev.studentsproj.db.dao;

import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManager;
import ru.innopolis.vasiliev.studentsproj.db.connection.ConnectionManagerJDBCImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private static final ConnectionManager connectionManager = ConnectionManagerJDBCImpl.getInstance();
    @Override
    public User getUserById(int id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        User user;
        try (PreparedStatement statement = connection.prepareStatement("SELECT *FROM users where user_id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                user = null;
                while (resultSet.next()) {
                    user = new User(resultSet.getInt("user_id"), resultSet.getString("login"), resultSet.getString("password_hash"), UserType.valueOf(resultSet.getString("user_type")));
                }
            }
        }
        connection.close();
        return user;
    }

    @Override
    public User getUserByLogin(String login)throws SQLException {
        Connection connection = connectionManager.getConnection();
        User user;
        try (PreparedStatement statement = connection.prepareStatement("SELECT *FROM users where login=?")) {
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                user = null;
                while (resultSet.next()) {
                    user = new User(resultSet.getInt("user_id"), resultSet.getString("login"), resultSet.getString("password_hash"), UserType.valueOf(resultSet.getString("user_type")));
                }
            }
        }
        connection.close();
        return user;
    }

    @Override
    public int addUser(String login,String passwordHash,UserType userType)throws SQLException  {
        Connection connection = connectionManager.getConnection();
        int i;
        int userId;
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (user_id, user_type, password_hash, login) VALUES (DEFAULT, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userType.toString());
            statement.setString(2, passwordHash);
            statement.setString(3, login);
            i = statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                userId = -1;
                if (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }
            }
        }
        connection.close();
        return i>0?userId:-1;
    }

    @Override
    public boolean updateUser(User user)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        try (PreparedStatement statement = connection.prepareStatement("UPDATE users SET user_type = ?,password_hash=?,login=? WHERE user_id = ?")) {
            statement.setString(1, user.getUserType().toString());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getLogin());
            statement.setInt(4, user.getUserId());
            i = statement.executeUpdate();
        }
        connection.close();
        return i > 0;
    }

    @Override
    public boolean deleteUserById(int id)throws SQLException {
        Connection connection = connectionManager.getConnection();
        int i;
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE user_id =? ")) {
            statement.setInt(1,id);
            i = statement.executeUpdate();
        }
        connection.close();
        return i > 0;
    }
}
