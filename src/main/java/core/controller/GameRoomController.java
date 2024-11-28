package core.controller;

import core.service.GameService;
import core.util.UIUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameRoomController {

    @FXML
    private VBox leftVBox;

    @FXML
    private Label gameRoomLabel;

    @FXML
    private TextArea gameContentArea;

    @FXML
    private TextField questionTextField;

    @FXML
    private Button sendButton;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatBox;

    @FXML
    private HBox ynBox;

    @FXML
    private Button yesButton, noButton, anserButton;

    @FXML
    private ListView<String> participantListView;

    @FXML
    private Button exitButton;

    @FXML
    private TextField chatInputField;

    @FXML
    private Button chatSendButton;

    private GameService gameService;

    private String roomTitle;

    private Runnable onExitCallback;

    private boolean isHost = false;

    private String admin = "admin";



    public GameRoomController() {
        // GameService 초기화
        this.gameService = new GameService();
        this.gameService.setOnMessageReceived(this::handleMessageReceived);
    }

    @FXML
    private void initialize() {
        // 초기화 작업: 게임 내용과 참여자 목록을 설정할 수 있음
        // gameContentArea.setText("게임에 입장했습니다.");
        // gameContentArea.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        participantListView.getItems().addAll("Player 1", "Player 2", "Player 3");
    }

    @FXML
    private void handleSendQuestion() {
        String message = questionTextField.getText();
        if (!message.isEmpty()) {
            gameContentArea.appendText("\nYou: " + message);
            gameService.sendQuestion(message);
            questionTextField.clear();
            // 서버로 질문 전송 로직 추가 가능
        } else {
            System.out.println("Question field is empty. Please type a question.");
        }
    }

    /**
     * 방 제목을 설정하는 메서드
     *
     * @param roomTitle 생성된 방의 제목
     */
    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
        gameRoomLabel.setText("Room #" + roomTitle);
    }

    // isHost 값을 설정하는 메서드
    public void setHost(boolean isHost) {
        this.isHost = isHost;
        updateYnBoxVisibility();
    }

    /**
     * 채팅 전송 처리 메서드
     */
    @FXML
    private void handleSendChat() {
        String message = chatInputField.getText().trim();
        if (!message.isEmpty()) {
            addChatMessage(message, true); // 사용자 메시지 추가
            gameService.sendMessage(message); // 서버로 메시지 전송
            chatInputField.clear();
        }
    }

    /**
     * 메시지 수신 처리 메서드
     *
     * @param message 수신된 메시지
     */
    private void handleMessageReceived(String message) {
        Platform.runLater(() -> {
            addChatMessage(message, false); // 상대방 메시지 추가
        });
    }



    @FXML
    private void handleYes() {
        System.out.println("Yes button clicked.");
        // 게임 서비스에 "Yes" 응답 전송해야 함
        gameService.sendMessage("/네");
    }

    @FXML
    private void handleNo() {
        System.out.println("No button clicked.");
        // 게임 서비스에 "No" 응답 전송해야 함
        gameService.sendMessage("/아니오");
    }

    @FXML
    private void handleAnswer() {
        System.out.println("Cancel button clicked.");
        // 추가: 취소 처리를 위해 필요한 작업 수행해야 함
        gameService.sendMessage("/정답입니다");
    }



    /**
     * 방 나가기 처리 메서드
     */
    @FXML
    private void handleExitRoom() {
        System.out.println("Exiting the room...");

        // 서버에 방 나가기 요청
        if (gameService != null) {
            gameService.exitRoom();
        }

        if (onExitCallback != null) {
            onExitCallback.run();
        }

        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
    }


    /**
     * 채팅 메시지를 채팅 박스에 추가하는 메서드
     *
     * @param message 메시지 텍스트
     * @param isUser  메시지 발신자가 사용자인지 여부
     */
    private void addChatMessage(String message, boolean isUser) {
        HBox messageBox = UIUtils.createMessageBox(message, isUser);
        chatBox.getChildren().add(messageBox);

        chatScrollPane.layout();
        chatScrollPane.setVvalue(1.0);
    }

    /**
     * 외부에서 GameService를 설정할 수 있도록 하는 메서드
     *
     * @param gameService 설정할 GameService 인스턴스
     */
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
        this.gameService.setOnMessageReceived(this::handleMessageReceived);
    }

    /**
     * 방 나가기 콜백 설정 메서드
     */
    public void setOnExitCallback(Runnable callback) {
        this.onExitCallback = callback;
    }

    private void updateYnBoxVisibility() {
        if (isHost) {
            ynBox.setVisible(true);
            ynBox.setManaged(true);
        } else {
            ynBox.setVisible(false);
            ynBox.setManaged(false);
        }
    }

}
