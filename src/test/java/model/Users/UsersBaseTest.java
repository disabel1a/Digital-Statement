package model.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersBaseTest {
    private static User user1;
    private static User user2;
    private static UsersBase ub;

    @BeforeAll
    public static void init() {
        user1 = new User("ivmak", "1234", false, "Ivan", "Makarenko");
        user2 = new User("admin", "4567", true, "Oleg", "Makarov");
        ub = new UsersBase();
    }

    @BeforeEach
    public void setUsersBase() {
        ub.addUser(user1);
        ub.addUser(user2);
    }

    @Test
    public void toStringTest() {
        String actual = ub.toString();

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

        String expected = sb.toString();

        assertEquals(expected, actual);
    }
}