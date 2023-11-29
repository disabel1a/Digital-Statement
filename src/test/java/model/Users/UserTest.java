package model.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static User user1;

    @BeforeAll
    public static void init() {
        user1 = new User("ivmak", "1234", false, "Ivan", "Makarenko");
    }

    @Test
    public void toStringTest() {
        String actual = user1.toString();

        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"login\" : \"").append(user1.getLogin())
        .append("\",\n  \"password\" : \"").append(user1.getPassword())
        .append("\",\n  \"isTeacher\" : \"").append(user1.getIsTeacher())
        .append("\",\n  \"name\" : \"").append(user1.getName())
        .append("\",\n  \"surname\" : \"").append(user1.getSurname())
        .append("\"\n}");

        String expected = sb.toString();

        assertEquals(expected, actual);
    }
}
