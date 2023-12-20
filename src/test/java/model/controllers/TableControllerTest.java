package model.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.dao.TableFileManager;
import model.tables.Table;

public class TableControllerTest {
    private static TableController tableController;
    private static TableFileManager fileManager;

    @BeforeAll
    public static void init() {
        tableController = new TableController("src\\test\\java\\model\\dao\\data");
        fileManager = new TableFileManager("src\\test\\java\\model\\dao\\data");
    }

    @BeforeEach
    public void setTable() throws IOException {
        Table table = tableController.loadTable("Физика-2155.txt");
        fileManager.saveToTextFile(table, "Физика-1411.txt");
    }

    @Test
    public void runActionsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Table actual = tableController.loadTable("Физика-1411.txt");
        String addRow = "addRowBack()";
        String setCell = "setCellContent(1, 1, mama mia)";
        String addColumn = "addColumnBack()";
        String clearCell = "clearCell(0, 0)";

        ArrayList<String> actions = new ArrayList<>();
        actions.add(addRow);
        actions.add(addColumn);
        actions.add(setCell);
        actions.add(clearCell);

        tableController.run("Физика-1411.txt", actions);
        
        actual.addRowBack();
        actual.addColumnBack();
        actual.setCellContent(1, 1, "mama mia");
        actual.clearCell(0, 0);

        Table expected = tableController.loadTable("Физика-1411.txt");

        assertEquals(expected, actual);
    }
}
