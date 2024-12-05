package core.controller;

import core.service.RoomListService;
import core.service.UserListService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import core.model.Room;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RoomListController {

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, Integer> idColumn;

    @FXML
    private TableColumn<Room, String> roomNameColumn, hostColumn;

    @FXML
    private Label usernameLabel, roomlistLabel, currentUserName, userListLabel;

    @FXML
    private ListView<String> userListView;

    @FXML
    private Button createRoomButton, joinRoomButton, exitButton, refreshRoomButton, refreshUserButton;


    // private static final String ADDRESS = "43.203.212.19";

    // private static final String ADDRESS = "43.203.212.19";
    private static final String ADDRESS = "127.0.0.1"; // 43.203.212.19
    // private static final String ADDRESS = "127.0.0.1"; // 43.203.212.19

    String userName;
    private boolean isRoom = false;  // 방 상태 플래그

    private List<Room> rooms;

    @FXML
    private void initialize() {

        /**
         * 각 필드의 getter 가 반드시 필요함
         */

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        hostColumn.setCellValueFactory(new PropertyValueFactory<>("Host"));

        roomTable.getColumns().clear(); // 기존 컬럼 초기화
        roomTable.getColumns().addAll(idColumn, roomNameColumn, hostColumn); // 원하는 컬럼만 추가

        // Sample data : 연결 성공시 삭제
        //roomTable.getItems().add(new Room(9999,"Room A", "hostA"));
        //roomTable.getItems().add(new Room(2222,"Room B", "hostB"));
        usernameLabel.setText("User123");

        userListView.getItems().addAll("User1", "User2", "User3");

        // sendRequest to server
        sendRoomListRequest();
        sendUserListRequest();

        // 버튼 상태 초기화
        updateButtonStates();

        // 테이블뷰 너비조정
        roomTable.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double totalWidth = newWidth.doubleValue();
            idColumn.setPrefWidth(totalWidth * 0.1); // 10% 너비
            roomNameColumn.setPrefWidth(totalWidth * 0.6); // 60% 너비
            hostColumn.setPrefWidth(totalWidth * 0.298); // 전체 합 100% 되면 스크롤바 생김
        });

        roomTable.lookupAll(".scroll-bar").forEach(scrollBar -> {
            scrollBar.setVisible(false);  // 스크롤바를 숨김
            scrollBar.setManaged(false); // 스크롤바가 레이아웃 공간을 차지하지 않도록 설정
        });

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

                GameRoomController gameRoomController = loader.getController();
                gameRoomController.setHost(false);
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


    @FXML
    private void refreshRoom(){

        sendRoomListRequest();
    }

    @FXML
    private void refreshUser(){

        sendUserListRequest();
    }

    private void deleteRoomList(){

    }

    private void sendRoomListRequest(){
        // RoomListService 호출
        System.out.println("roomlistService test");
        RoomListService roomListService = new RoomListService();
        rooms = roomListService.request(ADDRESS);
        System.out.println("roomlistService.request: " + rooms);

        // 연결에 성공하면 동기화를 위한 thread 실행부분 추가 예정입니다
        if(rooms != null){
            roomTable.getItems().clear();
            roomTable.getItems().addAll(rooms);
            roomListService.start();
        } else {
            System.out.println("연결 실패");
        }
    }

    private void sendUserListRequest(){
        // 유저 목록 가져오기
        System.out.println("userListService test");
        UserListService userListService = new UserListService();
        List<String> users = userListService.request(ADDRESS);

        if (users != null) {
            // 유저 목록 업데이트
            userListView.getItems().clear();
            userListView.getItems().addAll(users);
            System.out.println("유저 목록 업데이트 완료: " + users);
        } else {
            System.out.println("유저 목록 가져오기 실패");
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