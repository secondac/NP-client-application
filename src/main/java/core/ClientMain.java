package core;

import core.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class ClientMain extends Application {
    @Override
    public void start(Stage primaryStage) {

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

        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getLayout(), 480, 240);

        scene.getStylesheets().add(getClass().getResource("styles.css").toString());
        String s = getClass().getResource("styles.css").toString();
        System.out.println(s);

        primaryStage.setTitle("스무고개 게임");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
