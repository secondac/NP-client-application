package core.$__legacyfiles;

import com.google.gson.Gson;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MsgConnectTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        // Message 객체 생성
        Message message = new Message(123, 456, "Hello, this is a test message!");

        // Gson을 사용하여 Message 객체를 JSON으로 변환
        Gson gson = new Gson();
        String json = gson.toJson(message);

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