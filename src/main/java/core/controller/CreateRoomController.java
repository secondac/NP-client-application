package core.controller;

import core.service.GameService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateRoomController {

    @FXML
    private Label createLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField roomTitleField;

    @FXML
    private Button createRoomButton; // 방 생성 버튼

    @FXML
    private Button cancelButton; // 취소 버튼

    private GameService gameService;

    // RoomListController의 콜백 설정
    private Runnable onRoomExitCallback;




    public CreateRoomController() {
        // GameService 초기화 (필요에 따라 의존성 주입을 사용할 수 있음)
        this.gameService = new GameService();
    }

    @FXML
    private void initialize() {
        // 초기화 작업이 필요하다면 여기에 추가
    }

    /**
     * 방 생성 버튼 클릭 시 호출되는 메서드
     */
    @FXML
    private void handleCreateRoom() {
        String roomTitle = roomTitleField.getText().trim();

        if (roomTitle.isEmpty()) {
            showAlert("입력 오류", "방 제목을 입력하세요.", AlertType.WARNING);
            return;
        }

        // 방 생성 로직 (GameService를 통해 서버에 요청 등)
        boolean success = gameService.createRoom(roomTitle);

        if (success) {
            showAlert("성공", "방이 성공적으로 생성되었습니다!", AlertType.INFORMATION);
            openGameRoom(roomTitle); // GameRoom.fxml 열기
            closeWindow(); // 현재 창 닫기
        } else {
            showAlert("실패", "방 생성에 실패했습니다. 방 제목을 확인해주세요.", AlertType.ERROR);
        }
    }

    /**
     * 취소 버튼 클릭 시 호출되는 메서드
     */
    @FXML
    private void handleCancel() {
        // 취소 버튼을 눌렀을 때도 onRoomExitCallback 호출
        if (onRoomExitCallback != null) {
            onRoomExitCallback.run();
        }
        closeWindow();
    }

    /**
     * 경고 및 정보 메시지를 표시하는 메서드
     *
     * @param title   메시지 제목
     * @param message 메시지 내용
     * @param type    메시지 유형
     */
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setOnRoomExitCallback(Runnable onRoomExitCallback) {
        this.onRoomExitCallback = onRoomExitCallback;
    }


    /**
     * 현재 창을 닫는 메서드
     */
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * GameRoom.fxml을 열어주는 메서드
     *
     * @param roomTitle 생성된 방의 제목
     */
    private void openGameRoom(String roomTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/gameroom.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/core/view/gameroom.css").toExternalForm());


            // GameRoomController에 방 제목 전달
            GameRoomController gameRoomController = loader.getController();
            gameRoomController.setRoomTitle(roomTitle);

            // 방을 생성했으므로 Host로 설정
            gameRoomController.setHost(true);

            // 방 나가기 콜백
            gameRoomController.setOnExitCallback(() -> {
                if (onRoomExitCallback != null) {
                    onRoomExitCallback.run();
                }
            });

            // 새로운 Stage 생성
            Stage gameRoomStage = new Stage();

            gameRoomStage.setTitle("방: " + roomTitle);
            gameRoomStage.setScene(scene); // 크기는 필요에 따라 조정
            gameRoomStage.show();

            gameRoomStage.setOnCloseRequest(event -> {
                if (onRoomExitCallback != null) {
                    onRoomExitCallback.run();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("오류", "게임 방을 여는 중 오류가 발생했습니다.", AlertType.ERROR);
        }
    }



}