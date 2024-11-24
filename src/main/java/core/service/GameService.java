package core.service;


import java.util.function.Consumer;

public class GameService {
    private Consumer<String> onMessageReceived;

    public void setOnMessageReceived(Consumer<String> handler) {
        this.onMessageReceived = handler;
    }

    public void sendMessage(String message) {
        // 서버로 메시지 전송 로직 구현
    }

    // 서버로부터 메시지를 수신했을 때 호출되는 메서드
    private void receiveFromServer(String message) {
        if (onMessageReceived != null) {
            onMessageReceived.accept(message);
        }
    }
}
