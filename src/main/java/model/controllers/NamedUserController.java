package model.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import model.dao.TableFileManager;
import model.dao.UsersFileManager;
import model.users.NamedUser;
import model.users.Student;
import model.users.Teacher;

public class NamedUserController {
    private UsersFileManager usersManager;
    private TableFileManager tableManager;

    public NamedUserController(String mainFolder) {
        this.usersManager = new UsersFileManager(mainFolder);
        this.tableManager = new TableFileManager(mainFolder);
    }

    public Object getUserInfo(String login) {
        try {
            return usersManager.loadUserInfoByLogin(login);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refreshStatements(NamedUser namedUser) throws IOException {
        ArrayList<String> statements = new ArrayList<>();
        Class<?> userClass = getUserClass(namedUser);
        if (userClass.equals(Student.class)) {
            Student student = (Student) namedUser;
            statements = (ArrayList<String>) tableManager.getStatementsByPointer(student.getGroup());
            namedUser.setStatements(statements);
            usersManager.saveUserInfo((Student) namedUser);
        } else if (userClass.equals(Teacher.class)) {
            Teacher teacher = (Teacher) namedUser;
            for (String subject : teacher.getSubjects()) {
                statements.addAll((ArrayList<String>) tableManager.getStatementsByPointer(subject));
            }
            namedUser.setStatements(statements);
            usersManager.saveUserInfo((Teacher) namedUser);
        }
    }

    public Class<?> getUserClass(NamedUser namedUser) {
        Class<?> namedUserClass = namedUser.getClass();
        return namedUserClass;
    }
}
