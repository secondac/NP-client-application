package core.$__legacyfiles;


import com.google.gson.Gson;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        // 필요한 객체를 DTO로 래핑
        RequestType requestType = RequestType.CONNECTCHAT;
        ChatConnection chatConnection = new ChatConnection(456, 123); // userId와 chatId 설정

        DTO dto = new DTO(requestType, chatConnection);

        // Gson을 사용하여 DTO 객체를 JSON으로 변환
        Gson gson = new Gson();
        String json = gson.toJson(dto);

        // 서버로 JSON 전송
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println(json);  // JSON 데이터를 서버로 전송
            System.out.println("JSON 데이터 서버로 전송: " + json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}