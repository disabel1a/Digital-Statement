package model.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.dao.UsersFileManager;
import model.users.Student;
import model.users.Teacher;

public class NamedUserConrtollerTest {
    private static NamedUserController controller;
    private static Teacher teacher;
    private static Student student;
    private static String mainFolder;
    private static UsersFileManager fileManager;

    @BeforeAll
    public static void init() {
        mainFolder = "src\\test\\java\\model\\dao\\data";

        ArrayList<String> subjects = new ArrayList<>();
        subjects.add("Физика");
        subjects.add("Атомная энергетика");

        teacher = new Teacher("disabel1a", "3505", "Иван", "Макаренко", "Иванович", subjects);
        student = new Student("ivmak", "3505", "Иван", "Макаренко", "Андреевич", "5123");
        controller = new NamedUserController(mainFolder);
        fileManager = new UsersFileManager(mainFolder);

        try {
            ArrayList<String> statements = ((Teacher) fileManager.loadUserInfoByLogin(teacher.getLogin())).getStatements();
            teacher.setStatements(statements);

            statements = ((Student) fileManager.loadUserInfoByLogin(student.getLogin())).getStatements();
            student.setStatements(statements);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getUserInfoTestTeacher() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        Teacher expected = (Teacher) new UsersFileManager(mainFolder).loadUserInfoByLogin(teacher.getLogin());
        Teacher actual = (Teacher) controller.getUserInfo(teacher.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    public void refreshStatementsTestTeacher() throws IOException {
        Teacher expected = teacher;
        controller.refreshStatements(teacher);
        Teacher actual = (Teacher) controller.getUserInfo(teacher.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    public void getUserClassTestTeacher() {
        Class<?> expected = Teacher.class;
        Class<?> actual = controller.getUserClass(teacher);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserClassTestStudent() {
        Class<?> expected = Student.class;
        Class<?> actual = controller.getUserClass(student);

        assertEquals(expected, actual);
    }
}
