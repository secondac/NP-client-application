package core.$__legacyfiles.test;


import com.google.gson.Gson;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectChatTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        Gson gson = new Gson();


        // CONNECTCHAT
        RequestType requestType1 = RequestType.CONNECTCHAT;
        ChatConnection chatConnection = new ChatConnection(4413456, 121353); // userId와 chatId 설정
        DTO dto1 = new DTO(requestType1, chatConnection);
        String json1 = gson.toJson(dto1);

        // NEWROOM
        RequestType requestType2 = RequestType.NEWROOM;
        NewRoom newRoom = new NewRoom("TESTER", "TEST TITLE");
        DTO dto2 = new DTO(requestType2, newRoom);
        String json2 = gson.toJson(dto2);

        // SENDMESSAGE
        RequestType requestType3 = RequestType.ROOMLIST;
        Message message = new Message(123, 456, "Hello, this is a test message!"); // chatId, userId, message 설정
        DTO dto3 = new DTO(requestType3, message);
        String json3 = gson.toJson(dto3);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println(json1);
            System.out.println("CONNECTCHAT 요청 JSON 데이터 서버로 전송: " + json1);

            Thread.sleep(100);
            out.println(json2);
            System.out.println("NEWROOM 요청 JSON 데이터 서버로 전송: " + json2);

            Thread.sleep(100);
            out.println(json3);
            System.out.println("SENDMESSAGE 요청 JSON 데이터 서버로 전송: " + json3);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}