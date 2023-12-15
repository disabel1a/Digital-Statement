package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.tables.Cell;
import model.tables.Table;

public class TableFileManagerTest {
    private static Table table;
    private static TableFileManager tableManager;

    @BeforeAll
    public static void init() {
        table = new Table(5, 5);
        tableManager = new TableFileManager("src\\test\\java\\model\\dao\\data");
    }

    @BeforeEach
    public void setTable() {
        table.setCell(0, 0, new Cell("Table test row 1"));
        table.setCell(1, 0, new Cell("Table test row 2"));
        table.setCell(2, 0, new Cell("Table test row 3"));
        table.setCell(3, 0, new Cell("Table test row 4"));
        table.setCell(4, 0, new Cell("Table test row 5"));
    }

    @Test
    public void saveToTextFileTest() throws IOException {
        tableManager.saveToTextFile(table, "Физика", "2155");
    }

    @Test
    public void loadFromTextFile() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        Table actual = tableManager.loadFromTextFile("Физика", "2155");

        Table expected = table;

        assertEquals(expected, actual);
    }
}
