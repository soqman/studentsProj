package ru.innopolis.vasiliev.studentsproj.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class SubjectsEditorService {
    private final Logger logger = LogManager.getLogger(SubjectsEditorService.class);

    public int addSubject(String name, SubjectDAO subjectDAO) {
        if (!checkRights()) return -2;
        if (checkExists(name, subjectDAO)) return -3;
        try {
            return subjectDAO.addSubject(name);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return -1;
        }
    }

    public int deleteSubject(int id, SubjectDAO subjectDAO) {
        if (!checkRights()) return -2;
        if (!checkExists(id, subjectDAO)) return -3;
        try {
            if (subjectDAO.deleteSubject(id)) return 1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return -1;
        }
        return -1;
    }

    public int updateTeacher(int subject_id, int teacher_id, SubjectDAO subjectDAO, UserDAO userDAO) {
        int result = -1;
        if (!checkRights()) return -2;
        if (!checkForTeacher(teacher_id, userDAO)) return -3;
        try {
            Subject subject = subjectDAO.getSubjectById(subject_id);
            subject.setTeacherId(teacher_id);
            boolean res = subjectDAO.updateSubject(subject);
            result = (res) ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int addStudent(int subject_id, int student_id, GradeDAO gradeDAO, UserDAO userDAO) {
        int result = -1;
        if (!checkRights()) return -2;
        if (!checkForStudent(student_id, userDAO)) return -3;
        try {
            boolean res = gradeDAO.setGrade(student_id, subject_id, Grade.NULL);
            result = res ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteStudent(int subject_id, int student_id, GradeDAO gradeDAO, UserDAO userDAO) {
        int result = -1;
        if (!checkRights()) return -2;
        if (!checkForStudent(student_id, userDAO)) return -3;
        try {
            boolean res = gradeDAO.deleteGrade(student_id, subject_id);
            result = res ? 1 : -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Set<Subject> getSubjectsForUser(String login, UserDAO userDAO, SubjectDAO subjectDAO) {
        HashSet<Subject> subjects = null;
        User user = null;
        try {
            user = userDAO.getUserByLogin(login);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        if (user == null) return null;
        try {
            switch (user.getUserType()) {
                case Administrator:
                    subjects = subjectDAO.getAllSubjectsList();
                    break;
                case Teacher:
                    subjects = subjectDAO.getSubjectListByTeacherId(user.getUser_id());
                    break;
                case Student:
                    subjects = subjectDAO.getSubjectListByStudentId(user.getUser_id());
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    private boolean checkForTeacher(int user_id, UserDAO userDAO) {
        try {
            if (userDAO.getUserById(user_id).getUserType() == UserType.Teacher) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkForStudent(int user_id, UserDAO userDAO) {
        try {
            if (userDAO.getUserById(user_id).getUserType() == UserType.Student) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkRights() {
        return true;
    }

    private boolean checkExists(String name, SubjectDAO subjectDAO) {
        Subject subject = null;
        try {
            subject = subjectDAO.getSubjectByName(name);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        boolean result = subject != null ? true : false;
        return result;
    }

    private boolean checkExists(int id, SubjectDAO subjectDAO) {
        Subject subject = null;
        try {
            subject = subjectDAO.getSubjectById(id);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        boolean result = subject != null ? true : false;
        return result;
    }
}
