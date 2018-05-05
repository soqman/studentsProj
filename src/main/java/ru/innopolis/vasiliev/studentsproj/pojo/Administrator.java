package ru.innopolis.vasiliev.studentsproj.pojo;

public class Administrator extends User {
    public Administrator(int user_id,String login, int passwordHash) {
        super(user_id,login, passwordHash, UserType.Administrator);
    }
}
