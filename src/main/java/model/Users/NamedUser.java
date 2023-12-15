package model.users;

import java.util.ArrayList;

public class NamedUser extends User {
    private String name;
    private String surname;
    private String patronymic;
    private ArrayList<String> statements;

    public NamedUser() {
        super();
        name = new String();
        surname = new String();
        patronymic = new String();
        statements = new ArrayList<>();
    }

    public NamedUser(String login, String password, String name, String surname, String patronymic) {
        super(login, password);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.statements = new ArrayList<>();
    }

    public NamedUser(String login, String password, String name, String surname, String patronymic, ArrayList<String> statements) {
        super(login, password);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.statements = statements;
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

    public String getPatronymic() {
        return this.patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public ArrayList<String> getStatements() {
        return this.statements;
    }

    public void setStatements(ArrayList<String> statements) {
        this.statements = statements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;

        if(!super.equals(o))
            return false;

        NamedUser comp = (NamedUser) o;
        if (!this.name.equals(comp.getName()))
            return false;
        if (!this.surname.equals(comp.getSurname()))
            return false;
        if (!this.patronymic.equals(comp.getPatronymic()))
            return false;
        if (!this.statements.equals(comp.getStatements()))
            return false; 

        return true;
    }
}
