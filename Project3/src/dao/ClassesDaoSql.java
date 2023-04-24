package dao;

import model.Classes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionManager;

public class ClassesDaoSql implements ClassesDao {

    private Connection connection;

    @Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		connection = ConnectionManager.getConnection();
	}

    @Override
    public void addClasses(Classes classes) {
        String query = "INSERT INTO classes (class_name, class_code, teacher_id) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, classes.getClass_name());
            ps.setString(2, classes.getClass_code());
            ps.setInt(3, classes.getTeacher_id());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClasses(Classes classes) {
        String query = "UPDATE classes SET class_name=?, class_code=?, teacher_id=? WHERE class_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, classes.getClass_name());
            ps.setString(2, classes.getClass_code());
            ps.setInt(3, classes.getTeacher_id());
            ps.setInt(4, classes.getClass_id());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClasses(int class_id) {
        String query = "DELETE FROM classes WHERE class_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, class_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Classes getClassesById(int class_id) {
        Classes classes = null;
        String query = "SELECT * FROM classes WHERE class_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, class_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String class_name = rs.getString("class_name");
                String class_code = rs.getString("class_code");
                int teacher_id = rs.getInt("teacher_id");
                classes = new Classes(class_id, class_name, class_code, teacher_id);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    @Override
    public List<Classes> getAllClasses() {
        List<Classes> classesList = new ArrayList<>();
        String query = "SELECT * FROM classes";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int class_id = rs.getInt("class_id");
                String class_name = rs.getString("class_name");
                String class_code = rs.getString("class_code");
                int teacher_id = rs.getInt("teacher_id");
                Classes classes = new Classes(class_id, class_name, class_code, teacher_id);
                classesList.add(classes);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classesList;
    }

    @Override
    public List<Classes> getClassesByTeacherId(int teacherId) {
        String query = "SELECT * FROM classes WHERE teacher_id = ?";
        List<Classes> classes = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, teacherId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int classId = rs.getInt("class_id");
                String className = rs.getString("class_name");
                String classCode = rs.getString("class_code");
                int teacherIdFromDB = rs.getInt("teacher_id");
                Classes c = new Classes(classId, className, classCode, teacherIdFromDB);
                classes.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return classes;
    }
    
    

}
