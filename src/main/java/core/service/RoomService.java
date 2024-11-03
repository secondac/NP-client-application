package core.service;

import core.model.dto.RoomRequestDTO;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class RoomService {

    public boolean joinRoom(RoomRequestDTO joinRequest, String ipAddress, int port) {
        try (Socket socket = new Socket(ipAddress, port);
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

            // JSON 형식의 요청 데이터 작성
            String jsonInputString = "{ \"userId\": \"" + joinRequest.getUserId() + "\", \"roomId\": \"" + joinRequest.getRoomId() + "\" }";

            // 서버로 데이터 전송
            outputStream.writeUTF(jsonInputString);
            outputStream.flush();

            // 서버로부터 응답 수신
            String response = inputStream.readUTF();
            return response.equals("OK"); // 성공 여부 확인

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
