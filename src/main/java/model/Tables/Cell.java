package model.tables;

import java.lang.reflect.Field;

public class Cell {
    private String content;
    private String note;

    public Cell() {
        this.content = new String();
        this.note = new String();
    }

    public Cell(String content) {
        this.content = content;
        this.note = new String();
    }

    public Cell(String content, String note) {
        this.content = content;
        this.note = note;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void clear() {
        this.content = new String();
        this.note = new String();
    }

    public void clearNote() {
        this.note = new String();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        Field[] fields = this.getClass().getDeclaredFields();
        int regCouner = fields.length;
        for (Field f : fields) {
            try {
                f.setAccessible(true);
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
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if(!f.get(this).equals(f.get(o)))
                    return false;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
