package core.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private Button loginButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            errorLabel.setVisible(true);
        } else {
            System.out.println("Login successful with username: " + username);
            errorLabel.setVisible(false);
        }


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/roomlist.fxml"));
            Parent roomListLayout = loader.load();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(new Scene(roomListLayout));
            currentStage.setTitle("Room List");
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load the room list.");
            errorLabel.setVisible(true);
        }
    }


}