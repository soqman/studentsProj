package ru.innopolis.vasiliev.studentsproj.pojo;

public class Student extends User{

    public Student(int user_id,String login, int passwordHash) {
        super(user_id,login, passwordHash, UserType.Student);
    }
}
