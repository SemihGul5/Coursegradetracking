package com.example.coursegradetracking.Model;

public class Course {
    private String eMail;
    private String courseName;
    private String courseCredits;
    private String courseNotes;

    public Course(String eMail, String courseName, String courseCredits, String courseNotes) {
        this.eMail = eMail;
        this.courseName = courseName;
        this.courseCredits = courseCredits;
        this.courseNotes = courseNotes;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(String courseCredits) {
        this.courseCredits = courseCredits;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }
}
