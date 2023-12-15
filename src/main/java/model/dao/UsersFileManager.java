package model.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.users.NamedUser;
import model.users.NamedUserInterface;
import model.users.Student;
import model.users.Teacher;
import model.users.User;
import model.users.UsersBase;

public class UsersFileManager {
    private final static Pattern pattern = Pattern.compile("\"([^\"]*)\"\\s*:\\s*\"([^\"]*)\"");
    private String mainFolder;

    public UsersFileManager(String mainFolder) {
        this.mainFolder = mainFolder;
    }

    public void saveUsersBase(UsersBase ub) throws IOException {
        String filePath = mainFolder + "\\users\\users-base.txt";
        FileWriter fw = new FileWriter(filePath);
        fw.write(ub.toString());
        fw.close();
    }

    public UsersBase loadUsersBase() throws IOException {
        String filePath = mainFolder + "\\users\\users-base.txt";
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        UsersBase ub = new UsersBase();
        User user = new User();

        while (br.ready()) {
            String line = br.readLine();
            if (line.contains(":")) {
                String key = new String();
                String value = new String();

                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    key = matcher.group(1);
                    value = matcher.group(2);
                }

                if (key.equals("login"))
                    user.setLogin(value);
                else
                    user.setPassword(value);
            }

            if (line.contains("}, {")) {
                ub.addUser(user);
                user = new User();
            }
        }

        ub.addUser(user);
        br.close();
        return ub;
    }

    public void saveUserInfo(NamedUserInterface userWithRole) throws IOException {
        String filePath = mainFolder + "\\info\\" + userWithRole.getLogin() + "-info.txt";

        FileWriter fw = new FileWriter(new File(filePath));
        fw.write(userWithRole.toString());
        fw.close();
    }

    public Object loadUserInfo(File file) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        NamedUser namedUser = new NamedUser();

        Teacher teacher = new Teacher();
        Student student = new Student();

        boolean isTeacher = false;

        while (br.ready()) {
            String line = br.readLine();
            if (line.contains(":")) {
                String key = new String();
                String value = new String();
                ArrayList<String> arrayValues = new ArrayList<>();
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    key = matcher.group(1);
                    if(!key.equals("statements") && !key.equals("subjects"))
                        value = matcher.group(2);
                    else {
                        String values = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                        for (String v : values.split(",")) {
                            arrayValues.add(v.trim());
                        }
                    }
                }

                if (!key.isEmpty()) {
                    String setterName = "set" + capitalizeFirstLetter(key);
                
                    try {
                        Method setter;
                        if (!value.isEmpty()) {
                            setter = NamedUser.class.getMethod(setterName, String.class);
                            setter.invoke(namedUser, value);
                        } else {
                            setter = NamedUser.class.getMethod(setterName, ArrayList.class);
                            setter.invoke(namedUser, arrayValues);
                        }
                    } catch (NoSuchMethodException namedUserException) {
                        if (key.equals("subjects")) {
                            isTeacher = true;
                        }
                
                        try {
                            if (isTeacher) {
                                Method setter;
                                Class<Teacher> teacherClass = Teacher.class;
                                if (!value.isEmpty()) {
                                    setter = teacherClass.getMethod(setterName, String.class);
                                    setter.invoke(teacher, value);
                                } else {
                                    setter = teacherClass.getMethod(setterName, ArrayList.class);
                                    setter.invoke(teacher, arrayValues);
                                }
                            } else {
                                Method setter;
                                Class<Student> studentClass = Student.class;
                                if (!value.isEmpty()) {
                                    setter = studentClass.getMethod(setterName, String.class);
                                    setter.invoke(student, value);
                                } else {
                                    setter = studentClass.getMethod(setterName, ArrayList.class);
                                    setter.invoke(student, arrayValues);
                                }
                            }
                        } catch (NoSuchMethodException teacherStudentException) {
                            teacherStudentException.printStackTrace();
                        }
                    }
                }
            }
        }

        br.close();

        if (student.getGroup().isEmpty()) {
            copyInfo(namedUser, teacher);
            return teacher;
        } else {
            copyInfo(namedUser, student);
            return student;
        }
    }

    public Object loadUserInfoByLogin(String login) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        String filePath = mainFolder + "//info//" + login + "-info.txt";
        return loadUserInfo(new File(filePath));
    }

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void copyInfo(NamedUser namedUser, NamedUserInterface userWithRole) {
        userWithRole.setLogin(namedUser.getLogin());
        userWithRole.setPassword(namedUser.getPassword());
        userWithRole.setName(namedUser.getName());
        userWithRole.setSurname(namedUser.getSurname());
        userWithRole.setPatronymic(namedUser.getPatronymic());
        userWithRole.setStatements(namedUser.getStatements());
    }
}
