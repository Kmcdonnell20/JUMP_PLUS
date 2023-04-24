package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Teachers {
    
    private int teacher_id;
    private String username;
    private String password;
    private String full_name;
    private String email;
    private String phone_number;
    private List<Classes> classes;

    public Teachers(int teacher_id, String username, String password, String full_name, String email, String phone_number) {
        this.teacher_id = teacher_id;
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.classes = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone_number() {
        return phone_number;
    }
    
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void addClass(Classes classes) {
        this.classes.add(classes);
    }
    
    public List<Classes> getClasses() {
        return classes;
    }
    
    public void createClass(int class_id, String class_name, String class_code) {
        Classes newClass = new Classes(class_id, class_name, class_code, teacher_id);
        this.classes.add(newClass);
        System.out.println("Class created successfully.");
    }
    
    
    public void viewClasses() {
        if (classes.isEmpty()) {
            System.out.println("You are not teaching any classes.");
        } else {
            System.out.println("Your classes:");
            for (Classes c : classes) {
                System.out.println("- " + c.getClass_name());
            }
        }
    }

    public void selectClass(String className, Teachers teacher) {
        Classes selectedClass = null;
        for (Classes c : classes) {
            if (c.getClass_name().equalsIgnoreCase(className)) {
                selectedClass = c;
                break;
            }
        }
        if (selectedClass == null) {
            System.out.println("Class not found.");
        } else {
            List<Grade> gradeList = teacher.getGradeList(selectedClass.getClass_id());
            selectedClass.displayStudents(gradeList);
        }
    }
    
    public List<Grade> getGradeList(int classId) {
        for (Classes c : classes) {
            if (c.getClass_id() == classId) {
                return c.getGrade();
            }
        }
        return null;
    }

    public void sortStudentsByAlphabeticalOrder(List<Students> students) {
        Collections.sort(students, Comparator.comparing(Students::getStudentName));
        displayStudents(students);
    }
    
    public void sortStudentsByGrade(List<Grade> grades) {
        Collections.sort(grades, Comparator.comparingInt(Grade::getGrade));
        displayGrades(grades);
    }

    public void displayGrades(List<Grade> grades) {
        System.out.printf("%-10s %-15s %-15s %-10s %-20s\n", "Grade ID", "Student ID", "Class ID", "Grade", "Assignment Name");
        for (Grade grade : grades) {
            System.out.printf("%-10d %-15d %-15d %-10d %-20s\n", grade.getGrade_id(), grade.getStudent_id(), grade.getClass_id(), grade.getGrade(), grade.getAssignment_name());
        }
    }
    
    public void calculateClassAverage(List<Grade> grades) {
        if (grades.isEmpty()) {
            System.out.println("There are no grades for this class.");
            return;
        }
        
        double sum = 0;
        for (Grade g : grades) {
            sum += g.getGrade();
        }
        double average = sum / grades.size();
        
        System.out.println("Class average: " + average);
    }
    
    public void calculateClassMedian(List<Grade> grades) {
        if (grades.isEmpty()) {
            System.out.println("There are no grades for this class.");
            return;
        }
        
        int size = grades.size();
        List<Integer> sortedGrades = new ArrayList<>();
        for (Grade g : grades) {
            sortedGrades.add(g.getGrade());
        }
        
        Collections.sort(sortedGrades);
        
        double median;
        if (size % 2 == 0) {
            median = (double) (sortedGrades.get(size/2 - 1) + sortedGrades.get(size/2)) / 2;
        } else {
            median = sortedGrades.get(size/2);
        }
        
        System.out.println("Class median: " + median);
    }
    
    
    public void displayStudents(List<Students> students) {
        if (students.isEmpty()) {
            System.out.println("There are no students in this class.");
        } else {
            System.out.println("List of students:");
            for (Students s : students) {
                System.out.println("- " + s.getStudentName() + ", Grade: " + s.getGrade());
            }
        }
    }
}
