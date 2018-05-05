package ru.innopolis.vasiliev.studentsproj.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAOImpl;
import ru.innopolis.vasiliev.studentsproj.service.SubjectsEditorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    final static Logger logger=LogManager.getLogger(AuthServlet.class);
    private final String parameter_addsubj_name="add_subj_name";
    private final String getParameter_delsubj_id="del_subj_id";
    private final SubjectsEditorService subjectsEditorService=new SubjectsEditorService();
    private final SubjectDAO subjectDAO=new SubjectDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameterMap().containsKey(parameter_addsubj_name)&& !req.getParameter(parameter_addsubj_name).isEmpty()){
            int result =subjectsEditorService.addSubject(req.getParameter(parameter_addsubj_name),subjectDAO);
            if(result>1) resp.getWriter().println("new subject added successful. id is "+result);
            else if(result==-1) resp.getWriter().println("some error");
            else if(result==-2) resp.getWriter().println("access denied");
            else if(result==-3) resp.getWriter().println("is exist");
            return;
        }
        if(req.getParameterMap().containsKey(getParameter_delsubj_id) && !req.getParameter(getParameter_delsubj_id).isEmpty()){
            int result =subjectsEditorService.deleteSubject(Integer.parseInt(req.getParameter(getParameter_delsubj_id)),subjectDAO);
            if(result==1) resp.getWriter().println("deleted");
            else if(result==-1) resp.getWriter().println("some error");
            else if(result==-2) resp.getWriter().println("access denied");
            else if(result==-3) resp.getWriter().println("its not exist");
            return;
        }
        resp.getWriter().println("please, use parameters add_subj_name or del_subj_id");
    }
}
