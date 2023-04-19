package model;

public class Teachers {
    
    private int teacher_id;
    private String username;
    private String full_name;
    private String email;


    public Teachers(int teacher_id, String username, String full_name, String email) {
        this.teacher_id = teacher_id;
        this.username = username;
        this.full_name = full_name;
        this.email = email;
    }


    public int getTeacher_id() {
        return this.teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return this.full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
