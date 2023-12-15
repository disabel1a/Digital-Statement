package model.users;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentTest {
    private static Student student;
    private static ArrayList<String> stat;

    @BeforeAll
    public static void init() {
        student = new Student("ivmak", "3505", "Иван", "Макаренко", "Андреевич", "5123");
        stat = new ArrayList<>();
        stat.add("abba");
        stat.add("baba");
        student.setStatements(stat);
    }

    @Test
    public void equalsTest() {
        Student expected = student;
        Student actual = new Student("ivmak", "3505", "Иван", "Макаренко", "Андреевич", "5123");

        actual.setStatements(stat);

        assertEquals(expected, actual);
    }
}
