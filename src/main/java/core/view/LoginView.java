package core.view;

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

            // 폰트 로드
            try (InputStream fontStream = getClass().getResourceAsStream("/core/font/gangwon_font_bold.ttf")) {
                if (fontStream != null) {
                    Font gangwonFont = Font.loadFont(fontStream, 18);
                    if (gangwonFont != null) {
                        System.out.println("Font loaded successfully: " + gangwonFont.getName());
                    } else {
                        System.out.println("Failed to load font.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
