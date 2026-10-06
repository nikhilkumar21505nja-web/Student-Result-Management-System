package com.srms.model;

/** Represents one row of the students table. */
public class Student {

    private int id;
    private String rollNo;
    private String name;
    private String email;
    private String className;

    public Student() { }

    public Student(String rollNo, String name, String email, String className) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.className = className;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    @Override
    public String toString() {
        return String.format("%-4d %-10s %-22s %-26s %s", id, rollNo, name,
                email == null ? "-" : email, className);
    }
}
