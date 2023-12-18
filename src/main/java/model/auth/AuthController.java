package model.auth;

import java.io.IOException;

import model.dao.UsersFileManager;
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
        User findedUser = usersBase.getUser(user.getLogin());
        if (findedUser == null)
            return false;
        else if (findedUser.equals(user))
            return true;
        return false;
    }
}
