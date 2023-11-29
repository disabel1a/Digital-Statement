package model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UsersBase {
    private HashMap<String, User> users;

    public UsersBase() {
        this.users = new HashMap<>();
    }

    public UsersBase(HashMap<String,User> users) {
        this.users = users;
    }

    public HashMap<String,User> getUsers() {
        return this.users;
    }

    public void setUsers(HashMap<String,User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.put(user.getLogin(), user);
    }

    public User getUser(String name) {
        return users.get(name);
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public boolean containsKey(String key) {
        return users.containsKey(key);
    }

    public void clear() {
        users.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, User>> iterator = users.entrySet().iterator();
        sb.append("[");
        while (iterator.hasNext()) {
            sb.append(iterator.next().getValue().toString());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
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

        ArrayList<String> keys = new ArrayList<>();
        UsersBase currUsersBase = (UsersBase) o;
        for (Map.Entry<String,User> entry : users.entrySet()) {
            if (!currUsersBase.containsKey(entry.getKey()))
                return false;
            if (!currUsersBase.getUser(entry.getKey()).equals(entry.getValue()))
                return false;
        }
        return true;
    }

}
