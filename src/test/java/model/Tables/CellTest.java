package model.Tables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CellTest {
    private static Cell cell;
    @BeforeAll
    public static void init() {
        cell = new Cell(14, 25, "Work");
    }

    @Test
    public void toStringTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"xPos\" : \"").append(cell.getXPos())
        .append("\",\n  \"yPos\" : \"").append(cell.getYPos())
        .append("\",\n  \"content\" : \"").append(cell.getContent())
        .append("\"\n}");

        String actual = cell.toString();
        String expected = sb.toString();

        assertEquals(expected, actual);
    }
}
