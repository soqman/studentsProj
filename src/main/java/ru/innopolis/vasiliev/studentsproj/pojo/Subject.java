package ru.innopolis.vasiliev.studentsproj.pojo;

public class Subject{
private int subject_id;
private String name;
private int teacher_id;

    public Subject(int subject_id, String name, int teacher_id) {
        this.subject_id = subject_id;
        this.name = name;
        this.teacher_id = teacher_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
