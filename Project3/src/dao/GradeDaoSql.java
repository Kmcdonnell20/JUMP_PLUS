package dao;

import model.Grade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import connection.ConnectionManager;

public class GradeDaoSql implements GradeDao {

    private Connection connection;

    @Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		connection = ConnectionManager.getConnection();
	}


    @Override
    public List<Grade> getGradesByStudentId(int studentId) {
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
                    Grade gradeObj = new Grade(gradeId, studentId, classId, grade, assignmentName);
                    gradeList.add(gradeObj);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return gradeList;
    }

    @Override
    public boolean addGrade(Grade grade) {
        String query = "INSERT INTO grades (student_id, class_id, grade, assignment_name) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, grade.getStudent_id());
            pstmt.setInt(2, grade.getClass_id());
            pstmt.setInt(3, grade.getGrade());
            pstmt.setString(4, grade.getAssignment_name());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateGrade(Grade grade) {
        String query = "UPDATE grades SET class_id = ?, grade = ?, assignment_name = ? WHERE grade_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, grade.getClass_id());
            pstmt.setInt(2, grade.getGrade());
            pstmt.setString(3, grade.getAssignment_name());
            pstmt.setInt(4, grade.getGrade_id());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteGrade(int gradeId) {
        String query = "DELETE FROM grades WHERE grade_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, gradeId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
public List<Grade> getAllGrades() {
    List<Grade> gradeList = new ArrayList<>();
    String query = "SELECT * FROM grades";
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        while (rs.next()) {
            int gradeId = rs.getInt("grade_id");
            int studentId = rs.getInt("student_id");
            int classId = rs.getInt("class_id");
            int grade = rs.getInt("grade");
            String assignmentName = rs.getString("assignment_name");
            Grade gradeObj = new Grade(gradeId, studentId, classId, grade, assignmentName);
            gradeList.add(gradeObj);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return gradeList;
}

@Override
public List<Grade> getGradesByClassId(int classId) {
    List<Grade> gradeList = new ArrayList<>();
    String query = "SELECT * FROM grades WHERE class_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, classId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int gradeId = rs.getInt("grade_id");
                int studentId = rs.getInt("student_id");
                int grade = rs.getInt("grade");
                String assignmentName = rs.getString("assignment_name");
                Grade gradeObj = new Grade(gradeId, studentId, classId, grade, assignmentName);
                gradeList.add(gradeObj);
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return gradeList;
}

@Override
public List<Grade> getStudentGrades(int studentId) {
    List<Grade> gradeList = new ArrayList<>();
    String query = "SELECT * FROM grades WHERE student_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, studentId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            int gradeId = rs.getInt("grade_id");
            int classId = rs.getInt("class_id");
            int grade = rs.getInt("grade");
            String assignmentName = rs.getString("assignment_name");
            Grade gradeObj = new Grade(gradeId, studentId, classId, grade, assignmentName);
            gradeList.add(gradeObj);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return gradeList;
}

@Override
public boolean deleteGradesForStudent(int studentId) {
    String query = "DELETE FROM grades WHERE student_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, studentId);
        pstmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.err.println("Error deleting grades for student: " + e.getMessage());
        return false;
    }
}


@Override
public Grade getGradeByStudentAndClass(int studentId, int classId) {
    String query = "SELECT * FROM grades WHERE student_id = ? AND class_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, studentId);
        pstmt.setInt(2, classId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int grade_id = rs.getInt("grade_id");
            int grade = rs.getInt("grade");
            String assignment_name = rs.getString("assignment_name");
            return new Grade(grade_id, studentId, classId, grade, assignment_name);
        } else {
            return null;
        }
    } catch (SQLException e) {
        System.err.println("Error getting grade by student and class: " + e.getMessage());
        return null;
    }

}

    @Override
    public double getAverageGradeByClassId(int classId) {
        double average = 0;
        int count = 0;
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT grade FROM grades WHERE class_id = ?")) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                average += rs.getInt("grade");
                count++;
            }
            if (count > 0) {
                average /= count;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return average;
    }

    @Override
    public double getMedianGradeByClassId(int classId) {
        double median = 0;
        List<Integer> grades = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT grade FROM grades WHERE class_id = ?")) {
            pstmt.setInt(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                grades.add(rs.getInt("grade"));
            }
            if (!grades.isEmpty()) {
                Collections.sort(grades);
                int middle = grades.size() / 2;
                if (grades.size() % 2 == 0) {
                    median = (grades.get(middle - 1) + grades.get(middle)) / 2.0;
                } else {
                    median = grades.get(middle);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return median;
    }


}

    
   
