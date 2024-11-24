package core.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GameRoomController {

    @FXML
    private VBox chatMessagesContainer;

    @FXML
    private ScrollPane gameContentScrollPane;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatBox;

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
        // gameContentArea.setText("게임에 입장했습니다.");
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

    // 채팅 메시지를 추가하는 메소드
    private void addChatMessage(String sender, String message, boolean isUser) {
        // 메시지 HBox 생성
        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(5, 10, 5, 10));

        // 메시지 정렬 설정
        if (isUser) {
            messageBox.setAlignment(Pos.TOP_RIGHT);
        } else {
            messageBox.setAlignment(Pos.TOP_LEFT);
        }

        // 메시지 내용 VBox 생성 (보낸 사람과 메시지)
        VBox messageContent = new VBox();
        messageContent.setSpacing(2);

        // 보낸 사람 라벨
        Label senderLabel = new Label(sender);
        senderLabel.setStyle("-fx-font-weight: bold;");

        // 메시지 라벨 (말풍선)
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.getStyleClass().add("chat-bubble");
        if (isUser) {
            messageLabel.getStyleClass().add("user");
        } else {
            messageLabel.getStyleClass().add("other");
        }

        // 보낸 사람과 메시지를 VBox에 추가
        messageContent.getChildren().addAll(senderLabel, messageLabel);

        // 메시지 내용을 HBox에 추가
        messageBox.getChildren().add(messageContent);

        // 메시지를 채팅 컨테이너에 추가
        chatMessagesContainer.getChildren().add(messageBox);

        // ScrollPane을 맨 아래로 스크롤
        gameContentScrollPane.layout();
        gameContentScrollPane.setVvalue(1.0);
    }

    // 다른 사용자로부터 메시지를 받을 때 호출되는 메소드
    public void receiveMessage(String sender, String message) {
        addChatMessage(sender, message, false);
    }




}