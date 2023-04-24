package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Classes;

public interface ClassesDao {

    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
    
    void addClasses(Classes classes);

    void updateClasses(Classes classes);

    void deleteClasses(int class_id);

    Classes getClassesById(int class_id);
    
    List<Classes> getAllClasses();

    public List<Classes> getClassesByTeacherId(int teacherId);

}
