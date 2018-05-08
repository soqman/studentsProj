package ru.innopolis.vasiliev.studentsproj.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(AuthServlet.class);
    private final static String parameter_page = "page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        if (req.getParameter(parameter_page) != null) {
            switch (Integer.parseInt(req.getParameter(parameter_page))) {
                case 1:
                    req.setAttribute("content", "list of subjects");
                    break;
                case 2:
                    req.setAttribute("content", "list of students");
                    break;
                default:
                    break;
            }
        }
        req.getRequestDispatcher("/dash.jsp").forward(req, resp);
    }
}
