package ru.innopolis.vasiliev.studentsproj.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAOImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.AuthResult;
import ru.innopolis.vasiliev.studentsproj.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    final static Logger logger=LogManager.getLogger(AuthServlet.class);
    private final AuthService authService=new AuthService();
    private final String parameter_login="login";
    private final String parameter_password="password";
    private final UserDAO userDAO = new UserDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(parameter_login);
        String password = req.getParameter(parameter_password);
        if(!req.getParameterMap().containsKey(parameter_login) || !req.getParameterMap().containsKey(parameter_password)){
            resp.getWriter().println("please, use parameters and insert login and password");
            return;
        }
        AuthResult authResult =authService.checkAccess(login,Integer.valueOf(password),userDAO);
        switch (authResult){
            case INVALID_LOGIN: resp.getWriter().println("invalid_login");break;
            case INVALID_PASSWORD: resp.getWriter().println("invalid_password");break;
            case ACCESS_GRANTED_ADMINISTRATOR: resp.getWriter().println("Welcome, Administrator");break;
            case ACCESS_GRANTED_TEACHER: resp.getWriter().println("Welcome, Teacher");break;
            case ACCESS_GRANTED_STUDENT: resp.getWriter().println("Welcome, Student");break;
            case ERROR: resp.getWriter().println("some error");break;
            default:resp.getWriter().println("some error");break;
        }
    }
}
