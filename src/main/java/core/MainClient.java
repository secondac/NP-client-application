package core;

import core.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load the initial login view
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getLayout(), 800, 600);

        primaryStage.setTitle("Twenty Questions Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
