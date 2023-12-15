package model.auth;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import model.dao.UsersFileManager;
import model.users.NamedUser;
import model.users.User;
import model.users.UsersBase;

public class AuthController {
    private static UsersBase usersBase;
    private UsersFileManager fileManager;

    public AuthController(String mainFolder) throws IOException {
        fileManager = new UsersFileManager(mainFolder);
        usersBase = fileManager.loadUsersBase();
    }

    public boolean verification(User user) {
        if (usersBase.getUser(user.getLogin()).equals(user))
            return true;
        return false;
    }
}
