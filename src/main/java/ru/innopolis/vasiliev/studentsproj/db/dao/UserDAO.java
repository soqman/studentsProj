package ru.innopolis.vasiliev.studentsproj.db.dao;


import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.SQLException;

public interface UserDAO {
    User getUserById(int id)throws SQLException;
    User getUserByLogin(String login)throws SQLException;
    int addUser(String login,String passwordHash,UserType userType)throws SQLException;
    boolean updateUser(User user)throws SQLException;
    boolean deleteUserById(int id)throws SQLException;
}
