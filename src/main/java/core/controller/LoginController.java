package core.controller;

import core.view.RoomListView;
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

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String usernameFieldText = idField.getText();
        String serverFieldText = usernameField.getText();

        // 폰트 로드
        try (InputStream fontStream = getClass().getResourceAsStream("/core/font/gangwon_font_bold.ttf")) {
            if (fontStream != null) {
                Font gangwonFont = Font.loadFont(fontStream, 18);
                if (gangwonFont != null) {
                    welcomeLabel.setFont(gangwonFont);
                    welcomeLabel.setFont(new Font("GangwonBold", 16));
                    System.out.println("Font loaded successfully: " + gangwonFont.getName());
                } else {
                    System.out.println("Failed to load font.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 입력 필드 확인
        if (usernameFieldText.isEmpty() || serverFieldText.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            errorLabel.setVisible(true);
            return;
        }

        System.out.println("Login successful with username: " + usernameFieldText);
        errorLabel.setVisible(false);

        // 현재 Stage 닫기 및 RoomListView의 새 Stage 열기
        try {
            RoomListView roomListView = new RoomListView();
            Stage newStage = new Stage();
            Scene newScene = new Scene(roomListView.getLayout());
            newStage.setScene(newScene);
            newStage.setTitle("Room List");


            newScene.getStylesheets().add(getClass().getResource("/core/view/roomlist.css").toExternalForm());
            System.out.println("roomlist.css applied.");

            // 현재 Stage 닫기
            Stage currentStage = (Stage) idField.getScene().getWindow();
            currentStage.close();

            // 새 Stage 열기
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load the room list.");
            errorLabel.setVisible(true);
        }
    }
}