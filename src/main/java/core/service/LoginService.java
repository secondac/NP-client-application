package core.service;

import com.google.gson.Gson;
import core.model.dto.DTO;
import core.model.dto.LoginRequestDTO;
import core.model.dto.RequestType;
import core.model.dto.ResponseDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginService {

    private static final int SERVER_PORT = 10001;
    private final Gson gson = new Gson();

    public boolean login(String username, String serverAddress) {
        try (Socket socket = new Socket(serverAddress, SERVER_PORT);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // RequestType 및 LoginRequestDTO 생성
            RequestType requestType = RequestType.LOGIN;
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username,0);
            DTO requestDTO = new DTO(requestType, loginRequestDTO);

            String jsonRequest = gson.toJson(requestDTO);

            // 전송 전에 JSON 출력
            System.out.println("Sending JSON to server: " + jsonRequest);

            // JSON 전송
            out.println(jsonRequest);

            String jsonResponse = in.readLine();
            System.out.println("Received JSON from server: " + jsonResponse);


            ResponseDTO response = gson.fromJson(jsonResponse, ResponseDTO.class);

            System.out.println("Received response: " + response);
            return response.isSuccess();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}