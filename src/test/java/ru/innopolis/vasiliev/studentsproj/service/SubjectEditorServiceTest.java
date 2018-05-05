package ru.innopolis.vasiliev.studentsproj.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import ru.innopolis.vasiliev.studentsproj.db.dao.GradeDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.pojo.Grade;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;
import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;
import java.sql.SQLException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubjectEditorServiceTest {
    private static final Logger logger=LogManager.getLogger(SubjectEditorServiceTest.class);
    private static final String subject_name="TESTSUBJECT";
    private static final String subject_exists_name="EXISTTESTSUBJECT";
    private static final String teacher_name="TESTTEACHER";
    private static int subject_id=1;
    private static final int subject_exists_id=2;
    private static final int user_id=1;
    private static final int teacher_id=2;
    private static Subject existSubject=new Subject(subject_exists_id,subject_exists_name,0);
    private static User someUser=new User(user_id,teacher_name,123,UserType.Student);
    private static User teacher=new User(teacher_id,teacher_name,123,UserType.Teacher);
    private static final SubjectDAO subjectDAO=mock(SubjectDAO.class);
    private static final UserDAO userDAO=mock(UserDAO.class);
    private static final GradeDAO gradeDAO=mock(GradeDAO.class);
    private static final SubjectsEditorService subjectsEditorService=new SubjectsEditorService();


    @BeforeClass
    public static void mockDAO()throws SQLException{
        when(subjectDAO.addSubject(subject_name)).thenReturn(subject_id);
        when(subjectDAO.getSubjectById(subject_exists_id)).thenReturn(existSubject);
        when(subjectDAO.getSubjectByName(subject_exists_name)).thenReturn(existSubject);
        when(subjectDAO.deleteSubject(subject_exists_id)).thenReturn(true);
        when(subjectDAO.deleteSubject(subject_id)).thenReturn(false);
        when(userDAO.getUserById(user_id)).thenReturn(someUser);
        when(userDAO.getUserById(teacher_id)).thenReturn(teacher);
        when(subjectDAO.updateSubject(anyObject())).thenReturn(true);
        when(gradeDAO.setGrade(anyInt(),anyInt(),eq(Grade.NULL))).thenReturn(true);
        when(gradeDAO.deleteGrade(anyInt(),anyInt())).thenReturn(true);

    }
    @Test
    public void addSubjectTest(){
        int result =subjectsEditorService.addSubject(subject_name,subjectDAO);
        Assert.assertTrue(subject_id==result);
        result=subjectsEditorService.addSubject(subject_exists_name,subjectDAO);
        Assert.assertTrue(result==-3);
    }

    @Test
    public void deleteSubjectTest(){
        int result=subjectsEditorService.deleteSubject(subject_exists_id,subjectDAO);
        Assert.assertTrue(result==1);
        result=subjectsEditorService.deleteSubject(subject_id,subjectDAO);
        Assert.assertTrue(result==-3);

    }

    @Test
    public void updateTeacherTest(){
        int result=subjectsEditorService.updateTeacher(subject_exists_id,user_id,subjectDAO,userDAO);
        Assert.assertTrue(result==-3);
        result=subjectsEditorService.updateTeacher(subject_exists_id,teacher_id,subjectDAO,userDAO);
        Assert.assertTrue(result==1);
    }

    @Test
    public void addStudentTest(){
        int result=subjectsEditorService.addStudent(subject_id,user_id,gradeDAO,userDAO);
        Assert.assertTrue(result==1);
        result=subjectsEditorService.addStudent(subject_id,teacher_id,gradeDAO,userDAO);
        Assert.assertTrue(result==-3);
    }

    @Test
    public void deleteStudentTest(){
        int result=subjectsEditorService.deleteStudent(subject_id,user_id,gradeDAO,userDAO);
        Assert.assertTrue(result==1);
        result=subjectsEditorService.deleteStudent(subject_id,teacher_id,gradeDAO,userDAO);
        Assert.assertTrue(result==-3);
    }
}
