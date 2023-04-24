package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Classes;
import model.Grade;
import model.Students;
import model.Teachers;

public interface TeachersDao {

    public  void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

    public Teachers getTeacherByUsernameAndPassword(String username, String password) throws SQLException ;

    public List<Classes> getClassesByTeacherId(int teacherId) throws SQLException;

    public List<Grade> getGradesByClassId(int classId) throws SQLException;
    
    public Students getStudentById(int studentId) throws SQLException;

    public void addTeacher(Teachers teacher);

    public Teachers getTeacherById(int teacherId);

    public void updateGrade(int gradeId, int newGrade);

}
