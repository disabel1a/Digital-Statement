package model.users;

import java.lang.reflect.Field;

public class User {
    private String login;
    private String password;
    private Boolean isTeacher;

    private String name;
    private String surname;

    public User() {
        this.login = new String();
        this.password = new String();
        this.isTeacher = false;
        this.name = new String();
        this.surname = new String();
    }
    

    public User(String login, String password, Boolean isTeacher, String name, String surname) {
        this.login = login;
        this.password = password;
        this.isTeacher = isTeacher;
        this.name = name;
        this.surname = surname;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isIsTeacher() {
        return this.isTeacher;
    }

    public Boolean getIsTeacher() {
        return this.isTeacher;
    }

    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
