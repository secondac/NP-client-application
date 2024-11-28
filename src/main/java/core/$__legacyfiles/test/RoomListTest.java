package core.$__legacyfiles.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.reflect.Type;

import core.common.GsonLocalDateTimeAdapter;
import core.model.dto.response.ListChatRoom;
import core.model.dto.response.ChatRoom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RoomListTest {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        // 커스텀 Gson 빌더에 LocalDateTime TypeAdapter 추가
        // Gson에 GsonLocalDateTimeAdapter를 등록
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create();

        // ROOMLIST 요청 생성
        RequestType requestType = RequestType.ROOMLIST;
        DTO dto = new DTO(requestType, null);  // Room List 요청은 특별한 메시지 없이 null로 설정

        // DTO 객체를 JSON으로 변환
        String json = gson.toJson(dto);

        // 서버로 JSON 전송
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // JSON 데이터를 서버로 전송
            out.println(json);
            System.out.println("ROOMLIST 요청 JSON 데이터 서버로 전송: " + json);

            // 서버로부터 JSON 응답 수신
            String responseJson = in.readLine();
            System.out.println("서버로부터 수신한 JSON 데이터: " + responseJson);

            // JSON 데이터를 ListChatRoom 객체로 변환
            Type listChatRoomType = new TypeToken<ListChatRoom>() {}.getType();
            ListChatRoom listChatRoom = gson.fromJson(responseJson, listChatRoomType);

            // 방 목록 출력
            System.out.println("수신한 방 목록:");
            for (ChatRoom chatRoom : listChatRoom.getChatRooms()) {
                System.out.println(chatRoom);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
