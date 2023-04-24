package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionManager;
import model.Grade;
import model.Students;

public class StudentsDaoSql implements StudentsDao {
    private Connection connection;

    @Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		connection = ConnectionManager.getConnection();
	}

    @Override
    public List<Students> getAllStudents() {
        List<Students> studentsList = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                String studentEmail = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                int classId = rs.getInt("class_id");
                Students student = new Students(studentId, studentName, studentEmail, phoneNumber, classId);
                List<Grade> gradeList = getStudentGrades(studentId);
                for (Grade g : gradeList) {
                    student.getGrade().addGrade(g);
                }
                studentsList.add(student);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return studentsList;
    }

    @Override
    public Students getStudentById(int id) {
        Students student = null;
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = new Students();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setStudentName(resultSet.getString("student_name"));
                student.setStudentEmail(resultSet.getString("email"));
                // set any other properties of the student object that you need
            }
        } catch (SQLException e) {
            // handle the exception appropriately
            System.out.println("Error getting student by id");
        }
        return student;
    }

    @Override
    public List<Students> getStudentsByClass(int classId) {
        List<Students> studentsList = new ArrayList<>();
        String query = "SELECT * FROM students WHERE class_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                String studentEmail = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                Students student = new Students(studentId, studentName, studentEmail, phoneNumber, classId);
                List<Grade> gradeList = getStudentGrades(studentId);
                for (Grade g : gradeList) {
                    student.getGrade().addGrade(g);
                }
                studentsList.add(student);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return studentsList;
    }

    @Override
    public boolean addStudent(Students student, int classId, String password) {
        String query = "INSERT INTO students (username, password, student_name, email, phone_number, class_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getStudentEmail());
            statement.setString(2, password);
            statement.setString(3, student.getStudentName());
            statement.setString(4, student.getStudentEmail());
            statement.setString(5, student.getPhoneNumber());
            statement.setInt(6, classId);
    
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    


    @Override
    public boolean updateStudent(Students student) {
        String query = "UPDATE students SET student_name = ?, email = ?, class_id = ? WHERE student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getStudentName());
            pstmt.setString(2, student.getStudentEmail());
            pstmt.setInt(3, student.getClassId());
            pstmt.setInt(4, student.getStudentId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
            GradeDao gradeDao = new GradeDaoSql();
            for (Grade grade : student.getGrades()) {
                gradeDao.updateGrade(grade);
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteStudent(int studentId) {
        try {
            connection.setAutoCommit(false);
            String deleteGradesQuery = "DELETE FROM grades WHERE student_id = ?";
            PreparedStatement pstmt1 = connection.prepareStatement(deleteGradesQuery);
            pstmt1.setInt(1, studentId);
            pstmt1.executeUpdate();
            pstmt1.close();
            
            String deleteClassStudentsQuery = "DELETE FROM class_students WHERE student_id = ?";
            PreparedStatement pstmt2 = connection.prepareStatement(deleteClassStudentsQuery);
            pstmt2.setInt(1, studentId);
            pstmt2.executeUpdate();
            pstmt2.close();
            
            String deleteStudentQuery = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement pstmt3 = connection.prepareStatement(deleteStudentQuery);
            pstmt3.setInt(1, studentId);
            int rowsAffected = pstmt3.executeUpdate();
            pstmt3.close();
            
            if (rowsAffected == 0) {
                connection.rollback();
                return false;
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
    
    
    

    @Override
public List<Grade> getStudentGrades(int studentId) {
    List<Grade> gradeList = new ArrayList<>();
    String query = "SELECT * FROM grades WHERE student_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, studentId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int gradeId = rs.getInt("grade_id");
                int classId = rs.getInt("class_id");
                int grade = rs.getInt("grade");
                String assignmentName = rs.getString("assignment_name");
                Grade g = new Grade(gradeId, studentId, classId, grade, assignmentName);
                gradeList.add(g);
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return gradeList;
}

}    