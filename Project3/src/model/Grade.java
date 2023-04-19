package model;

public class Grade {
    
    private int grade_int;
    private int student_id;
    private int class_id;
    private int grade;


    public Grade(int grade_int, int student_id, int class_id, int grade) {
        this.grade_int = grade_int;
        this.student_id = student_id;
        this.class_id = class_id;
        this.grade = grade;
    }


    public int getGrade_int() {
        return this.grade_int;
    }

    public void setGrade_int(int grade_int) {
        this.grade_int = grade_int;
    }

    public int getStudent_id() {
        return this.student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getClass_id() {
        return this.class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }



}
