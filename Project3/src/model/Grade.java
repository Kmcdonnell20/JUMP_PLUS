package model;

import java.util.ArrayList;
import java.util.List;

public class Grade {
    
    private int grade_id;
    private int student_id;
    private int class_id;
    private int grade;
    private String assignment_name;
    private List<Grade> gradeList;

    public Grade(int grade_id, int student_id, int class_id, int grade, String assignment_name) {
        this.grade_id = grade_id;
        this.student_id = student_id;
        this.class_id = class_id;
        this.grade = grade;
        this.gradeList = new ArrayList<>();
        this.assignment_name = assignment_name;
    }

    public Grade(Grade grade) {
        gradeList.add(grade);
    }

    public Grade() {
    }

    public int getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(int grade_id) {
        this.grade_id = grade_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getAssignment_name() {
        return assignment_name;
    }

    public void setAssignment_name(String assignment_name) {
         this.assignment_name = assignment_name;
    }

    public void addGrade(Grade grade) {
        gradeList.add(grade);
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }
    

    public void showGrade() {
        System.out.println("Grade ID: " + grade_id);
        System.out.println("Student ID: " + student_id);
        System.out.println("Class ID: " + class_id);
        System.out.println("Assignment Name: " + assignment_name);
        System.out.println("Grade: " + grade);
    }
}
