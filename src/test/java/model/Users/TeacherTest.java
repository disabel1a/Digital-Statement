package model.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TeacherTest {
    private static Teacher teacher;
    private static ArrayList<String> stat;
    private static ArrayList<String> subjects;

    @BeforeAll
    public static void init() {
        stat = new ArrayList<>();
        stat.add("abba");
        stat.add("baba");

        subjects = new ArrayList<>();
        subjects.add("Атомная энергетика");

        teacher = new Teacher("ivmak", "3505", "Иван", "Макаренко", "Андреевич", subjects);
        teacher.setStatements(stat);
    }

    @Test
    public void equalsTest() {
        Teacher expected = teacher;
        Teacher actual = new Teacher("ivmak", "3505", "Иван", "Макаренко", "Андреевич", subjects);

        actual.setStatements(stat);

        assertEquals(expected, actual);
    }
}
