package ru.innopolis.vasiliev.studentsproj.pojo;

public class Teacher extends User{
    public Teacher(int userId,String login, String passwordHash) {
        super(userId,login, passwordHash, UserType.Teacher);
    }
}
