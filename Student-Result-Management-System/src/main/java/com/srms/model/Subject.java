package com.srms.model;

/** Represents one row of the subjects table. */
public class Subject {

    private int id;
    private String code;
    private String name;
    private int maxMarks;

    public Subject() { }

    public Subject(String code, String name, int maxMarks) {
        this.code = code;
        this.name = name;
        this.maxMarks = maxMarks;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMaxMarks() { return maxMarks; }
    public void setMaxMarks(int maxMarks) { this.maxMarks = maxMarks; }

    @Override
    public String toString() {
        return String.format("%-4d %-10s %-28s %d", id, code, name, maxMarks);
    }
}
