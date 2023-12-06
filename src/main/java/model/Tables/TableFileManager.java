package model.tables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

public class TableFileManager {
    private File file;

    public TableFileManager(File file) {
        this.file = file;
    }
    
    public TableFileManager(String filePath) {
        this.file = new File(filePath);
    }

    public void saveToTextFile(Table table) throws IOException {
        FileWriter fw = new FileWriter(file);
        fw.write(table.toString());
        fw.close();
    }

    public Table loadFromTextFile() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        Table table = null;
        while (br.ready()) {
            String line = br.readLine();
            if(line.contains("table")) {
                table = new Table(readTable(br, line));
                break;
            }
        }
        br.close();
        return table;
    }

    private LinkedList<LinkedList<Cell>> readTable(BufferedReader br, String line) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LinkedList<LinkedList<Cell>> table = new LinkedList<>();
        table.add(new LinkedList<Cell>());
        br.readLine();
        Integer row = 0;
        Field[] fields = Cell.class.getDeclaredFields();
        Cell cell = new Cell();
        while (!line.trim().equals("}]")) {
            line = br.readLine();
            if(line.contains(":")) {
                for (Field f : fields) {
                    String setterName = "set" + capitalizeFirstLetter(f.getName());
                    Method setter = Cell.class.getMethod(setterName, f.getType());

                    String[] content = line.split(":", 2);

                    //System.out.println(content[1].split("\"", 3)[1]);

                    setter.invoke(cell, convertToFieldType(content[1].split("\"", 3)[1], f.getType()));

                    line = br.readLine();
                    if (!line.contains(":"))
                        break;
                }
            }

            if (line.contains("}, {")) {
                table.get(row).add(cell);
                cell = new Cell();
                continue;
            }

            if (line.contains("[{")) {
                table.get(row).add(cell);
                cell = new Cell();
                table.add(new LinkedList<Cell>());
                row++;
                continue;
            }
        }
        table.get(row).add(cell);
        return table;
    }

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Object convertToFieldType(String value, Class<?> type) {
        if (type == String.class) 
            return value;
        else if (type == Boolean.class)
            return Boolean.parseBoolean(value);
        else if (type == Integer.class)
            return Integer.parseInt(value);
        return null; 
    }
}
