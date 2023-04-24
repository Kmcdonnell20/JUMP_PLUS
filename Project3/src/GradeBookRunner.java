import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import connection.ConnectionManager;
import dao.ClassesDao;
import dao.ClassesDaoSql;
import dao.GradeDao;
import dao.GradeDaoSql;
import dao.StudentsDao;
import dao.StudentsDaoSql;
import dao.TeachersDao;
import dao.TeachersDaoSql;
import model.Classes;
import model.Grade;
import model.Students;
import model.Teachers;

public class GradeBookRunner {

    private static TeachersDao teacherDao;

    private static ClassesDao classesDao;

    private static StudentsDao studentsDao;

    private static GradeDao gradeDao;

    public static void main(String[] args) throws SQLException {

        try {

            Scanner scanner = new Scanner(System.in);

            teacherDao = new TeachersDaoSql();
            teacherDao.setConnection();

            classesDao = new ClassesDaoSql();
            classesDao.setConnection();

            studentsDao = new StudentsDaoSql();
            studentsDao.setConnection();

            gradeDao = new GradeDaoSql();
            gradeDao.setConnection();


        boolean loggedIn = false;
        int teacherId = -1;

        while (!loggedIn) {
            System.out.println("Welcome to the gradebook application. Would you like to login or register? (login/register)");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("login")) {
                System.out.println("Please enter your username: ");
                String username = scanner.nextLine().trim();

                System.out.println("Please enter your password:");
                String password = scanner.nextLine().trim();

                Teachers teacher = teacherDao.getTeacherByUsernameAndPassword(username, password);
                if (teacher != null) {
                    System.out.println();
                    System.out.println("Login successful. Welcome, " + teacher.getFull_name() + ".");
                    System.out.println();
                    loggedIn = true;
                    teacherId = teacher.getTeacher_id();
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } else if (choice.equals("register")) {
                System.out.println("Please enter your full name:");
                String fullName = scanner.nextLine().trim();
            
                System.out.println("Please enter your email address:");
                String email = scanner.nextLine().trim();
            
                System.out.println("Please enter your phone number:");
                String phoneNumber = scanner.nextLine().trim();
            
                System.out.println("Please choose a username:");
                String username = scanner.nextLine().trim();
            
                System.out.println("Please choose a password:");
                String password = scanner.nextLine().trim();
            
                Teachers newTeacher = new Teachers(0, username, password, fullName, email, phoneNumber);
                teacherDao.addTeacher(newTeacher);
                System.out.println("Registration successful. Please login to continue.");
            
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        boolean running = true;

        while (running) {
            System.out.println("Please select an option:");
            System.out.println("1. Create a new class");
            System.out.println("2. View classes");
            System.out.println("3. Logout");
            // System.out.println();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume new line character
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.println("Please enter the name of the new class:");
                    String className = scanner.nextLine().trim();

                    System.out.println("Please enter the class code for the new class:");
                    String classCode = scanner.nextLine().trim();

                    Classes newClass = new Classes(0, className, classCode, teacherId);
                    classesDao.addClasses(newClass);
                    System.out.println("Class created successfully.");
                    break;

                    case 2:
                    List<Classes> classesList = classesDao.getClassesByTeacherId(teacherId);
                
                    if (classesList.isEmpty()) {
                        System.out.println("You are not currently teaching any classes.");
                    } else {
                        System.out.println("Please select a class:");
                
                        for (int i = 0; i < classesList.size(); i++) {
                            System.out.println((i+1) + ". " + classesList.get(i).getClass_name());
                        }
                
                        int classChoice = scanner.nextInt();
                        scanner.nextLine(); // consume new line character
                
                        if (classChoice < 1 || classChoice > classesList.size()) {
                            System.out.println("Invalid choice. Please try again.");
                            break;
                        }
                
                        Classes selectedClass = classesList.get(classChoice-1);
                        List<Students> studentsList = studentsDao.getStudentsByClass(selectedClass.getClass_id());
                        List<Grade> gradeList = gradeDao.getGradesByClassId(selectedClass.getClass_id());
                        
                        // Add the following snippet to view the grades by class ID
                        System.out.println("Grades in " + selectedClass.getClass_name() + ":");
                        for (Grade grade : gradeList) {
                            Students student = studentsDao.getStudentById(grade.getStudent_id());
                            if (student != null) {
                                System.out.println(student.getStudentName() + " - Grade: " + grade.getGrade());
                            } else {
                                System.out.println("Student not found for ID: " + grade.getStudent_id());
                            }
                        }
                        
                        
                        boolean classRunning = true;
                        while (classRunning) {
                            System.out.println();
                            System.out.println("Please select an option:");
                            System.out.println("1. View students");
                            System.out.println("2. Add student");
                            System.out.println("3. Remove student");
                            System.out.println("4. Update student grade");
                            System.out.println("5. View average and median grade");
                            System.out.println("6. View students sorted by name");
                            System.out.println("7. View students sorted by grade");
                            System.out.println("8. Back");
                            // System.out.println();
                            int classOption = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println();
                
                            switch (classOption) {
                                case 1:
                                System.out.println("Students in " + selectedClass.getClass_name() + ":");
                                for (Students student : studentsList) {
                                    Grade grade = gradeDao.getGradeByStudentAndClass(student.getStudentId(), selectedClass.getClass_id());
                                    if (grade != null) {
                                        System.out.println(student.getStudentName() + " - Grade: " + grade.getGrade());
                                    } else {
                                        System.out.println(student.getStudentName() + " - Grade: N/A");
                                    }
                                }
                                break;
                            

                                    case 2:
                                    System.out.println("Please enter the name of the new student:");
                                    String studentName = scanner.nextLine().trim();
                                    System.out.println("Please enter the email of the new student:");
                                    String studentEmail = scanner.nextLine().trim();
                                    System.out.println("Please enter the password for the new student:");
                                    String password = scanner.nextLine().trim();
                                    System.out.println("Please enter the class ID:");
                                    int classId = scanner.nextInt();
                                    System.out.println("Please enter the phone number for the new student:");
                                    String phoneNumber = scanner.nextLine().trim();
                                    scanner.nextLine(); // Consume the newline character

                                    Classes selectedClassById = classesDao.getClassesById(classId);
                                    if (selectedClassById == null) {
                                        System.out.println("Invalid class ID.");
                                        break;
                                    }

                                    Students newStudent = new Students(0, studentName, studentEmail, phoneNumber, selectedClass.getClass_id());
                                    if (studentsDao.addStudent(newStudent, selectedClass.getClass_id(), password)) {
                                        System.out.println("Student added successfully.");
                                        studentsList = studentsDao.getStudentsByClass(selectedClass.getClass_id());
                                    } else {
                                        System.out.println("Error adding student.");
                                    }
                                    break;



                                    case 3:
                                    System.out.println("Please select a student to remove:");
                                    for (int i = 0; i < studentsList.size(); i++) {
                                        System.out.println((i+1) + ". " + studentsList.get(i).getStudentName());
                                    }
                                    int studentChoice = scanner.nextInt();
                                    scanner.nextLine(); // consume new line character
                                    if (studentChoice < 1 || studentChoice > studentsList.size()) {
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                    }
                                    Students studentToRemove = studentsList.get(studentChoice-1);
                                    if (studentsDao.deleteStudent(studentToRemove.getStudentId())) {
                                        studentsList.remove(studentToRemove);
                                        System.out.println("Student removed successfully.");
                                    } else {
                                        System.out.println("Error removing student. Please try again.");
                                    }
                                    break;
                            
                                case 4:
                                System.out.println("Please select a student to update:");
                            
                                for (int i = 0; i < studentsList.size(); i++) {
                                    System.out.println((i+1) + ". " + studentsList.get(i).getStudentName());
                                }
                            
                                int studentToUpdate = scanner.nextInt();
                                scanner.nextLine(); // consume new line character
                            
                                if (studentToUpdate < 1 || studentToUpdate > studentsList.size()) {
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                                }
                            
                                Students selectedStudent = studentsList.get(studentToUpdate-1);
                                System.out.println("Please enter the new grade for " + selectedStudent.getStudentName() + ":");
                                int newGrade = scanner.nextInt();
                                scanner.nextLine(); // consume new line character
                            
                                Grade existingGrade = gradeDao.getGradeByStudentAndClass(selectedStudent.getStudentId(), selectedClass.getClass_id());
                            
                                if (existingGrade != null) {
                                    existingGrade.setGrade(newGrade);
                                    gradeDao.updateGrade(existingGrade);
                                } else {
                                    Grade newGradeObject = new Grade(0, selectedStudent.getStudentId(), selectedClass.getClass_id(), newGrade, "");
                                    gradeDao.addGrade(newGradeObject);
                                }
                            
                                System.out.println("Grade updated successfully.");
                                break;
                            
                            case 5:
                                double average = gradeDao.getAverageGradeByClassId(selectedClass.getClass_id());
                                double median = gradeDao.getMedianGradeByClassId(selectedClass.getClass_id());

                                System.out.println("Average grade: " + average);
                                System.out.println("Median grade: " + median);
                                break;

                                case 6:
                          Collections.sort(studentsList, new Comparator<Students>() {
                              @Override
                              public int compare(Students s1, Students s2) {
                                  return s1.getStudentName().compareTo(s2.getStudentName());
                              }
                          });

                          System.out.println("Students in " + selectedClass.getClass_name() + " sorted by name:");
                          for (Students student : studentsList) {
                              System.out.println(student.getStudentName() + " - Grade: " +
                                      gradeDao.getGradeByStudentAndClass(student.getStudentId(),
                                              selectedClass.getClass_id()).getGrade());
                          }
                          break;

                      case 7:
                          Collections.sort(studentsList, new Comparator<Students>() {
                              @Override
                              public int compare(Students s1, Students s2) {
                                  Grade g1 = gradeDao.getGradeByStudentAndClass(s1.getStudentId(),
                                          selectedClass.getClass_id());
                                  Grade g2 = gradeDao.getGradeByStudentAndClass(s2.getStudentId(),
                                          selectedClass.getClass_id());

                                  if (g1 == null || g2 == null) {
                                      return 0;
                                  }

                                  return Integer.compare(g1.getGrade(), g2.getGrade());
                              }
                          });

                          System.out.println("Students in " + selectedClass.getClass_name() + " sorted by grade:");
                          for (Students student : studentsList) {
                              System.out.println(student.getStudentName() + " - Grade: " +
                                      gradeDao.getGradeByStudentAndClass(student.getStudentId(),
                                              selectedClass.getClass_id()).getGrade());
                          }
                          break;

                      case 8:
                          classRunning = false;
                          break;

                      default:
                          System.out.println("Invalid option. Please try again.");
                  }
              }
          }
          break;

      case 3:
          System.out.println("Goodbye!");
          running = false;
          break;

      default:
          System.out.println("Invalid option. Please try again.");
  }
    }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }}

