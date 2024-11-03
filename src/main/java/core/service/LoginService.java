package core.service;

import core.model.dto.LoginRequestDTO;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class LoginService {

    public boolean authenticate(LoginRequestDTO loginRequest, String ipAddress, int port) {
        try (Socket socket = new Socket(ipAddress, port);
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

            // JSON ?
            String jsonInputString = "{ \"id\": \"" + loginRequest.getId() + "\", \"username\": \"" + loginRequest.getUsername() + "\" }";

            // 서버로 데이터 전송
            outputStream.writeUTF(jsonInputString);
            outputStream.flush();

            // 서버로부터 응답 받기
            String response = inputStream.readUTF();
            System.out.println("Response from server: " + response);

            return response.equals("OK");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}