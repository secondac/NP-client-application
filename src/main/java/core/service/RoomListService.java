/**
 * RoomListService : 클라이언트가 로그인 후, 서버로 요청을 보냄
 * @Param Thread 사용
 * */

package core.service;

import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.Gson;

import core.model.Room;
import core.model.dto.request.*;
import core.model.dto.response.*;
import core.model.dto.*;


public class RoomListService extends Thread {

    private static final int SERVER_PORT = 10001;
    private final Gson gson = new Gson();

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // private String roomName;
    // ArrayList로 구현 ?
    /**
     * rooms : 방 목록을 ArrayList generic으로 담음
     */

    private ArrayList<Room> rooms;



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
    
    public boolean request(){
        
        
        
        return false; // 임시 주석
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
