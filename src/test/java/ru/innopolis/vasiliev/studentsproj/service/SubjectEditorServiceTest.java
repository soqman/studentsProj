package ru.innopolis.vasiliev.studentsproj.service;

import org.junit.*;
import ru.innopolis.vasiliev.studentsproj.db.dao.GradeDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.pojo.Grade;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;
import ru.innopolis.vasiliev.studentsproj.pojo.User;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubjectEditorServiceTest {
    private static final String SUBJECT1NAME="testsubject";
    private static final String SUBJECT2NAME ="testsubject2";
    private static final String SUBJECT3NAME ="testsubject3";
    private static final int SUBJECT1ID=1;
    private static final int SUBJECT2ID=2;
    private static final int SUBJECT3ID=3;
    private static final String PASSWORDHASH="123";
    private static final String NAMESTUDENT="testStudent";
    private static final String NAMETEACHER="testTeacher";
    private static final String NAMEADMIN="testAdmin";
    private static final int USERSTUDENTID =1;
    private static final int USERTEACHERID =2;
    private static final int USERADMINID =3;
    private static final Subject SUBJECT1=new Subject(SUBJECT1ID, SUBJECT1NAME,USERTEACHERID);
    private static final Subject SUBJECT2=new Subject(SUBJECT2ID, SUBJECT2NAME,USERTEACHERID);
    private static final Subject SUBJECT3=new Subject(SUBJECT3ID, SUBJECT3NAME,USERTEACHERID);
    private static final User USERSTUDENT =new User(USERSTUDENTID,NAMESTUDENT,PASSWORDHASH,UserType.Student);
    private static final User USERTEACHER =new User(USERTEACHERID,NAMETEACHER,PASSWORDHASH,UserType.Teacher);
    private static final User USERADMIN =new User(USERADMINID,NAMEADMIN,PASSWORDHASH,UserType.Administrator);
    private static final SubjectDAO subjectDAO=mock(SubjectDAO.class);
    private static final UserDAO userDAO=mock(UserDAO.class);
    private static final GradeDAO gradeDAO=mock(GradeDAO.class);
    private static final HashSet<Subject>subjects=new HashSet<>();
    private static final SubjectsEditorService subjectsEditorService=new SubjectsEditorService();

    @BeforeClass
    public static void mockDAO()throws SQLException{
        subjects.add(SUBJECT1);
        when(subjectDAO.addSubject(SUBJECT1NAME)).thenReturn(SUBJECT1ID);
        when(subjectDAO.addSubject(SUBJECT2NAME)).thenReturn(SUBJECT2ID);
        when(subjectDAO.getSubjectById(SUBJECT1ID)).thenReturn(SUBJECT1);
        when(subjectDAO.getSubjectById(SUBJECT2ID)).thenReturn(null);
        when(subjectDAO.getSubjectById(SUBJECT3ID)).thenReturn(SUBJECT3);
        when(subjectDAO.getSubjectByName(SUBJECT1NAME)).thenReturn(null);
        when(subjectDAO.getSubjectByName(SUBJECT2NAME)).thenReturn(SUBJECT2);
        when(subjectDAO.deleteSubject(SUBJECT1ID)).thenReturn(true);
        when(subjectDAO.deleteSubject(SUBJECT2ID)).thenReturn(true);
        when(subjectDAO.deleteSubject(SUBJECT3ID)).thenReturn(false);
        when(userDAO.getUserById(USERSTUDENTID)).thenReturn(USERSTUDENT);
        when(userDAO.getUserById(USERTEACHERID)).thenReturn(USERTEACHER);
        when(userDAO.getUserById(USERADMINID)).thenReturn(USERADMIN);
        when(userDAO.getUserByLogin(NAMETEACHER)).thenReturn(USERTEACHER);
        when(userDAO.getUserByLogin(NAMESTUDENT)).thenReturn(USERSTUDENT);
        when(userDAO.getUserByLogin(NAMEADMIN)).thenReturn(USERADMIN);
        when(subjectDAO.getSubjectListByTeacherId(USERTEACHERID)).thenReturn(subjects);
        when(subjectDAO.getSubjectListByStudentId(USERSTUDENTID)).thenReturn(null);
        when(subjectDAO.updateSubject(anyObject())).thenReturn(true);
        when(gradeDAO.setGrade(anyInt(),anyInt(),eq(Grade.NULL))).thenReturn(true);
        when(gradeDAO.deleteGrade(anyInt(),anyInt())).thenReturn(true);
    }
    @Test
    public void addSubjectTest(){
        int result =subjectsEditorService.addSubject(NAMEADMIN,SUBJECT1NAME,subjectDAO,userDAO);
        Assert.assertEquals(SUBJECT1ID,result);
        result=subjectsEditorService.addSubject(NAMEADMIN,SUBJECT2NAME,subjectDAO,userDAO);
        Assert.assertEquals(-3,result);
        result=subjectsEditorService.addSubject(NAMESTUDENT,SUBJECT1NAME,subjectDAO,userDAO);
        Assert.assertEquals(-2,result);
    }

    @Test
    public void deleteSubjectTest(){
        int result=subjectsEditorService.deleteSubject(NAMEADMIN,SUBJECT1ID,subjectDAO,userDAO);
        Assert.assertEquals(1,result);
        result=subjectsEditorService.deleteSubject(NAMEADMIN,SUBJECT3ID,subjectDAO,userDAO);
        Assert.assertEquals(-1,result);
        result=subjectsEditorService.deleteSubject(NAMETEACHER,SUBJECT1ID,subjectDAO,userDAO);
        Assert.assertEquals(-2,result);
        result=subjectsEditorService.deleteSubject(NAMEADMIN,SUBJECT2ID,subjectDAO,userDAO);
        Assert.assertEquals(result,-3);


    }

    @Test
    public void updateTeacherTest(){
        int result=subjectsEditorService.updateTeacher(NAMEADMIN,SUBJECT2ID, USERSTUDENTID,subjectDAO,userDAO);
        Assert.assertEquals(-3,result);
        result=subjectsEditorService.updateTeacher(NAMETEACHER,SUBJECT1ID, USERTEACHERID,subjectDAO,userDAO);
        Assert.assertEquals(-2,result);
        result=subjectsEditorService.updateTeacher(NAMEADMIN,SUBJECT1ID, USERTEACHERID,subjectDAO,userDAO);
        Assert.assertEquals(1,result);
    }

    @Test
    public void addStudentTest(){
        int result=subjectsEditorService.addStudent(NAMEADMIN,SUBJECT1ID, USERSTUDENTID,gradeDAO,userDAO);
        Assert.assertEquals(1,result);
        result=subjectsEditorService.addStudent(NAMETEACHER,SUBJECT1ID, USERSTUDENTID,gradeDAO,userDAO);
        Assert.assertEquals(-2,result);
        result=subjectsEditorService.addStudent(NAMEADMIN,SUBJECT1ID, USERTEACHERID,gradeDAO,userDAO);
        Assert.assertEquals(-3,result);
    }

    @Test
    public void deleteStudentTest(){
        int result=subjectsEditorService.deleteStudent(NAMEADMIN,SUBJECT1ID, USERSTUDENTID,gradeDAO,userDAO);
        Assert.assertEquals(1,result);
        result=subjectsEditorService.deleteStudent(NAMESTUDENT,SUBJECT1ID, USERSTUDENTID,gradeDAO,userDAO);
        Assert.assertEquals(-2,result);
        result=subjectsEditorService.deleteStudent(NAMEADMIN,SUBJECT2ID, USERTEACHERID,gradeDAO,userDAO);
        Assert.assertEquals(-3,result);
    }

    @Test
    public void getSubjectsForUserTest(){
        Set<Subject> s=subjectsEditorService.getSubjectsForUser(NAMETEACHER,userDAO,subjectDAO);
        Assert.assertEquals(subjects,s);
        s=subjectsEditorService.getSubjectsForUser(NAMESTUDENT,userDAO,subjectDAO);
        Assert.assertNull(s);

    }
}
