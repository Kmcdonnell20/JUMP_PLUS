package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Grade;

public interface GradeDao {

    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    
    public boolean addGrade(Grade grade);
    
    public boolean deleteGrade(int gradeId);
    
    public boolean updateGrade(Grade grade);
    
    public List<Grade> getAllGrades();
    
    public List<Grade> getGradesByStudentId(int studentId);
    
    public List<Grade> getGradesByClassId(int classId);

    public List<Grade> getStudentGrades(int studentId);

    public boolean deleteGradesForStudent(int studentId);

    public Grade getGradeByStudentAndClass(int studentId, int classId);

    public double getMedianGradeByClassId(int classId);

    public double getAverageGradeByClassId(int classId);


}

