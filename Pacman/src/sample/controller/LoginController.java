package sample.controller;

import sample.model.User;

import java.util.ArrayList;

public class LoginController {
    private static LoginController loginController = null;
    ArrayList<User> users = new ArrayList<>();

    private LoginController() {
    }

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        return loginController;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void addUserToUsers(User user) {
        users.add(user);
    }

    public void removeUserFromUsers(User user) {
        users.remove(user);
    }

    public boolean isUserWithThisExists(String username) {
        return getUserByUsername(username) != null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        User user = getUserByUsername(username);
        if (username == null)
            return false;
        return user.getPassword().equals(password);
    }

    public void signUpProcess(String username, String password) {
        User user = new User(username, password);
        addUserToUsers(user);
    }

    public void removeAccountProcess(String username) {
        User user = getUserByUsername(username);
        if (user != null)
            removeUserFromUsers(user);
    }

    public void changePasswordProcess(String username, String newPassword) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.setPassword(newPassword);
        }
    }
}
