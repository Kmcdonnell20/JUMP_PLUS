package model;

public class Class {
    
    private int class_id;
    private int teacher_id;
    private String class_name;
    private String class_code;


    public Class(int class_id, int teacher_id, String class_name, String class_code) {
        this.class_id = class_id;
        this.teacher_id = teacher_id;
        this.class_name = class_name;
        this.class_code = class_code;
    }



    public int getClass_id() {
        return this.class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getTeacher_id() {
        return this.teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getClass_name() {
        return this.class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_code() {
        return this.class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }


}
