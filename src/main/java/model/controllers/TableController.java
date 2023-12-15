package model.controllers;

import model.dao.TableFileManager;
import model.tables.Table;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TableController {
    private Table table;
    private TableFileManager tableManager;
    private String fileName;

    public TableController(String mainFolder) {
        tableManager = new TableFileManager(mainFolder);
        table = null;
        fileName = null;
    }

    public void loadTable(String subject, String group) {
        try {
            table = tableManager.loadFromTextFile(subject, group);
            fileName = subject + "-" + group + ".txt";
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTable(String fileName) {
        try {
            table = tableManager.loadFromTextFile(fileName);
            this.fileName = fileName;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public Table getTable() {
        if (!emptyTable())
            throw new NullPointerException("Table is null");
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

    public void run(ArrayList<String> actions) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!emptyTable())
            throw new NullPointerException();
        for (String action : actions) {
            String methodName = action.substring(0, action.indexOf("(")).trim();
            Method method = Table.class.getMethod(methodName);

            String argString = action.substring(action.indexOf("(") + 1, action.indexOf(")"));
            String[] args = null;
            if (argString.contains(",")) {
                args = argString.split(",", 4);
            }

            if (argString.equals("")) {
                method.invoke(table);

            } else if (args == null) {
                method.invoke(table, Integer.parseInt(argString.trim()));

            } else if (args.length == 2) {
                method.invoke(table, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()));

            } else if (args.length == 3) {
                method.invoke(table, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), args[2].trim());

            } else if (args.length == 4){
                method.invoke(table, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), args[2].trim(), args[3].trim());
            }
        }

        try {
            tableManager.saveToTextFile(table, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean emptyTable() {
        if (table == null)
            return false;
        return true;
    }
    
}
