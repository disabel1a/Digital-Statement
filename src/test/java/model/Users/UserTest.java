package model.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTest {
    private static User user;

    @BeforeAll
    public static void init() {
        user = new User("ivmak", "3505");
    }

    @Test
    public void toStringTest() {
        String actiual = user.toString();

        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"login\" : \"").append(user.getLogin())
        .append("\",\n  \"password\" : \"").append(user.getPassword())
        .append("\"\n}");

        String expected = sb.toString();

        assertEquals(expected, actiual);
    }

    @Test
    public void equalsTest() {
        User expected = user;
        User actual = new User("ivmak", "3505");

        assertEquals(expected, actual);
    }
}
