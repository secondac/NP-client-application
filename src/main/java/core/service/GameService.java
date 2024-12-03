package core.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.common.GsonLocalDateTimeAdapter;
import core.common.GsonMessageAdapter;
import core.model.Message;
import core.model.dto.DTO;
import core.model.dto.RequestType;
import core.model.dto.request.NewRoom;
import core.model.dto.response.ChatRoom;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class GameService {

    private LoginService loginService;
    private Consumer<String> onMessageReceived;
    private final Gson gson = new Gson();

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String requestUserName;
    private int requestRoomID;

    public GameService() {
    }

    public GameService(String requestUserName, int requestRoomID, Socket socket) {
        this.requestUserName = requestUserName;
        this.requestRoomID = requestRoomID;
        this.socket = socket;
    }

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
        //simulateServerResponse(message);
        try {
            //socket = new Socket(serverAddress, SERVER_PORT);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Message.class, new GsonMessageAdapter()) // 어댑터 등록
                    .create();

            RequestType requestType = RequestType.CONNECTCHAT;
            Message msg = new Message(this.requestRoomID,this.requestUserName,message);
            DTO requestDTO = new DTO(requestType,msg);
            String jsonRequest = gson.toJson(requestDTO);

            System.out.println("Sending JSON to server: " + jsonRequest);
            out.println(jsonRequest);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("메세지 전송 중 예외 발생");
        }
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

        try {
            //socket = new Socket(serverAddress, SERVER_PORT);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Message.class, new GsonMessageAdapter()) // 어댑터 등록
                    .create();

            RequestType requestType = RequestType.CONNECTCHAT;
            Message msg = new Message(this.requestRoomID,this.requestUserName,question);
            DTO requestDTO = new DTO(requestType,msg);
            String jsonRequest = gson.toJson(requestDTO);

            System.out.println("Sending JSON to server: " + jsonRequest);
            out.println(jsonRequest);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("메세지 전송 중 예외 발생");
        }

    }

    /**
     * 서버로부터 메시지를 수신했을 때 호출되는 메서드.
     *
     */
    public void receiveFromServer() {
        setOnMessageReceived(onMessageReceived);
        new Thread(() -> {
            try {
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    // 서버로부터 메시지를 읽음
                    String jsonResponse = in.readLine();
                    if (jsonResponse == null) {
                        System.out.println("서버와의 연결이 종료되었습니다.");
                        break;
                    }

                    // JSON 문자열을 Message 객체로 변환
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Message.class, new GsonMessageAdapter())
                            .create();
                    Message receivedMessage = gson.fromJson(jsonResponse, Message.class);
                    System.out.println(receivedMessage.toString());

                    // admin 메시지와 일반 메시지 처리
                    if ("admin".equals(receivedMessage.getUsername())) {
                        if("이미 20번의 질문기회를 사용하셨습니다.".equals(receivedMessage.getMessage())){
                            System.out.println("admin: 20개 질문 완료로 게임 종료");
                        }
                        else {
                            System.out.println("admin: " + receivedMessage.getMessage());
                        }
                    } else {
                        System.out.println("[" + receivedMessage.getUsername() + "]: " + receivedMessage.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("메시지 수신 중 오류 발생: " + e.getMessage());
            }finally {
                // 자원 정리
                try {
                    if (socket != null && !socket.isClosed()) socket.close();
                    if (in != null) in.close();
                    if (out != null) out.close();
                    System.out.println("자원이 성공적으로 해제되었습니다.");
                } catch (IOException e) {
                    System.err.println("소켓 닫는 중 오류 발생: " + e.getMessage());
                }
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

        try {
            //socket = new Socket(serverAddress, SERVER_PORT);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 방 생성 요청 전송
            RequestType requestType = RequestType.NEWROOM;
            NewRoom newRoom = new NewRoom(this.requestUserName,roomTitle);
            DTO requestDTO = new DTO(requestType, newRoom); // 래핑된 DTO 사용
            String jsonRequest = gson.toJson(requestDTO);
            System.out.println("Sending JSON to server: " + jsonRequest);
            out.println(jsonRequest);

            // 서버 응답 처리
            String jsonResponse = in.readLine();
            System.out.println("Received JSON from server: " + jsonResponse);

            if (jsonResponse == null || jsonResponse.isEmpty()) {
                System.err.println("서버로부터 응답이 없습니다.");
                return false;
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()) // 어댑터 등록
                    .create();

            ChatRoom roomResponse = gson.fromJson(jsonResponse, ChatRoom.class);
            System.out.println("Received user: " + roomResponse);

            // 응답이 유효한지 확인
            if (roomResponse != null) {
                System.out.println(roomResponse.getName() + "방 생성 완료");
                return true;
            } else {
                System.err.println("방 생성 실패: 서버 응답이 유효하지 않습니다.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("방 생성 중 예외 발생:");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 방을 나가는 로직을 구현합니다.
     */
    public void exitRoom() {
        // 실제 방 나가기 로직을 구현해야 합니다.
        try {
            // 소켓이 열려 있는 경우 닫기
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("채팅방에서 나갔습니다.");
            } else {
                System.out.println("이미 소켓이 닫혀 있습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("소켓 종료 중 예외 발생");
        }
    }
}
