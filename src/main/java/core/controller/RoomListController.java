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

public class RoomListController {

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> roomNameColumn;

    @FXML
    private TableColumn<Room, Integer> currentPlayersColumn;

    @FXML
    private Label usernameLabel;

    @FXML
    private ListView<String> userListView;

    @FXML
    private Button refreshButton;

    @FXML
    private Button joinRoomButton;

    @FXML
    private void initialize() {
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        currentPlayersColumn.setCellValueFactory(new PropertyValueFactory<>("currentPlayers"));

        // sample data
        roomTable.getItems().add(new Room("Room A", 5));
        roomTable.getItems().add(new Room("Room B", 3));

        // sample data
        usernameLabel.setText("User123");
        userListView.getItems().addAll("User1", "User2", "User3");
    }

    @FXML
    private void handleRefresh() {
        System.out.println("Refreshing room list...");
    }

    @FXML
    private void handleJoinRoom() {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            System.out.println("Joining room: " + selectedRoom.getName());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/gameroom.fxml"));
                Parent gameRoomLayout = loader.load();

                // 새로운 Stage 생성
                Stage newStage = new Stage();
                newStage.setScene(new Scene(gameRoomLayout));
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

    private void openGameRoom() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/gameroom.fxml"));
            Parent gameRoomLayout = loader.load();

            Stage currentStage = (Stage) roomTable.getScene().getWindow();
            currentStage.setScene(new Scene(gameRoomLayout));
            currentStage.setTitle("Game Room");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game room.");
        }
    }
}