package model.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import model.dao.TableFileManager;
import model.dao.UsersFileManager;
import model.users.NamedUser;
import model.users.Student;
import model.users.Teacher;
import model.users.User;

public class NamedUserController {
    private NamedUser namedUser;
    private UsersFileManager usersManager;
    private TableFileManager tableManager;

    public NamedUserController(String mainFolder) {
        this.usersManager = new UsersFileManager(mainFolder);
        this.tableManager = new TableFileManager(mainFolder);
        this.namedUser = null;
    }

    public void setNamedUser(User user) {
        try {
            this.namedUser = (NamedUser) usersManager.loadUserInfoByLogin(user.getLogin());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public Object getUserInfo() {
        if(!emptyUser())
            throw new NullPointerException("User is empty");
        try {
            return usersManager.loadUserInfoByLogin(namedUser.getLogin());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void refreshStatements() throws IOException {
        if(!emptyUser())
            throw new NullPointerException("User is empty");
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

    public Class<?> getUserClass() {
        if (!emptyUser())
            throw new NullPointerException(); 
        Class<?> namedUserClass = namedUser.getClass();
        return namedUserClass;
    }

    private boolean emptyUser() {
        if (namedUser == null)
            return false;
        return true;
    }
}
