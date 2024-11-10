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
    private Label roomlistLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private ListView<String> userListView;

    @FXML
    private Button createRoomButton;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/createroom.fxml"));
            Parent createRoomLayout = loader.load();

            Stage createRoomStage = new Stage();
            Scene createRoomScene = new Scene(createRoomLayout);

            // 스타일 시트를 적용하고 창을 보여줍니다.
            //createRoomScene.getStylesheets().add(getClass().getResource("/core/view/createroom.css").toExternalForm());
            System.out.println("createroom.css applied.");

            createRoomStage.setScene(createRoomScene);
            createRoomStage.setTitle("Create New Room");
            createRoomStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the create room.");
        }
    }
}