package core.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import core.model.Room;

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
        }
    }
}