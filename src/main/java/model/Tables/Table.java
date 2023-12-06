package model.tables;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class Table {
    private Integer rows;
    private Integer columns;
    private LinkedList<LinkedList<Cell>> table;

    public Table(Integer rows, Integer columns) {
        table = new LinkedList<LinkedList<Cell>>();
        this.rows = rows;
        this.columns = columns;
        for (Integer i = 0;  i < rows; i++) {
            table.add(new LinkedList<Cell>());
            for (Integer j = 0; j < columns; j++) {
                table.get(i).add(new Cell());
            }
        }
    }

    public Table(LinkedList<LinkedList<Cell>> table) {
        this.table = table;
        this.rows = table.size();
        this.columns = table.getFirst().size();
    }

    public LinkedList<LinkedList<Cell>> getTable() {
        return this.table;
    }

    public void setTable(LinkedList<LinkedList<Cell>> table) {
        this.table = table;
    }

    public Integer getRows() {
        return this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getColumns() {
        return this.columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public void addRowBack() {
        table.add(new LinkedList<Cell>());
        for(Integer i = 0; i < table.getFirst().size(); i++) {
            table.getLast().add(new Cell());
        }
        rows++;
    }

    public void addColumnBack() {
        for (LinkedList<Cell> row : table) {
            row.add(new Cell());
        }
        columns++;
    }

    public void setCell(Integer row, Integer column, Cell cell) {
        table.get(row).set(column, cell);
    }

    public void setCell(Integer row, Integer column, String content, String note) {
        Cell cell = table.get(row).get(column);
        cell.setContent(content);
        cell.setNote(note);
    }

    public void setCellContent(Integer row, Integer column, String content) {
        Cell cell = table.get(row).get(column);
        cell.setContent(content);
    }

    public void setCellNote(Integer row, Integer column, String note) {
        Cell cell = table.get(row).get(column);
        cell.setContent(note);
    }

    public Cell getCell(Integer row, Integer column) {
        return table.get(row).get(column);
    }

    public void clearCell(Integer row, Integer column) {
        Cell cell = table.get(row).get(column);
        cell.clear();
    }

    public void clearCellNote(Integer row, Integer column) {
        Cell cell = table.get(row).get(column);
        cell.clearNote();
    }

    public void removeRow(Integer row) {
        table.remove(table.get(row));
        rows--;
    }

    public void removeColumn(Integer column) {
        for(LinkedList<Cell> row : table) {
            row.remove(row.get(column));
        }
        columns--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[{\n");
        Field[] fields = this.getClass().getDeclaredFields();
        Integer regCouner = fields.length;
        for(Field f : fields) {
            try {
                f.setAccessible(true);
                if (f.getType() == LinkedList.class) {
                    sb.append("  \"")
                    .append(f.getName())
                    .append("\" : \n")
                    .append(tableElementsToString());
                    regCouner--;
                    continue;
                }
                String content = String.valueOf(f.get(this));
                sb.append("  \"")
                .append(f.getName())
                .append("\" : \"")
                .append(content)
                .append("\"");
                if (regCouner != 1) {
                    sb.append(",");
                }
                sb.append("\n");
                regCouner--;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Table compTable = (Table) o;

        if (this.rows != compTable.getRows() || this.columns != compTable.getColumns())
            return false;
        
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getName().equals("rows") || f.getName().equals("columns") || f.getName().equals("table"))
                continue;
            try {
                if(!f.get(this).equals(f.get(o)))
                    return false;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Integer i = 0; i < rows; i++) {
            for (Integer j = 0; j < columns; j++) {
                if(!this.table.get(i).get(j).equals(compTable.getCell(i, j)))
                    return false;
            }
        }

        return true;
    }

    private String tableElementsToString() {
        StringBuilder sb = new StringBuilder();

        Integer rowCounter = 1;
        for (LinkedList<Cell> row : table) {
            sb.append("[");
            Integer columnCounter = 1;
            for (Cell cell : row) {
                sb.append(cell.toString());
                if (columnCounter < columns)
                    sb.append(", ");
                columnCounter++;
            }
            sb.append("]");
            if (rowCounter < rows) {
                sb.append(", ");
            }
            sb.append("\n");
            rowCounter++;
        }

        return sb.toString();
    }
}
