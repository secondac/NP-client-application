package core.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import core.model.dto.LoginRequestDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    private Button loginButton;

    @FXML
    private void handleLogin() {
        String usernameFieldText = idField.getText();
        String serverFieldText = usernameField.getText();


        try (InputStream fontStream = getClass().getResourceAsStream("/core/font/gangwon_font_bold.ttf")) {
            if (fontStream != null) {
                Font gangwonFont = Font.loadFont(fontStream, 18);
                if (gangwonFont != null) {
                    welcomeLabel.setFont(gangwonFont);
                    System.out.println("Font loaded successfully: " + gangwonFont.getName());
                } else {
                    System.out.println("Failed to load font.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (usernameFieldText.isEmpty() || serverFieldText.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            errorLabel.setVisible(true);
        } else {
            System.out.println("Login successful with username: " + usernameFieldText);
            errorLabel.setVisible(false);
        }


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/roomlist.fxml"));
            Parent roomListLayout = loader.load();

            Stage currentStage = (Stage) idField.getScene().getWindow();
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