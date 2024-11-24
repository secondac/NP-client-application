package core.$__testserver;

import com.google.gson.Gson;
import core.model.dto.LoginRequestDTO;
import core.model.dto.ResponseDTO;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class LoginRequestHandler implements Runnable {

    private final BlockingQueue<Socket> loginQueue;
    private final Gson gson = new Gson();

    public LoginRequestHandler(BlockingQueue<Socket> loginQueue) {
        this.loginQueue = loginQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = loginQueue.take();
                handleLogin(clientSocket);
            } catch (InterruptedException e) {
                System.err.println("LoginRequestHandler interrupted");
                Thread.currentThread().interrupt(); // Re-set interrupt flag
            }
        }
    }

    private void handleLogin(Socket clientSocket) {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            // Read and parse request
            String jsonRequest = in.readLine();
            DTO requestDTO = gson.fromJson(jsonRequest, DTO.class);
            LoginRequestDTO loginRequest = gson.fromJson(gson.toJson(requestDTO.getRequestMsg()), LoginRequestDTO.class);

            // Simulate login validation (Always successful for demonstration)
            boolean success = true;
            String message = "Login successful for username: " + loginRequest.getUsername();

            // Create and send response
            ResponseDTO response = new ResponseDTO();
            response.setSuccess(success);
            response.setMessage(message);

            String jsonResponse = gson.toJson(response);
            out.println(jsonResponse);

            System.out.println("Processed login for username: " + loginRequest.getUsername());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
}