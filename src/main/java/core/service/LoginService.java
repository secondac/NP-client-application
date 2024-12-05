package core.service;

import com.google.gson.Gson;
import core.model.dto.DTO;
import core.model.dto.LoginRequestDTO;
import core.model.dto.RequestType;
import core.model.dto.ResponseDTO;

import core.model.dto.request.UserLogin;
import core.model.dto.response.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class LoginService {

    public static final int SERVER_PORT = 10001;
    public static String SERVER_ADDRESS = "43.203.212.19";
    //public static String SERVER_ADDRESS = "127.0.0.1";

    private final Gson gson = new Gson();

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String username;

    /**
     * 사용자 로그인 메서드.
     *
     * @param username      로그인할 사용자 이름
     * @param serverAddress 서버 주소
     * @return 로그인 성공 여부
     */
    public boolean login(String username, String serverAddress) {
        try {

            socket = new Socket(serverAddress, SERVER_PORT);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 로그인 요청 생성 및 전송
            RequestType requestType = RequestType.LOGIN;
            UserLogin userLogin = new UserLogin(username);
            DTO requestDTO = new DTO(requestType, userLogin); // 래핑된 DTO 사용
            String jsonRequest = gson.toJson(requestDTO);
            System.out.println("Sending JSON to server: " + jsonRequest);
            out.println(jsonRequest);

            // 서버 응답 처리
            String jsonResponse = in.readLine();
            System.out.println("Received JSON from server: " + jsonResponse);

            if (jsonResponse == null || jsonResponse.isEmpty()) {
                System.err.println("서버로부터 응답이 없습니다.");
                return false;
            }

            User userResponse = gson.fromJson(jsonResponse, User.class);
            System.out.println("Received user: " + userResponse);

            // 응답이 유효한지 확인
            if (userResponse != null && userResponse.getUsername() != null) {
                this.username = userResponse.getUsername();
                // 필요에 따라 추가 필드(예: score)도 저장할 수 있습니다.
                return true;
            } else {
                System.err.println("로그인 실패: 서버 응답이 유효하지 않습니다.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("로그인 중 예외 발생:");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 소켓과 스트림을 닫는 메서드.
     */
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Socket closed.");
        } catch (IOException e) {
            System.err.println("소켓 닫기 중 예외 발생:");
            e.printStackTrace();
        }
    }

    /**
     * 현재 로그인된 사용자 이름을 반환하는 메서드.
     *
     * @return 사용자 이름
     */
    public String getUsername() {
        return username;
    }
}