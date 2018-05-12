package ru.innopolis.vasiliev.studentsproj.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.GradeDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.pojo.Grade;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;
import ru.innopolis.vasiliev.studentsproj.pojo.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;

public class SubjectsEditorService {
    private final Logger logger = LogManager.getLogger(SubjectsEditorService.class);

    int addSubject(String operatorLogin, String subjectName, SubjectDAO subjectDAO, UserDAO userDao) {
        if (!DataChecker.checkForAdmin(operatorLogin,userDao)) return -2;
        if (DataChecker.checkExists(subjectName,subjectDAO)) return -3;
        try {
            return subjectDAO.addSubject(subjectName);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return -1;
        }
    }

    int deleteSubject(String operatorLogin, int subjectId, SubjectDAO subjectDAO, UserDAO userDao) {
        if (!DataChecker.checkForAdmin(operatorLogin,userDao)) return -2;
        if (!DataChecker.checkExists(subjectId, subjectDAO)) return -3;
        try {
            if (subjectDAO.deleteSubject(subjectId)) return 1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return -1;
        }
        return -1;
    }

    int updateTeacher(String operatorLogin,int subjectId, int teacherId, SubjectDAO subjectDAO, UserDAO userDao) {
        int result = -1;
        if (!DataChecker.checkForAdmin(operatorLogin,userDao)) return -2;
        if (!DataChecker.checkForTeacher(teacherId, userDao)) return -3;
        try {
            Subject subject = subjectDAO.getSubjectById(subjectId);
            subject.setTeacherId(teacherId);
            boolean res = subjectDAO.updateSubject(subject);
            result = (res) ? 1 : -1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    int addStudent(String operatorLogin, int subjectId, int studentId, GradeDAO gradeDAO, UserDAO userDao) {
        int result = -1;
        if (!DataChecker.checkForAdmin(operatorLogin,userDao)) return -2;
        if (!DataChecker.checkForStudent(studentId, userDao)) return -3;
        try {
            boolean res = gradeDAO.setGrade(studentId, subjectId, Grade.NULL);
            result = res ? 1 : -1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    int deleteStudent(String operatorName,int subjectId, int studentId, GradeDAO gradeDAO, UserDAO userDAO) {
        int result = -1;
        if (!DataChecker.checkForAdmin(operatorName,userDAO)) return -2;
        if (!DataChecker.checkForStudent(studentId, userDAO)) return -3;
        try {
            boolean res = gradeDAO.deleteGrade(studentId, subjectId);
            result = res ? 1 : -1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public Set<Subject> getSubjectsForUser(String login, UserDAO userDAO, SubjectDAO subjectDAO) {
        Set<Subject> subjects = null;
        User user = null;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (user == null) return Collections.emptySet();
        try {
            switch (user.getUserType()) {
                case Administrator:
                    subjects = subjectDAO.getAllSubjectsList();
                    break;
                case Teacher:
                    subjects = subjectDAO.getSubjectListByTeacherId(user.getUserId());
                    break;
                case Student:
                    subjects = subjectDAO.getSubjectListByStudentId(user.getUserId());
                    break;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return subjects;
    }
}
