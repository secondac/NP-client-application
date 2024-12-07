package core.util;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UIUtils {

    /**
     * 메시지 박스를 생성합니다.
     *
     * @param message 메시지 텍스트
     * @param isUser  메시지 발신자가 사용자인지 여부
     * @return 생성된 HBox 메시지 박스
     */
    public static HBox createMessageBox(String message, boolean isUser) {
        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(5, 10, 5, 10));

        Label text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(250); // 말풍선의 최대 너비 설정

        if (isUser) {
            // 사용자 메시지 (우측 정렬)
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            text.getStyleClass().add("user-bubble");
        } else if (message.startsWith("[관리자]")) {
            // 관리자 메시지 (중앙 정렬)
            messageBox.setAlignment(Pos.CENTER);
            text.getStyleClass().add("admin-bubble");
        } else {
            // 상대방 메시지 (좌측 정렬)
            messageBox.setAlignment(Pos.CENTER_LEFT);
            text.getStyleClass().add("opponent-bubble");
        }

//        Label text = new Label(message);
//        text.setWrapText(true);
//        text.setMaxWidth(250); // 말풍선의 최대 너비 설정
//        text.getStyleClass().add(isUser ? "admin-bubble" : "opponent-bubble");

        messageBox.getChildren().add(text);
        return messageBox;
    }
}