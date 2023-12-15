package model.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.tables.Cell;
import model.tables.Table;

public class TableFileManager {
    private String mainFolder;

    public TableFileManager(String mainFolder) {
        this.mainFolder = mainFolder;
    }

    public void saveToTextFile(Table table, String subject, String group) throws IOException {
        String filePath = mainFolder + "\\tables\\" + subject + "-" + group + ".txt";

        FileWriter fw = new FileWriter(filePath);
        fw.write(table.toString());
        fw.close();
    }

    public Table loadFromTextFile(String subject, String group) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String filePath = mainFolder + "\\tables\\" + subject + "-" + group + ".txt";

        BufferedReader br = new BufferedReader(new FileReader(filePath));
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

    public List<String> getStatementsByPointer(String pointer) {
        Path folderPath = Paths.get(mainFolder, "tables");
        List<String> statements;

        try (Stream<Path> files = Files.list(folderPath)) {
            statements = files
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(name -> name.contains(pointer))
                .collect(Collectors.toList());
        } catch (IOException e) {
            statements = new ArrayList<>();
        }

        return statements;
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
