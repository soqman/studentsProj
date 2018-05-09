package ru.innopolis.vasiliev.studentsproj.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final static Logger logger = LogManager.getLogger(AuthServlet.class);
    private final static String parameter_page = "page";
    private final static String parameter_login = "login";
    private final static SubjectsEditorService subjectEditorService = new SubjectsEditorService();
    private final static GradeDAO gradeDAO=new GradeDAOImpl();
    private final static UserDAO userDAO=new UserDAOImpl();
    private final static SubjectDAO subjectDAO=new SubjectDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        switch (checkParameter(req.getParameter(parameter_page))) {
            case 1:
                req.setAttribute("content", getSubjectsContent((String)req.getSession().getAttribute(parameter_login)));
                break;
            case 2:
                req.setAttribute("content", "list of students");
                break;
            default:
                break;
        }
        req.getRequestDispatcher("/dash.jsp").forward(req, resp);
    }

    private ArrayList getSubjectsContent(String login){
        Set<Subject> subjects = subjectEditorService.getSubjectsForUser(login, userDAO,subjectDAO );
        HashMap<String,String>map=new HashMap<>();
        ArrayList<HashMap> content=new ArrayList<>();
        for (Subject subject:subjects){
            map.put("name",subject.getName());
            try {
                map.put("teacher",userDAO.getUserById(subject.getTeacherId()).getLogin());
            } catch (SQLException e) {
                map.put("teacher","");
            }catch (NullPointerException o){
                map.put("teacher","");
            }
            try {
                map.put("grade",gradeDAO.getGrade(userDAO.getUserByLogin(login).getUser_id(),subject.getSubjectId()).toString());
            } catch (SQLException e) {
                map.put("grade","");
            } catch (NullPointerException o){
                map.put("grade","");
            }
            content.add(new HashMap(map));
        }
        return content;
    }

    private int checkParameter(String parameter_page) {
        int res = 0;
        if (parameter_page != null) {
            try {
                res = Integer.parseInt(parameter_page);
            } catch (NumberFormatException e) {
            }
        }
        return res;
    }
}
