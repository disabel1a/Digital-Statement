package model.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.tables.Table;

public class TableControllerTest {
    private static TableController tableController;

    @BeforeAll
    public static void init() {
        tableController = new TableController("src\\test\\java\\model\\dao\\data");
    }

    // @Test
    // public void runActionsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    //     Table table = tableController.loadTable("Физика-1411.txt");
    //     String addRow = "addRowBack()";
    //     String setCell = "setCellContent(1, 1, mama mia)";
    //     String addColumn = "addColumnBack()";
    //     String clearCell = "clearCell(0, 0)";

    //     ArrayList<String> actions = new ArrayList<>();
    //     actions.add(addRow);
    //     actions.add(addColumn);
    //     actions.add(setCell);
    //     actions.add(clearCell);

    //     tableController.run("Физика-1411.txt", actions);

    //     Table actual = tableController.loadTable("Физика-1411.txt");
    //     Table expected = new Table(1, 1);

    //     assertEquals(expected, actual);
    // }
}
