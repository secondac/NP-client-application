package core.controller;

import core.view.RoomListView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField serverField;

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
        String usernameFieldText = usernameField.getText();
        String serverFieldText = serverField.getText();

        // username이 비어있으면 error msg 출력
        if (usernameFieldText.isEmpty()) {
            errorLabel.setText("Please enter username");
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
            Stage currentStage = (Stage) serverField.getScene().getWindow();
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
























/*
        // 폰트 로드
        try (InputStream fontStream = getClass().getResourceAsStream("/core/font/LeferiBaseBold.ttf")) {
            if (fontStream != null) {
                Font font = Font.loadFont(fontStream, 18);
                if (font != null) {
                    welcomeLabel.setFont(font);

                    System.out.println("Font loaded successfully: " + font.getName());
                } else {
                    System.out.println("Failed to load font.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         */