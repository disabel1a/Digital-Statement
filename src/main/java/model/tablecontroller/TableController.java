package model.tablecontroller;

import model.tables.Table;
import model.tables.TableFileManager;

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
    private File file;

    public TableController(File file) {
        this.file = file;
        TableFileManager fileManager = new TableFileManager(file);
        try {
            this.table = fileManager.loadFromTextFile();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public TableController(String filePath) {
        this.file = new File(filePath);
        TableFileManager fileManager = new TableFileManager(file);
        try {
            this.table = fileManager.loadFromTextFile();
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
