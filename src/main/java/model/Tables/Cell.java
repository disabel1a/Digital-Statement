package model.Tables;

import java.lang.reflect.Field;

public class Cell {
    private Integer xPos;
    private Integer yPos;
    private String content;

    public Cell(Integer xPos, Integer yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.content = new String();
    }

    public Cell(Integer xPos, Integer yPos, String content) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.content = content;
    }

    public Integer getXPos() {
        return this.xPos;
    }

    public void setXPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getYPos() {
        return this.yPos;
    }

    public void setYPos(Integer yPos) {
        this.yPos = yPos;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
