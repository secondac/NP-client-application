package core.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class GameRoomView {

    private Parent layout;

    public GameRoomView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/gameroom.fxml"));
            layout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game room view.");
        }
    }

    public Parent getLayout() {
        return layout;
    }
}