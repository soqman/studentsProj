package ru.innopolis.vasiliev.studentsproj.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAOImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.AuthResult;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;
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
    private final String parameter_usertype="userType";
    private final UserDAO userDAO = new UserDAOImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if(req.getParameterMap().containsKey("logout")){
            req.getSession().removeAttribute(parameter_login);
            req.getSession().removeAttribute(parameter_usertype);
        }
        if(req.getSession().getAttribute(parameter_login)!=null){
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/auth.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter(parameter_login);
        String password = req.getParameter(parameter_password);
        if(!req.getParameterMap().containsKey(parameter_login) ||!req.getParameterMap().containsKey(parameter_password) || req.getParameter(parameter_login).isEmpty() || req.getParameter(parameter_password).isEmpty()){
            resp.sendRedirect(req.getContextPath() + "/auth?errorMsg=authErr");
            return;
        }
        AuthResult authResult =authService.checkAccess(login,DigestUtils.md5Hex(password),userDAO);
        switch (authResult){
            case INVALID_LOGIN:
                resp.sendRedirect(req.getContextPath() + "/auth?errorMsg=authErr");
                break;
            case INVALID_PASSWORD:
                resp.sendRedirect(req.getContextPath() + "/auth?errorMsg=authErr");
                break;
            case ACCESS_GRANTED_ADMINISTRATOR:
                req.getSession().setAttribute(parameter_login, login);
                req.getSession().setAttribute(parameter_usertype, UserType.Administrator);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
                break;
            case ACCESS_GRANTED_TEACHER:
                req.getSession().setAttribute(parameter_login, login);
                req.getSession().setAttribute(parameter_usertype, UserType.Teacher);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
                break;
            case ACCESS_GRANTED_STUDENT:
                req.getSession().setAttribute(parameter_login, login);
                req.getSession().setAttribute(parameter_usertype, UserType.Student);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
                break;
            case ERROR: resp.sendRedirect(req.getContextPath() + "/auth?errorMsg=UnError");
            break;
            default:resp.sendRedirect(req.getContextPath() + "/auth?errorMsg=UnError");
            break;
        }
    }
}
