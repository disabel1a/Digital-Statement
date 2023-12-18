package model.controllers;

import model.dao.TableFileManager;
import model.tables.Table;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TableController {
    private TableFileManager tableManager;

    public TableController(String mainFolder) {
        tableManager = new TableFileManager(mainFolder);
    }

    public Table loadTable(String subject, String group) {
        Table table = new Table(1,1);
        try {
            table = tableManager.loadFromTextFile(subject, group);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    public Table loadTable(String fileName) {
        Table table = new Table(1,1);
        try {
            table = tableManager.loadFromTextFile(fileName);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    // methods:
    // addRowBack()                                                         addRowBack()
    // addColumnBack()                                                      addColumnBack()
    // setCell(Integer row, Integer column, String content, String note)    setCell(row, column, content, note)
    // setCellContent(Integer row, Integer column, String content)          setCellContent(row, column, content)
    // setCellNote(Integer row, Integer column, String note)                setCellNote(row, column, note)
    // clearCell(Integer row, Integer column)                               clearCell()
    // clearCellNote(Integer row, Integer column)                           clearCellNote()
    // removeRow(Integer row)                                               removeRow()
    // removeColumn(Integer column)                                         removeColumn()

    public void run(String fileName, ArrayList<String> actions) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Table table = new Table(1, 1);
        try {
            table = tableManager.loadFromTextFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String action : actions) {
            String methodName = action.substring(0, action.indexOf("(")).trim();

            System.out.println(methodName);

            String argString = action.substring(action.indexOf("(") + 1, action.indexOf(")"));
            String[] args = null;

            if (argString.contains(",")) {
                args = argString.split(",", 4);
            }

            if (argString.equals("")) {
                Method method = Table.class.getMethod(methodName);
                method.invoke(table);

            } else if (args == null) {
                Method method = Table.class.getMethod(methodName, Integer.class);
                method.invoke(table, Integer.parseInt(argString.trim()));

            } else if (args.length == 2) {
                Method method = Table.class.getMethod(methodName, Integer.class, Integer.class);
                method.invoke(table, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()));

            } else if (args.length == 3) {
                Method method = Table.class.getMethod(methodName, Integer.class, Integer.class, String.class);
                method.invoke(table, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), args[2].trim());

            } else if (args.length == 4){
                Method method = Table.class.getMethod(methodName, Integer.class, Integer.class, String.class, String.class);
                method.invoke(table, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), args[2].trim(), args[3].trim());
            }
        }

        try {
            tableManager.saveToTextFile(table, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     TableController tableController = new TableController("src\\test\\java\\model\\dao\\data");
    //     Table table = tableController.loadTable("Физика-1411.txt");
    //     String addRow = "addRowBack()";
    //     String setCell = "setCellContent(1, 1, mamamia)";
    //     String addColumn = "addColumnBack()";
    //     String clearCell = "clearCell(0, 0)";

    //     ArrayList<String> actions = new ArrayList<>();
    //     actions.add(addRow);
    //     actions.add(addColumn);
    //     actions.add(setCell);
    //     actions.add(clearCell);

    //     try {
    //         tableController.run("Физика-1411.txt", actions);
    //     } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
    //             | InvocationTargetException e) {
    //         e.printStackTrace();
    //     }
    // }
}
