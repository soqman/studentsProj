package ru.innopolis.vasiliev.studentsproj.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAOImpl;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;
import ru.innopolis.vasiliev.studentsproj.service.SubjectsEditorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    final static Logger logger=LogManager.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/dash.jsp").forward(req, resp);
    }
}
