package core.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GameRoomController {

    @FXML
    private TextArea gameContentArea;

    @FXML
    private TextField questionTextField;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<String> participantListView;

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {
        // 초기화 작업: 게임 내용과 참여자 목록을 설정할 수 있음
        gameContentArea.setText("게임에 입장했습니다.");
        // gameContentArea.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        participantListView.getItems().addAll("Player 1", "Player 2", "Player 3");
    }

    @FXML
    private void handleSendQuestion() {
        String question = questionTextField.getText();
        if (!question.isEmpty()) {
            gameContentArea.appendText("\nYou: " + question);
            questionTextField.clear();
            // 서버로 질문 전송 로직 추가 가능
        } else {
            System.out.println("Question field is empty. Please type a question.");
        }
    }

    @FXML
    private void handleExitRoom() {
        System.out.println("Exiting the room...");

        // 현재 Stage 닫기
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
    }


}