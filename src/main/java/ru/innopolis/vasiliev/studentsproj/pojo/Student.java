package ru.innopolis.vasiliev.studentsproj.pojo;

public class Student extends User{

    public Student(int userId,String login, String passwordHash) {
        super(userId,login, passwordHash, UserType.Student);
    }
}
