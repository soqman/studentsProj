package ru.innopolis.vasiliev.studentsproj.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.controller.utils.ConstHolder;
import ru.innopolis.vasiliev.studentsproj.db.dao.*;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;
import ru.innopolis.vasiliev.studentsproj.service.SubjectsEditorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DashboardServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AuthServlet.class);
    private static final SubjectsEditorService subjectEditorService = new SubjectsEditorService();
    private static final GradeDAO gradeDAO=new GradeDAOImpl();
    private static final UserDAO userDAO=new UserDAOImpl();
    private static final SubjectDAO subjectDAO=new SubjectDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        switch (checkParameter(req.getParameter(ConstHolder.PARAM_PAGE))) {
            case 1:
                req.setAttribute(ConstHolder.ATR_CONTENT, getSubjectsContent((String)req.getSession().getAttribute(ConstHolder.PARAM_LGN)));
                break;
            case 2:
                req.setAttribute(ConstHolder.ATR_CONTENT, "list of students");
                break;
            default:
                break;
        }
        try {
            req.getRequestDispatcher(ConstHolder.JSP_DASHBOARD).forward(req, resp);
        } catch (ServletException|IOException e) {
            logger.error(e.getMessage());
        }
    }

    private ArrayList getSubjectsContent(String login){
        Set<Subject> subjects = subjectEditorService.getSubjectsForUser(login, userDAO,subjectDAO );
        HashMap<String,String>map=new HashMap<>();
        ArrayList<HashMap> content=new ArrayList<>();
        for (Subject subject:subjects){
            map.put("name",subject.getName());
            try {
                map.put(ConstHolder.ATR_TEACHER,userDAO.getUserById(subject.getTeacherId()).getLogin());
            } catch (SQLException|NullPointerException e) {
                map.put(ConstHolder.ATR_TEACHER,"");
            }
            try {
                map.put(ConstHolder.ATR_GRADE,gradeDAO.getGrade(userDAO.getUserByLogin(login).getUserId(),subject.getSubjectId()).toString());
            } catch (SQLException|NullPointerException e) {
                map.put(ConstHolder.ATR_GRADE,"");
            }
            content.add(new HashMap<>(map));
        }
        return content;
    }

    private int checkParameter(String parameterPage) {
        int res = 0;
        if (parameterPage != null) {
            try {
                res = Integer.parseInt(parameterPage);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
            }
        }
        return res;
    }
}
