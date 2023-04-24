package model;

import java.util.ArrayList;
import java.util.List;

public class Students {
    private int student_id;
    private String student_name;
    private String email;
    private Grade grade;
    private int class_id;
    private String phoneNumber;
    private ArrayList<Grade> grades;


    public Students(int student_id, String student_name, String email, String phoneNumber, int class_id) {
        this.student_id = student_id;
        this.student_name = student_name;
        this.email = email;
        this.grade = new Grade(0, student_id, class_id, 0, "");
        this.phoneNumber = phoneNumber;
        this.class_id = class_id;
        this.grades = new ArrayList<>();
    }
    

    public Students() {
    }

    public int getStudent_id() {
        return this.student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return this.student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getClass_id() {
        return this.class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }


    public int getStudentId() {
        return student_id;
    }

    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }

    public String getStudentName() {
        return student_name;
    }

    public void setStudentName(String student_name) {
        this.student_name = student_name;
    }

    public String getStudentEmail() {
        return email;
    }

    public void setStudentEmail(String student_email) {
        this.email = email;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public int getClassId() {
        return class_id;
    }

    public void setClassId(int class_id) {
        this.class_id = class_id;
    }

    public Grade getGradeByClassId(int class_id, List<Grade> gradeList) {
        for (Grade g : gradeList) {
            if (g.getClass_id() == class_id) {
                return g;
            }
        }
        return null;
    }
    
    public List<Grade> getGrades() {
        return this.grades;
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

}
