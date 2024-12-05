/**
 * RoomListService : 클라이언트가 로그인 후, 서버로 요청을 보냄
 * @Param Thread 사용
 * */

package core.service;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.common.GsonLocalDateTimeAdapter;
import core.model.Room;
import core.model.dto.request.*;
import core.model.dto.response.*;
import core.model.dto.*;

import static core.service.LoginService.SERVER_ADDRESS;
import static core.service.LoginService.SERVER_PORT;


public class RoomListService extends Thread {

    // private boolean connectedFlag = false;
    private final Gson gson = new Gson();
    private String userName;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int roomID;

    // private String roomName;
    // ArrayList로 구현 ?
    /**
     * rooms : 방 목록을 ArrayList generic으로 담음
     */

    private ArrayList<Room> rooms;

    /**
     * run : 서버로부터 연결되면 실행됨, 실시간으로 요청을 받아야 하므로, thread 위에서 실행됨
     */

    @Override
    public void run() {
        

    }
    
    
    
    /** roomlistRequest 
     *
     * RoomService의 인스턴스를 생성하고 .roomlistRequest를 통해 서버에 방 리스트 전달 요청
     * 로그인에 성공하면 true를 RoomService를 호출한 곳 (RoomListController ?)에 전달합니다.
     * 그러면, 그곳에서 RoomService.run()을 호출합니다.
     *
     * 예시: boolean roomlistRequest = roomService.request();
     * **/



    public List<Room> request(String serverAddress) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create();
        DTO dto = new DTO(RequestType.ROOMLIST, null);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 여기서 전송
            String json = gson.toJson(dto);
            out.println(json);
            System.out.println("ROOMLIST 요청 JSON 데이터 서버로 전송: " + json);

            // 여기서부터 수신
            String responseJson = in.readLine();
            if (responseJson == null || responseJson.isEmpty()) {
                System.err.println("서버 응답이 비어있습니다.");
                return null;
            }
            System.out.println("서버로부터 수신한 JSON 데이터: " + responseJson);

            // JSON 데이터를 ListChatRoom 객체로 변환
            Type listChatRoomType = new TypeToken<ListChatRoom>() {}.getType();
            ListChatRoom listChatRoom = gson.fromJson(responseJson, listChatRoomType);

            System.out.println("수신한 방 목록:");
            List<Room> rooms = new ArrayList<>();
            for (ChatRoom chatRoom : listChatRoom.getChatRooms()) {
                System.out.println(chatRoom);
                rooms.add(new Room(chatRoom.getId(), chatRoom.getName(), chatRoom.getCreator()));
            }

            return rooms; // 변환된 Room 리스트 반환


        } catch (Exception e) {
            System.err.println("ROOMLIST 요청 처리 중 오류 발생:");
            e.printStackTrace();
            return null;
        }
    }


    @Deprecated
    public boolean isConnected() {
        return true;
    }




}






























/* Room Class 내용


package core.model;

public class Room {
    private String name;
    private int currentPlayers;

    public Room(String name, int currentPlayers) {
        this.name = name;
        this.currentPlayers = currentPlayers;
    }

    public String getName() {
        return name;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }
}

 */
