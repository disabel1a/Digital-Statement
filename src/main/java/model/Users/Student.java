package model.users;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Student extends NamedUser implements NamedUserInterface{
    private String group;

    public Student() {
        group = new String();
    }

    public Student(String login, String password, String name, String surname, String patronymic, String group) {
        super(login, password, name, surname, patronymic);
        this.group = group;
    }

    public Student(String login, String password, String name, String surname, String patronymic, ArrayList<String> statements,  String group) {
        super(login, password, name, surname, patronymic, statements);
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        
        sb.append(fieldsToString(this.getClass().getSuperclass().getSuperclass().getDeclaredFields(), false));
        sb.append(fieldsToString(this.getClass().getSuperclass().getDeclaredFields(), false));
        sb.append(fieldsToString(this.getClass().getDeclaredFields(), true));

        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;
        
        if(!super.equals(o))
            return false;

        Field[] fields = this.getClass().getSuperclass().getSuperclass().getDeclaredFields();

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

    private String fieldsToString(Field[] fields, boolean isLast) {
        StringBuilder sb = new StringBuilder();
        int regCouner = fields.length;
        if(!isLast)
            regCouner++;
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
        return sb.toString();
    }
}
