package core.$__legacyfiles.test;

import com.google.gson.Gson;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class NewRoomTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        Gson gson = new Gson();

        // NEWROOM 요청 생성
        RequestType requestType = RequestType.NEWROOM;
        NewRoom newRoom = new NewRoom("TESTER", "TEST TITLE");
        DTO dto = new DTO(requestType, newRoom);
        String json = gson.toJson(dto);

        // 서버로 JSON 전송
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println(json);  // JSON 데이터를 서버로 전송
            System.out.println("NEWROOM 요청 JSON 데이터 서버로 전송: " + json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}