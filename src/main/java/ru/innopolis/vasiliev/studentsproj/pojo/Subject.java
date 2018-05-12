package ru.innopolis.vasiliev.studentsproj.pojo;

public class Subject{
private int subjectId;
private String name;
private int teacherId;

    public Subject(int subjectId, String name, int teacherId) {
        this.subjectId = subjectId;
        this.name = name;
        this.teacherId = teacherId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
