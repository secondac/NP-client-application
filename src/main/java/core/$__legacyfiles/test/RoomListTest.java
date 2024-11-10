package core.$__legacyfiles.test;

import com.google.gson.Gson;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class RoomListTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        Gson gson = new Gson();

        // ROOMLIST 요청 생성
        RequestType requestType = RequestType.ROOMLIST;
        DTO dto = new DTO(requestType, null);  // Room List 요청은 특별한 메시지 없이 null로 설정

        // DTO 객체를 JSON으로 변환
        String json = gson.toJson(dto);

        // 서버로 JSON 전송
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            // JSON 데이터를 서버로 전송
            out.println(json);
            System.out.println("ROOMLIST 요청 JSON 데이터 서버로 전송: " + json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
