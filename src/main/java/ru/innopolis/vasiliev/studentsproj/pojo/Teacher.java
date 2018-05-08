package ru.innopolis.vasiliev.studentsproj.pojo;

public class Teacher extends User{
    public Teacher(int user_id,String login, String passwordHash) {
        super(user_id,login, passwordHash, UserType.Teacher);
    }
}
