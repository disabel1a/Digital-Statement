package model.controllers;

import model.dao.TableFileManager;
import model.tables.Table;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import org.eclipse.tags.shaded.org.apache.bcel.classfile.Field;

public class TableController {
    private Table table;
    private TableFileManager tableManager;

    public TableController(String mainFolder) {
        tableManager = new TableFileManager(mainFolder);
        table = null;
    }

    public void loadTable(String subject, String group) {
        try {
            table = tableManager.loadFromTextFile(subject, group);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    // methods (void):
    // addRowBack()                                                         addRowBack()
    // addColumnBack()                                                      addColumnBack()
    // setCell(Integer row, Integer column, String content, String note)    setCell(row, column, content, note)
    // setCellContent(Integer row, Integer column, String content)          setCellContent(row, column, content)
    // setCellNote(Integer row, Integer column, String note)                setCellNote(row, column, note)
    // clearCell(Integer row, Integer column)                               clearCell()
    // clearCellNote(Integer row, Integer column)                           clearCellNote()
    // removeRow(Integer row)                                               removeRow()
    // removeColumn(Integer column)                                         removeColumn()

    public void run(ArrayList<String> actions) {
        for (String action : actions) {

        }
    }
    
}
