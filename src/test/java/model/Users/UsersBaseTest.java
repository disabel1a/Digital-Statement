package model.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersBaseTest {
    private static UsersBase ub;
    private static User user1;
    private static User user2;
    private static User user3;

    @BeforeAll
    public static void init() {
        ub = new UsersBase();
        user1 = new User("ivmak", "3505");
        user2 = new User("disabel1a", "1234");
        user3 = new User("dimaholl", "3505");
    }

    @BeforeEach
    public void startPoint() {
        if (!ub.isEmpty())
            ub.clear();
        ub.addUser(user1);
        ub.addUser(user2);
        ub.addUser(user3);
    }

    @Test
    public void equalsTest() {
        UsersBase expected = ub;
        UsersBase actual = new UsersBase();
        actual.addUser(user1);
        actual.addUser(user2);
        actual.addUser(user3);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserTest() {
        User expected = user2;
        User actual = ub.getUser("disabel1a");
        assertEquals(expected, actual);
    }

    @Test
    public void getUnknownUserTest() {
        User expected = null;
        User actual = ub.getUser("aaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertEquals(expected, actual);
    }
}
