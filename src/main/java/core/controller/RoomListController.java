package core.controller;

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    // private final Lock lock = new ReentrantLock();

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
    }

    private boolean isCreateRoomOpen = false;  // flag로 lock과 유사하게 설정함

    @FXML
    private void handleJoinRoom() {
        if (isCreateRoomOpen) { // 일종의 lock, createroom 열려 있으면 실행하지 않음
            System.out.println("Cannot join room while the create room window is open.");
            return;
        }

        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            System.out.println("Joining room: " + selectedRoom.getName());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/gameroom.fxml"));
                Parent gameRoomLayout = loader.load();

                Stage newStage = new Stage();
                Scene newScene = new Scene(gameRoomLayout);

                newScene.getStylesheets().add(getClass().getResource("/core/view/gameroom.css").toExternalForm());
                System.out.println("gameroom.css applied.");

                newStage.setScene(newScene);
                newStage.setTitle("Game Room");
                newStage.show();

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
        if (isCreateRoomOpen) {
            System.out.println("Create room window is already open.");
            return;
        }

        isCreateRoomOpen = true;


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/createroom.fxml"));
            Parent createRoomLayout = loader.load();

            Stage createRoomStage = new Stage();
            Scene createRoomScene = new Scene(createRoomLayout);

            System.out.println("createroom.fxml loaded and scene created.");

            createRoomStage.setScene(createRoomScene);
            createRoomStage.setTitle("Create New Room");
            createRoomStage.show();

            // When close stage, unlock

            createRoomStage.setOnCloseRequest(event -> {
                isCreateRoomOpen = false;
                System.out.println("Create room window closed.");
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the create room.");
            isCreateRoomOpen = false;
        }
    }
}