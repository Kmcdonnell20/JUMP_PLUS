package model;

import java.util.ArrayList;
import java.util.List;

public class Classes {
    
    private int class_id;
    private String class_name;
    private String class_code;
    private int teacher_id;
    private List<Students> students;
    private List<Grade> grade;

    public Classes(int class_id, String class_name, String class_code, int teacher_id) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_code = class_code;
        this.teacher_id = teacher_id;
        this.students = new ArrayList<>();
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void addStudent(Students student) {
        this.students.add(student);
    }
    
    public List<Students> getStudents() {
        return students;
    }

    public List<Grade> getGrade() {
        return grade;
    }
    
    public void displayStudents(List<Grade> gradeList) {
        if (students.isEmpty()) {
            System.out.println("There are no students in this class.");
        } else {
            System.out.println("Students in " + class_name + ":");
            for (Students s : students) {
                Grade grade = s.getGradeByClassId(class_id, gradeList);
                if (grade == null) {
                    System.out.println("- " + s.getStudentName() + " (no grade)");
                } else {
                    System.out.println("- " + s.getStudentName() + " (" + grade.getGrade() + ")");
                }
            }
        }
    }

}
