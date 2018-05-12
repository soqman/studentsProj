package ru.innopolis.vasiliev.studentsproj.service;

import org.junit.*;
import org.mockito.Mockito;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAOImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.AuthResult;
import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class AuthServiceTest {
    private static final AuthService authService=new AuthService();
    private static final String VALID_LOGIN="vlogin";
    private static final String INVALID_LOGIN="ivlogin";
    private static final String VALID_PASS="1";
    private static final String INVALID_PASS="-1";
    private static final UserType VALID_USERTYPE=UserType.Administrator;

    @Test
    public void checkAccessTest()throws SQLException{
        UserDAOImpl userDAO=Mockito.mock(UserDAOImpl.class);
        User user=new User(0,VALID_LOGIN,VALID_PASS,VALID_USERTYPE);
        when(userDAO.getUserByLogin(VALID_LOGIN)).thenReturn(user);
        when(userDAO.getUserByLogin(INVALID_LOGIN)).thenReturn(null);
        AuthResult authResult = authService.checkAccess(VALID_LOGIN,VALID_PASS,userDAO);
        assertEquals(AuthResult.ACCESS_GRANTED_ADMINISTRATOR,authResult);
        authResult = authService.checkAccess(INVALID_LOGIN,VALID_PASS,userDAO);
        assertEquals(AuthResult.INVALID_LOGIN,authResult);
        authResult = authService.checkAccess(VALID_LOGIN,INVALID_PASS,userDAO);
        assertEquals(AuthResult.INVALID_PASSWORD,authResult);
        authResult = authService.checkAccess(INVALID_LOGIN,VALID_PASS,userDAO);
        assertEquals(AuthResult.INVALID_LOGIN,authResult);
    }
}
