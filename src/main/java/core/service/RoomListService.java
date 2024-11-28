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






}
