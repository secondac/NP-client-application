package core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.common.GsonLocalDateTimeAdapter;
import core.common.GsonMessageAdapter;
import core.common.InputThread;
import core.model.Message;
import core.model.dto.DTO;
import core.model.dto.RequestType;
import core.model.dto.request.ChatConnection;
import core.model.dto.request.NewRoom;
import core.model.dto.response.ChatRoom;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class GameService {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Message.class, new GsonMessageAdapter())
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
            .create();

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String requestUserName;
    private int requestRoomID;
    private int roomId;
    private Consumer<String> onMessageReceived;

    public GameService() {
    }

    public GameService(String requestUserName, int requestRoomID, Socket socket) {
        this.requestUserName = requestUserName;
        this.requestRoomID = requestRoomID;
        this.socket = socket;
        initializeSocketStreams();
        sendToServer(new DTO(RequestType.CONNECTCHAT, new ChatConnection(requestUserName,requestRoomID)));
    }

    /**
     * 소켓 및 스트림 초기화
     */
    private void initializeSocketStreams() {
        try {
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("소켓 스트림 초기화 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 메시지 수신 핸들러 설정
     */
    public void setOnMessageReceived(Consumer<String> handler) {
        this.onMessageReceived = handler;
    }

    /**
     * 서버로 메시지 전송
     */
    public void sendMessage(String message) {
        try {
            String jsonRequest = gson.toJson(new Message(requestRoomID, requestUserName, message));
            System.out.println(jsonRequest);
            out.println(jsonRequest);
        } catch (Exception e) {
            System.err.println("서버로 메시지 전송 실패: " + e.getMessage());
        }
    }

    /**
     * 질문 전송
     */
    public void sendQuestion(String question) {
        sendMessage(question);
    }

    /**
     * 방 생성 요청
     */
    public boolean createRoom(String roomTitle) {
        NewRoom newRoom = new NewRoom(requestUserName, roomTitle);
        DTO requestDTO = new DTO(RequestType.NEWROOM, newRoom);
        String responseRoom = sendToServerAndGetResponse(requestDTO);
        ChatRoom chatRoom = gson.fromJson(responseRoom, ChatRoom.class);
        if (chatRoom != null) {
            System.out.println("방 생성 완료: " + chatRoom.getName());
            this.roomId = chatRoom.getId();
            return true;
        } else {
            System.err.println("방 생성 실패: 서버 응답이 유효하지 않습니다.");
            return false;
        }
    }

    /**
     * 서버와의 메시지 수신 시작
     */
    public void receiveFromServer() {
        new Thread(() -> {
            try {
                while (true) {
                    String jsonResponse = in.readLine();
                    System.out.println(jsonResponse);
                    if (jsonResponse == null) {
                        System.out.println("서버와의 연결이 종료되었습니다.");
                        break;
                    }

                    Message receivedMessage = gson.fromJson(jsonResponse, Message.class);
                    handleReceivedMessage(receivedMessage);
                }
            } catch (IOException e) {
                System.err.println("메시지 수신 중 오류 발생: " + e.getMessage());
            }
        }).start();
    }

    /**
     * 서버로 메시지 전송 후 응답 수신
     */
    private String sendToServerAndGetResponse(DTO requestDTO) {
        try {
            String jsonRequest = gson.toJson(requestDTO);
            System.out.println(jsonRequest);
            out.println(jsonRequest);
            String responseRoom = in.readLine();
            return responseRoom;
        } catch (IOException e) {
            System.err.println("서버 응답 수신 실패: " + e.getMessage());
            return null;
        }
    }

    /**
     * 서버로 메시지 전송
     */
    private void sendToServer(DTO requestDTO) {
        try {
            String jsonRequest = gson.toJson(requestDTO);
            System.out.println(jsonRequest);
            out.println(jsonRequest);
        } catch (Exception e) {
            System.err.println("서버로 메시지 전송 실패: " + e.getMessage());
        }
    }

    /**
     * 수신된 메시지 처리
     */
    private void handleReceivedMessage(Message message) {
        if ("admin".equals(message.getUsername())) {
            // 관리자의 메시지 처리
            switch (message.getMessage()) {
                case "이미 20번의 질문기회를 사용하셨습니다.":
                    System.out.println("[관리자]: " + message.getMessage());
                    synchronized (this) {
                        exitRoom(); // 모든 소켓 연결 종료
                    }
                    System.out.println("메인 화면으로 돌아갑니다.");
                    break;

                case "정답입니다.":
                    System.out.println("[관리자]: " + message.getMessage());
                    System.out.println("");
                    break;

                default:
                    System.out.println("[관리자]: " + message.getMessage());
                    break;
            }
        } else {
            // 참가자의 메시지 처리
            System.out.println("[" + message.getUsername() + "] (메시지 번호): " + message.getMessage());
        }

        if (onMessageReceived != null) {
            onMessageReceived.accept(message.getMessage());
        }
    }

    /**
     * 소켓 및 스트림 자원 정리
     */
    public synchronized void exitRoom() {
        try {
            // 소켓과 스트림 닫기
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }

            // 자원 해제 완료 메시지
            System.out.println("채팅방 연결이 종료되었습니다. 메인 화면으로 돌아갑니다.");

        } catch (IOException e) {
            System.err.println("자원 정리 중 오류 발생: " + e.getMessage());
        }
    }

    public int getRoomId() {
        return this.roomId;
    }

//    public static void main(String[] args) {
//        try {
//            // 서버와의 연결 설정
//            Socket socket1 = new Socket("127.0.0.1", 10001);
//            GameService aliceService = new GameService("Alice", 1, socket1);
//            aliceService.receiveFromServer();
//
//            Socket socket2 = new Socket("127.0.0.1", 10001);
//            GameService bobService = new GameService("Bob", 1, socket2);
//            bobService.receiveFromServer();
//
//            // Alice가 메시지 전송
//            for (int i = 1; i <= 20; i++) {
//                aliceService.sendMessage("질문 " + i);
//                Thread.sleep(200);
//            }
//
//            // 21번째 메시지 전송 시 게임 종료
//            aliceService.sendMessage("질문 21");
//
//            // 정답 메시지 테스트
//            aliceService.sendToServer(new DTO(RequestType.CONNECTCHAT, new Message(1, "admin", "정답입니다.")));
//            Thread.sleep(1000);
//
//            // 프로그램 종료
//            aliceService.exitRoom();
//            bobService.exitRoom();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}


