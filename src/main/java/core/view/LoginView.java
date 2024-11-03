package core.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Font;

import java.io.IOException;

public class LoginView {
    private Parent layout;

    public LoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("loginview.fxml"));
            layout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getLayout() {
        return layout;
    }
}