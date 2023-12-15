package model.users;

import java.util.ArrayList;

public interface NamedUserInterface {
    public String getLogin();

    public String getPassword();

    public String getName();

    public String getSurname();

    public String getPatronymic();

    public ArrayList<String> getStatements();

    public void setLogin(String login);

    public void setPassword(String password);

    public void setName(String name);

    public void setSurname(String surname);

    public void setPatronymic(String patonymic);

    public void setStatements(ArrayList<String> statements);

    public String toString();

    public boolean equals(Object o);
}
