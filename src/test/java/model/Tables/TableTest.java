package model.tables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableTest {
    private static Table table;
    private static Table emptyTable;
    public static Table table2x2;
    private static Cell cell1;
    private static Cell cell2;

    @BeforeAll
    public static void init() {
        cell1 = new Cell("Some Content 1", "Some Note 1");
        cell2 = new Cell("Some Content 2", "Some Note 2");

        table = new Table(5, 5);
        emptyTable = new Table(5, 5);

        table2x2 = new Table(2,2);
    }

    @BeforeEach
    public void setTable() {
        table.setCell(1, 2, cell1);
        table.setCell(3, 4, cell2);    
        
        table2x2.setCell(0, 0, cell1);
        table2x2.setCell(0, 1, cell2);
        table2x2.setCell(1, 0, cell1);
        table2x2.setCell(1, 1, cell2);
    }

    @Test
    public void toStringTableTest() {
        StringBuilder sb = new StringBuilder();

        sb.append("[{\n")
        .append("  \"rows\" : \"")
        .append(2).append("\",\n")
        .append("  \"columns\" : \"")
        .append(2).append("\",\n")
        .append("  \"table\" : \n");

        for (Integer i = 0; i < 2; i++) {
            sb.append("[");
            for (Integer j = 0; j < 2; j++) {
                if (j == 0) {
                    sb.append(cell1.toString());
                    sb.append(", ");
                } else
                    sb.append(cell2.toString());
            }
            if(!(i + 1 == 2))
                sb.append("], \n");
            else
                sb.append("]\n");
        }

        sb.append("}]");

        String expected = sb.toString();
        String actual = table2x2.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void equalsTest() {
        Table actual = new Table(5, 5);
        actual.setCell(1, 2, cell1);
        actual.setCell(3, 4, cell2);

        Table expected = table;

        assertEquals(expected, actual);
    }

    @Test
    public void addRowTest() {
        Table expected = emptyTable;
        Table actual = new Table(4, 5);

        actual.addRowBack();

        assertEquals(expected, actual);
    }

    @Test
    public void addColumnTest() {
        Table expected = emptyTable;
        Table actual = new Table(5, 4);
        actual.addColumnBack();

        assertEquals(expected, actual);
    }

    @Test
    public void removeRowTest() {
        Table expected = emptyTable;
        Table actual = new Table(6, 5);

        actual.removeRow(actual.getRows() - 1);

        assertEquals(expected, actual);
    }

    @Test
    public void removeColumnTest() {
        Table expected = emptyTable;
        Table actual = new Table(5, 6);

        actual.removeColumn(actual.getColumns() - 1);

        assertEquals(expected, actual);
    }

    @Test
    public void clearCellTest() {
        Table expected = emptyTable;
        Table actual = table;
        actual.clearCell(1, 2);
        actual.clearCell(3, 4);

        assertEquals(expected, actual);
    }
}
