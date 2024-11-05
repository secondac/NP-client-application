package core.view;

import core.ClientMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class LoginView {
    private Parent layout;
    private Stage stage;

    public LoginView() {
        try {
            // FXML 로드
            FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("loginview.fxml"));
            layout = loader.load();

            Font.loadFont(ClientMain.class.getResource("font/TRON.ttf").toExternalForm(), 10);
            Font.loadFont(ClientMain.class.getResource("font/BMDOHYEON.ttf").toExternalForm(), 10);
            Font.loadFont(ClientMain.class.getResource("font/gangwon_font_bold.ttf").toExternalForm(), 10);





            // Stage와 Scene 생성 및 설정
            stage = new Stage();
            Scene scene = new Scene(layout, 480, 240);
            scene.getStylesheets().add(getClass().getResource("/core/view/loginview.css").toExternalForm());
            System.out.println("loginview.css applied.");

            stage.setTitle("스무고개 게임");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getter
    public Parent getLayout() {
        return layout;
    }

    public Stage getStage() {
        return stage;
    }
}
