package sample.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeView {
    private Stage stage;
    private Scene scene;

    public void gotoLogin(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

}
