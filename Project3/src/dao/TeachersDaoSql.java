package dao;

import model.Classes;
import model.Grade;
import model.Students;
import model.Teachers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionManager;

public class TeachersDaoSql implements TeachersDao {
    private Connection connection;

    @Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		connection = ConnectionManager.getConnection();
	}

    @Override
    public Teachers getTeacherByUsernameAndPassword(String username, String password) throws SQLException {
        String query = "SELECT * FROM teachers WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }

        Teachers teacher = new Teachers(
                resultSet.getInt("teacher_id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("full_name"),
                resultSet.getString("email"),
                resultSet.getString("phone_number")
        );

        List<Classes> classes = getClassesByTeacherId(teacher.getTeacher_id());
        for (Classes c : classes) {
            List<Grade> grades = getGradesByClassId(c.getClass_id());
            for (Grade g : grades) {
                Students student = getStudentById(g.getStudent_id());
                c.addStudent(student);
            }
            teacher.addClass(c);
        }

        return teacher;
    }

    @Override
    public List<Classes> getClassesByTeacherId(int teacherId) throws SQLException {
        String query = "SELECT * FROM classes WHERE teacher_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, teacherId);

        ResultSet resultSet = statement.executeQuery();

        List<Classes> classes = new ArrayList<>();
        while (resultSet.next()) {
            Classes c = new Classes(
                    resultSet.getInt("class_id"),
                    resultSet.getString("class_name"),
                    resultSet.getString("class_code"),
                    resultSet.getInt("teacher_id")
            );
            classes.add(c);
        }

        return classes;
    }

    @Override
    public List<Grade> getGradesByClassId(int classId) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM grades WHERE class_id = ?")) {
            statement.setInt(1, classId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Grade> gradesList = new ArrayList<>();
                
                while (resultSet.next()) {
                    Grade grades = new Grade();
                    grades.setClass_id(resultSet.getInt("class_id"));
                    grades.setStudent_id(resultSet.getInt("student_id"));
                    grades.setGrade(resultSet.getInt("grade"));
                    gradesList.add(grades);
                }
                return gradesList;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving grades by class id: " + e.getMessage());
        }
    }
    

    @Override
public Students getStudentById(int studentId) throws SQLException {
    String query = "SELECT * FROM students WHERE student_id = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setInt(1, studentId);

    ResultSet resultSet = statement.executeQuery();
    if (!resultSet.next()) {
        return null;
    }

    Students student = new Students(
            resultSet.getInt("student_id"),
            resultSet.getString("student_name"),
            resultSet.getString("email"),
            resultSet.getString("phone_number"),
            resultSet.getInt("class_id")
    );

    return student;
}

    

    @Override
    public void addTeacher(Teachers teacher) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO teachers(teacher_id, username, password, full_name, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, teacher.getTeacher_id());
            statement.setString(2, teacher.getUsername());
            statement.setString(3, teacher.getPassword());
            statement.setString(4, teacher.getFull_name());
            statement.setString(5, teacher.getEmail());
            statement.setString(6, teacher.getPhone_number());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding teacher to database.");
            e.printStackTrace();
        }
    }

    @Override
    public Teachers getTeacherById(int teacherId) {
        Teachers teacher = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM teachers WHERE teacher_id = ?");
            statement.setInt(1, teacherId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                teacher = new Teachers(teacherId, username, password, fullName, email, phoneNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error getting teacher from database.");
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public void updateGrade(int gradeId, int newGrade) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE grades SET grade = ? WHERE grade_id = ?");
            statement.setInt(1, newGrade);
            statement.setInt(2, gradeId);
            statement.executeUpdate();
            System.out.println("Grade updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating grade in database.");
            e.printStackTrace();
        }
    }
}