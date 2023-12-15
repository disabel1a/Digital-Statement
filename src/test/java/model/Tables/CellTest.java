package model.tables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CellTest {
    private static Cell cell;
    @BeforeAll
    public static void init() {
        cell = new Cell("Some content", "Some note");
    }

    @Test
    public void toStringTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"content\" : \"").append(cell.getContent())
        .append("\",\n  \"note\" : \"").append(cell.getNote())
        .append("\"\n}");

        String actual = cell.toString();
        String expected = sb.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void equalsTest() {
        Cell expected = cell;
        Cell actual = new Cell("Some content", "Some note");

        assertEquals(expected, actual);
    }
}
