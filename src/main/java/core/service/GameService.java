package core.service;


import java.util.function.Consumer;

public class GameService {
    private Consumer<String> onMessageReceived;

    /**
     * 메시지 수신 시 호출될 핸들러를 설정합니다.
     *
     * @param handler 메시지 수신 핸들러
     */
    public void setOnMessageReceived(Consumer<String> handler) {
        this.onMessageReceived = handler;
    }

    /**
     * 서버로 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     */
    public void sendMessage(String message) {
        // 실제 서버로 메시지를 전송하는 로직을 구현해야 합니다.
        // 여기서는 예시로 시뮬레이션된 응답을 사용합니다.
        simulateServerResponse(message);
    }

    /**
     * 질문을 서버로 전송합니다.
     *
     * @param question 전송할 질문
     */
    public void sendQuestion(String question) {
        // 실제 서버로 질문을 전송하는 로직을 구현해야 합니다.
        // 여기서는 예시로 게임 내용 영역에 질문을 추가하는 로직을 호출할 수 있습니다.
        // 예를 들어, 컨트롤러에 콜백을 설정하여 처리할 수 있습니다.
    }

    /**
     * 서버로부터 메시지를 수신했을 때 호출되는 메서드.
     *
     * @param message 수신된 메시지
     */
    private void receiveFromServer(String message) {
        if (onMessageReceived != null) {
            onMessageReceived.accept(message);
        }
    }

    /**
     * 서버 응답을 시뮬레이션하는 메서드 (예시용).
     *
     * @param message 사용자 메시지
     */
    private void simulateServerResponse(String message) {
        // 실제 서버 통신에서는 이 메서드를 사용하지 않습니다.
        // 예시로 1초 후에 상대방의 메시지를 수신
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                String response = "상대방: " + message;
                receiveFromServer(response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 방을 생성하는 메서드
     *
     * @param roomTitle 생성할 방의 제목
     * @return 방 생성 성공 여부
     */
    public boolean createRoom(String roomTitle) {
        // 실제 방 생성 로직 구현
        // 예: 서버에 HTTP 요청을 보내거나, 데이터베이스에 방 정보를 저장

        // 여기서는 예시로 항상 성공했다고 가정
        System.out.println("방 생성 요청: " + roomTitle);
        return true;
    }


    /**
     * 방을 나가는 로직을 구현합니다.
     */
    public void exitRoom() {
        // 실제 방 나가기 로직을 구현해야 합니다.
    }
}
