package core;

import core.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {
    @Override
    public void start(Stage primaryStage) {

        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getLayout(), 480, 240);

        primaryStage.setTitle("스무고개 게임");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
