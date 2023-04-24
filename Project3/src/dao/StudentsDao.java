package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Grade;
import model.Students;

public interface StudentsDao {

    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

    public List<Students> getAllStudents();

    public Students getStudentById(int id);

    public List<Students> getStudentsByClass(int classId);
    
    public boolean addStudent(Students student, int classId, String password);

    public boolean updateStudent(Students student);

    public boolean deleteStudent(int studentId);

    public List<Grade> getStudentGrades(int studentId);
}

