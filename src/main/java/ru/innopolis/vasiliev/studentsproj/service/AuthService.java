package ru.innopolis.vasiliev.studentsproj.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.pojo.AuthResult;
import ru.innopolis.vasiliev.studentsproj.pojo.User;

import java.sql.SQLException;

public class AuthService {
    final static Logger logger=LogManager.getLogger(AuthService.class);
    public AuthResult checkAccess(String login, int passwordHash, UserDAO userDAO){
        User user=null;
        try {
            user = userDAO.getUserByLogin(login);
            if (user==null) return AuthResult.INVALID_LOGIN;
            if (user.getPasswordHash()==passwordHash){
                switch (user.getUserType()) {
                    case Student:
                        return AuthResult.ACCESS_GRANTED_STUDENT;
                    case Teacher:
                        return AuthResult.ACCESS_GRANTED_TEACHER;
                    case Administrator:
                        return AuthResult.ACCESS_GRANTED_ADMINISTRATOR;
                    default:
                        return AuthResult.ERROR;
                }
            }
            else return AuthResult.INVALID_PASSWORD;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return AuthResult.ERROR;
        }
    }
}
