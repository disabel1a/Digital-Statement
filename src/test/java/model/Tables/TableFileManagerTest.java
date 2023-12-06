package model.Tables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.tables.Cell;
import model.tables.Table;
import model.tables.TableFileManager;

public class TableFileManagerTest {
    private static Table table;
    private static File saveFile;
    private static File loadFile;

    @BeforeAll
    public static void init() {
        saveFile = new File("src\\test\\java\\model\\Tables\\TextFiles\\Save.text");
        loadFile = new File("src\\test\\java\\model\\Tables\\TextFiles\\Load.text");
        table = new Table(5, 5);
    }

    @BeforeEach
    public void clearSaveFile() {
        try {
            FileWriter fw = new FileWriter(saveFile);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        TableFileManager tm = new TableFileManager(saveFile);
        tm.saveToTextFile(table);
        
        BufferedReader br = new BufferedReader(new FileReader(saveFile));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append(br.readLine()).append("\n");
        }
        br.close();

        String actual = sb.toString();
        String expexted = table.toString() + "\n";

        assertEquals(expexted, actual);
    }

    @Test
    public void loadFromTextFile() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
        TableFileManager tm = new TableFileManager(loadFile);
        Table actual = tm.loadFromTextFile();

        Table expected = table;

        assertEquals(expected, actual);
    }
}
