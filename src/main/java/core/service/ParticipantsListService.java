package core.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.common.GsonLocalDateTimeAdapter;
import core.model.dto.DTO;
import core.model.dto.RequestType;
import core.model.dto.response.ListUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static core.service.LoginService.SERVER_ADDRESS;
import static core.service.LoginService.SERVER_PORT;

public class ParticipantsListService {
    private final Gson gson = new Gson();
    private String userName;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private int roomid;

    public List<String> request(int roomid){
        //
        this.roomid = roomid;
        System.out.println("roomid = " + roomid);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create();
        DTO dto = new DTO(RequestType.USERLIST, roomid);

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json = gson.toJson(dto);
            out.println(json);
            System.out.println("참가자 목록 요청 JSON 데이터 서버로 전송: " + json);


            // 여기서부터 수신
            String responseJson = in.readLine();
            if (responseJson == null || responseJson.isEmpty()) {
                System.err.println("서버 응답이 비어있습니다.");
                return null;
            }
            System.out.println("서버로부터 수신한 JSON 데이터: " + responseJson);

            // JSON 데이터를 ListUser 객체로 변환

            Type listUserType = new TypeToken<ListUser>() {}.getType();
            ListUser listUser = gson.fromJson(responseJson, listUserType);

            // 유저 목록 반환
            System.out.println("수신한 유저 목록:");
            List<String> users = new ArrayList<>(listUser.getUsers());
            for (String user : users) {
                System.out.println(user);
            }

            return users;

            /*
            System.out.println("수신한 유저 목록:");
            List<String> users = new ArrayList<>();

            for (String user : listUser.getUsers()) {
                System.out.println(user);
                users.add(user);
            }

            return users;

             */

        } catch (Exception e) {
            System.err.println("참가자리스트 요청 처리 중 오류 발생:");
            e.printStackTrace();
            return null;
        }
    }







}