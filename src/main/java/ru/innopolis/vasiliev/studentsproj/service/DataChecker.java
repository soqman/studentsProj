package ru.innopolis.vasiliev.studentsproj.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.innopolis.vasiliev.studentsproj.db.dao.SubjectDAO;
import ru.innopolis.vasiliev.studentsproj.db.dao.UserDAO;
import ru.innopolis.vasiliev.studentsproj.pojo.Subject;
import ru.innopolis.vasiliev.studentsproj.pojo.UserType;

import java.sql.SQLException;

class DataChecker {
    private static final Logger logger=LogManager.getLogger(DataChecker.class);
    static boolean checkForTeacher(int userId, UserDAO userDAO) {
        try {
            if (userDAO.getUserById(userId).getUserType() == UserType.Teacher) return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkForTeacher(String name, UserDAO userDAO) {
        try {
            if (userDAO.getUserByLogin(name).getUserType() == UserType.Teacher) return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkForAdmin(int userId, UserDAO userDAO) {
        try {
            if (userDAO.getUserById(userId).getUserType() == UserType.Administrator) return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkForAdmin(String name, UserDAO userDAO) {
        try {
            if (userDAO.getUserByLogin(name).getUserType() == UserType.Administrator) return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkForStudent(int userId, UserDAO userDAO) {
        try {
            if (userDAO.getUserById(userId).getUserType() == UserType.Student) return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkForStudent(String name, UserDAO userDAO) {
        try {
            if (userDAO.getUserByLogin(name).getUserType() == UserType.Student) return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    static boolean checkExists(String name, SubjectDAO subjectDAO) {
        Subject subject;
        try {
            subject = subjectDAO.getSubjectByName(name);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return subject != null;
    }

    static boolean checkExists(int id, SubjectDAO subjectDAO) {
        Subject subject;
        try {
            subject = subjectDAO.getSubjectById(id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
        return subject != null;
    }

    private DataChecker() {
        throw new IllegalStateException("Utility class");
    }
}
