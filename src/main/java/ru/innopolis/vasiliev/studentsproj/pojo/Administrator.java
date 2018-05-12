package ru.innopolis.vasiliev.studentsproj.pojo;

class Administrator extends User {
    public Administrator(int userId,String login, String passwordHash) {
        super(userId,login, passwordHash, UserType.Administrator);
    }
}
