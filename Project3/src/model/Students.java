package model;

public class Students {
    
    private int student_id;
    private String first_name;
    private String last_name;
    private int class_id;


    public Students(int student_id, String first_name, String last_name, int class_id) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.class_id = class_id;
    }


    public int getStudent_id() {
        return this.student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getClass_id() {
        return this.class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }



}
