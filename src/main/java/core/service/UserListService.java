package core.service;


import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;
import com.google.gson.Gson;
import java.lang.reflect.Type;


import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.common.GsonLocalDateTimeAdapter;
import core.model.Room;
import core.model.dto.request.*;
import core.model.dto.response.*;
import core.model.dto.*;

public class UserListService {

    private static final int SERVER_PORT = 10001;
    private final Gson gson = new Gson();
    private String userName;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private final Integer i = 0;

    public List<String> request(String serverAddress){
        //

        //System.out.println("i = " + i);



        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
                .create();
        DTO dto = new DTO(RequestType.USERLIST, i);

        try (Socket socket = new Socket(serverAddress, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json = gson.toJson(dto);
            out.println(json);
            System.out.println("USERLIST 요청 JSON 데이터 서버로 전송: " + json);


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
            System.err.println("USERLIST 요청 처리 중 오류 발생:");
            e.printStackTrace();
            return null;
        }
    }







}