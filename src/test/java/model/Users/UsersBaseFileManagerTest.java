package model.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersBaseFileManagerTest {
    private static UsersBase ub;
    private static User user1;
    private static User user2;
    private static User user3;

    private static File loadFile;
    private static File saveFile;

    private static String stringUsersBase;

    @BeforeAll
    public static void init() {
        user1 = new User("ivmak", "1234", false, "Ivan", "Makarenko");
        user2 = new User("admin", "4567", true, "Oleg", "Makarov");
        user3 = new User("legend17", "12324", false, "Alexey", "Igorkov");
        ub = new UsersBase();

        //loadFile = new File("src\\test\\java\\model\\Users\\TextFiles\\Load.txt");
        loadFile = new File("src\\test\\java\\model\\Users\\TextFiles\\Load.json");
        //saveFile = new File("src\\test\\java\\model\\Users\\TextFiles\\Save.txt");
        saveFile = new File("src\\test\\java\\model\\Users\\TextFiles\\Save.json");

        StringBuilder sb = new StringBuilder();

        sb.append("[");

        sb.append("{\n  \"login\" : \"").append(user1.getLogin())
        .append("\",\n  \"password\" : \"").append(user1.getPassword())
        .append("\",\n  \"isTeacher\" : \"").append(user1.getIsTeacher())
        .append("\",\n  \"name\" : \"").append(user1.getName())
        .append("\",\n  \"surname\" : \"").append(user1.getSurname())
        .append("\"\n}");

        sb.append(", ");

        sb.append("{\n  \"login\" : \"").append(user2.getLogin())
        .append("\",\n  \"password\" : \"").append(user2.getPassword())
        .append("\",\n  \"isTeacher\" : \"").append(user2.getIsTeacher())
        .append("\",\n  \"name\" : \"").append(user2.getName())
        .append("\",\n  \"surname\" : \"").append(user2.getSurname())
        .append("\"\n}");

        sb.append("]");

        sb.append("\n");

        stringUsersBase = sb.toString();
    }

    @BeforeEach
    public void setUsersBase() {
        if(!ub.isEmpty())
            ub.clear();
        ub.addUser(user1);
        ub.addUser(user2);
        //ub.addUser(user3);

        try {
            FileWriter fw = new FileWriter(saveFile);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveToTextFileTest() throws IOException {
        UsersBaseFileManager fileManager = new UsersBaseFileManager(saveFile);
        fileManager.saveToTextFile(ub);

        BufferedReader br = new BufferedReader(new FileReader(saveFile));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine()).append("\n");
        }
        br.close();

        String actual = sb.toString();
        String expexted = stringUsersBase;

        assertEquals(expexted, actual);
    }

    @Test void loadFromTextFileTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        UsersBase actual = new UsersBaseFileManager(loadFile).loadFromTextFile();
        UsersBase expected = ub;

        assertEquals(expected, actual);
    }
}
