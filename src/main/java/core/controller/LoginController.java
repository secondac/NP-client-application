package core.controller;

import core.service.LoginService;
import core.service.RoomListService;
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

    private final LoginService loginService = new LoginService();

    private String userName;

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



        // 로그인 요청
        boolean loginSuccess = loginService.login(usernameFieldText, serverFieldText);
        System.out.println("Login successful? " + loginSuccess);

        // 임시 코드 - 통신 실패시 사용하는 용도
        // loginSuccess = true;

        // username을 얻어와서 내 정보에 표시해야 함
        userName = loginService.getUsername();




        if (loginSuccess) {
            System.out.println("Login successful with username: " + usernameFieldText);

            // 현재 Stage 닫기 및 RoomListView의 새 Stage 열기
            try {
                RoomListView roomListView = new RoomListView();
                Stage newStage = new Stage();
                Scene newScene = new Scene(roomListView.getLayout());
                newStage.setScene(newScene);
                newStage.setTitle("Room List");

                newScene.getStylesheets().add(getClass().getResource("/core/view/roomlist.css").toExternalForm());
                System.out.println("roomlist.css applied.");

                Stage currentStage = (Stage) serverField.getScene().getWindow();
                currentStage.close();
                newStage.show();


                // 테스트 코드
                System.out.println("roomlistService test");
                RoomListService roomListService = new RoomListService();
                boolean r = roomListService.request("127.0.0.1");
                System.out.println("roomlistService.request: " + r);

            } catch (Exception e) {
                e.printStackTrace();
                errorLabel.setText("Failed to load the room list.");
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("Login failed. Please try again.");
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