package model.users;

public class User {
    private String login;
    private String password;

    public User() {
        this.login = new String();
        this.password = new String();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
        .append("  \"login\" : \"")
        .append(login)
        .append("\",\n")
        .append("  \"password\" : \"")
        .append(password)
        .append("\"\n")
        .append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;

        User comp = (User) o;
        if (!this.login.equals(comp.getLogin()))
            return false;
        if (!this.password.equals(comp.getPassword()))
            return false;

        return true;
    }
}
