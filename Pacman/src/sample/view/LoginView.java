package sample.view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.controller.LoginController;

import java.io.IOException;
import java.util.Optional;

public class LoginView {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    private String username;
    private String password;
    LoginController loginController = LoginController.getInstance();

    public void initializeUserInfo() {
        username = usernameField.getText();
        password = passwordField.getText();
    }

    public void Login(ActionEvent actionEvent) {
        initializeUserInfo();
        if (PrimaryChecks()) return;

        try {
            GameView gameView = new GameView();
            gameView.getStage().show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void signUp(ActionEvent actionEvent) {
        initializeUserInfo();
        if (loginController.isUserWithThisExists(username)) {
            showErrorAlert("User with this username already exists");
            return;
        }

        loginController.signUpProcess(username, password);
        showSuccessfulAlert("You sign up successfully");
    }

    public void removeAccount(ActionEvent actionEvent) {
        initializeUserInfo();
        if (PrimaryChecks()) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure???");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loginController.removeAccountProcess(username);
            showSuccessfulAlert("User removed successfully");
        }
    }

    public void changePassword(ActionEvent actionEvent) {
        initializeUserInfo();
        if (PrimaryChecks()) return;

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Change password");
        dialog.setHeaderText("Enter your new password");
        Optional<String> result = dialog.showAndWait();
        String newPassword = dialog.getEditor().getText();

        result.ifPresent(name -> {
            loginController.changePasswordProcess(username, newPassword);
            showSuccessfulAlert("Password changed successfully");
        });
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


    private boolean PrimaryChecks() {
        if (!loginController.isUserWithThisExists(username)) {
            showErrorAlert("User with this username doesn't exits");
            return true;
        }
        if (!loginController.isPasswordCorrect(username, password)) {
            showErrorAlert("Password is wrong");
            return true;
        }
        return false;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }

    private void showSuccessfulAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful");
        alert.setHeaderText(message);
        alert.show();
    }
}