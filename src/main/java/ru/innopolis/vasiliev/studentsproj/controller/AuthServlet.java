package ru.innopolis.vasiliev.studentsproj.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.controller.utils.ConstHolder;
import ru.innopolis.vasiliev.studentsproj.controller.utils.ServletUtils;
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
    private final AuthService authService = new AuthService();

    private static final Logger logger = LogManager.getLogger(AuthServlet.class);
    private final UserDAO userDAO = new UserDAOImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.setEncoding(req, resp);
        if (req.getParameterMap().containsKey(ConstHolder.PARAM_LOGOUT)) {
            req.getSession().removeAttribute(ConstHolder.PARAM_LGN);
            req.getSession().removeAttribute(ConstHolder.PARAM_PW);
        }
        if (req.getSession().getAttribute(ConstHolder.PARAM_LGN) != null) {
            ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_DASHBOARD, resp);
            return;
        }
        try {
            req.getRequestDispatcher(ConstHolder.JSP_AUTH).forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.setEncoding(req, resp);
        String login = req.getParameter(ConstHolder.PARAM_LGN);
        String password = req.getParameter(ConstHolder.PARAM_PW);
        if (!req.getParameterMap().containsKey(ConstHolder.PARAM_LGN) || !req.getParameterMap().containsKey(ConstHolder.PARAM_PW) || req.getParameter(ConstHolder.PARAM_LGN).isEmpty() || req.getParameter(ConstHolder.PARAM_PW).isEmpty()) {
            ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_AUTH_ERROR, resp);
            return;
        }
        AuthResult authResult = authService.checkAccess(login, DigestUtils.md5Hex(password), userDAO);
        switch (authResult) {
            case INVALID_LOGIN:
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_AUTH_ERROR, resp);
                break;
            case INVALID_PASSWORD:
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_AUTH_ERROR, resp);
                break;
            case ACCESS_GRANTED_ADMINISTRATOR:
                req.getSession().setAttribute(ConstHolder.PARAM_LGN, login);
                req.getSession().setAttribute(ConstHolder.PARAM_USERTYPE, UserType.Administrator);
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_DASHBOARD, resp);
                break;
            case ACCESS_GRANTED_TEACHER:
                req.getSession().setAttribute(ConstHolder.PARAM_LGN, login);
                req.getSession().setAttribute(ConstHolder.PARAM_USERTYPE, UserType.Teacher);
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_DASHBOARD, resp);
                break;
            case ACCESS_GRANTED_STUDENT:
                req.getSession().setAttribute(ConstHolder.PARAM_LGN, login);
                req.getSession().setAttribute(ConstHolder.PARAM_USERTYPE, UserType.Student);
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_DASHBOARD, resp);
                break;
            case ERROR:
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_UNDEF_ERROR, resp);
                break;
            default:
                ServletUtils.redirectTo(req.getContextPath() + ConstHolder.URL_UNDEF_ERROR, resp);
                break;
        }
    }


}
