package model.users;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UsersBase {
    private HashMap<String, String> users;

    public UsersBase() {
        users = new HashMap<>();
    }

    public UsersBase(HashMap<String,String> users) {
        this.users = users;
    }

    public HashMap<String,String> getUsers() {
        return this.users;
    }

    public void setUsers(HashMap<String,String> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.put(user.getLogin(), user.getPassword());
    }

    public User getUser(String login) {
        if(!users.containsKey(login))
            return null;
        return new User(login, users.get(login));
    }

    public void removeUser(User user) {
        users.remove(user.getLogin());
    }

    public void removeUser(String login) {
        users.remove(login);
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public boolean containsLogin(String login) {
        return users.containsKey(login);
    }

    public boolean containsLogin(User user) {
        return users.containsKey(user.getLogin());
    }

    public void clear() {
        users.clear();
    }

    public int getSize() {
        return users.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = users.entrySet().iterator();
        sb.append("[");
        while (iterator.hasNext()) {
            User user = this.getUser(iterator.next().getKey());
            sb.append(user.toString());
            if(iterator.hasNext())
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        
        if (o == null || this.getClass() != o.getClass())
            return false;

        UsersBase comp = (UsersBase) o;
        if (comp.getSize() != users.size()) {
            return false;
        }
    
        for (Map.Entry<String, String> entry : users.entrySet()) {
            if (!comp.containsLogin(entry.getKey()))
                return false;
            if (!comp.getUser(entry.getKey()).getPassword().equals(entry.getValue()))
                return false;
        }
    
        return true;
    }
}
