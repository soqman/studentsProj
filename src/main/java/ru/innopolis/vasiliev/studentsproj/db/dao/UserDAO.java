package ru.innopolis.vasiliev.studentsproj.db.dao;


import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.SQLException;

public interface UserDAO {
    public User getUserById(int id)throws SQLException;
    public User getUserByLogin(String login)throws SQLException;
    public int addUser(String login,int passwordHash,UserType userType)throws SQLException;
    public boolean updateUser(User user)throws SQLException;
    public boolean deleteUserById(int id)throws SQLException;
}
