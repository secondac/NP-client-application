package core.controller;

import core.service.RoomListService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import core.model.Room;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomListController {

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, Integer> currentPlayersColumn;

    @FXML
    private Label roomlistLabel;

    @FXML
    private Label currentUserName;

    @FXML
    private Label usernameLabel;

    @FXML
    private ListView<String> userListView;

    @FXML
    private Button createRoomButton;

    @FXML
    private Button joinRoomButton;

    private boolean isRoom = false;  // 방 상태 플래그

    @FXML
    private void initialize() {
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentPlayersColumn.setCellValueFactory(new PropertyValueFactory<>("currentPlayers"));

        // 서버에 연결하기 전 예시 데이터
        roomTable.getItems().add(new Room("Room A", 5));
        roomTable.getItems().add(new Room("Room B", 3));

        // 서버에 연결하기 전 예시 데이터
        usernameLabel.setText("User123");
        userListView.getItems().addAll("User1", "User2", "User3");


        // 버튼 상태 초기화
        updateButtonStates();
    }

    @FXML
    private void handleJoinRoom() {
        if (isRoom) {
            System.out.println("Cannot join room while another room is active.");
            return;
        }

        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            System.out.println("Joining room: " + selectedRoom.getName());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/gameroom.fxml"));
                Parent gameRoomLayout = loader.load();

                // GameRoomController 가져오기
                GameRoomController gameRoomController = loader.getController();

                // 방에 입장한 경우 Host가 아님
                gameRoomController.setHost(false);

                // 방 나가기 콜백 설정
                gameRoomController.setOnExitCallback(() -> Platform.runLater(this::handleRoomExit));


                Stage newStage = new Stage();
                Scene newScene = new Scene(gameRoomLayout);

                newScene.getStylesheets().add(getClass().getResource("/core/view/gameroom.css").toExternalForm());
                System.out.println("gameroom.css applied.");

                newStage.setScene(newScene);
                newStage.setTitle("Game Room");
                newStage.show();

                // 방에 성공적으로 들어갔으므로 플래그 설정 및 버튼 상태 업데이트
                isRoom = true;
                updateButtonStates();

                // 방 창이 닫힐 때 플래그 해제 및 버튼 상태 업데이트
                newStage.setOnCloseRequest(event -> {
                    isRoom = false;
                    System.out.println("Game room closed.");
                    updateButtonStates();
                });

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load the game room.");
            }
        } else {
            System.out.println("No room selected.");
        }
    }

    @FXML
    private void openGameRoom() {
        if (isRoom) {
            System.out.println("Cannot create room while another room is active.");
            return;
        }

        isRoom = true;
        updateButtonStates();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/createroom.fxml"));
            Parent createRoomLayout = loader.load();

            CreateRoomController createRoomController = loader.getController();

            // 방 나가기 콜백 설정
            createRoomController.setOnRoomExitCallback(() -> Platform.runLater(this::handleRoomExit));


            Stage createRoomStage = new Stage();
            Scene createRoomScene = new Scene(createRoomLayout);

            System.out.println("createroom.fxml loaded and scene created.");

            createRoomStage.setScene(createRoomScene);
            createRoomStage.setTitle("Create New Room");
            createRoomStage.show();

            // 방 만들기 창이 닫히면 플래그를 해제하고 버튼 상태를 업데이트
            createRoomStage.setOnCloseRequest(event -> {
                isRoom = false;
                System.out.println("Create room window closed.");
                updateButtonStates();
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the create room.");
            isRoom = false;
            updateButtonStates();
        }
    }

    /**
     * isRoom 상태에 따라 버튼 활성화 상태를 업데이트하는 메서드
     */
    private void updateButtonStates() {
        createRoomButton.setDisable(isRoom);
        joinRoomButton.setDisable(isRoom);
    }

    private void handleRoomExit() {
        System.out.println("Room exited.");
        isRoom = false;
        updateButtonStates();
    }
}