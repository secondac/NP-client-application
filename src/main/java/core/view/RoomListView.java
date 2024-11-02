package core.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class RoomListView {

    private Parent layout;

    public RoomListView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/core/view/roomlist.fxml"));
            layout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getLayout() {
        return layout;
    }
}