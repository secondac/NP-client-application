package core.$__testserver;

import com.google.gson.Gson;
import core.model.dto.ResponseDTO;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class GameRequestHandler implements Runnable {

    private final BlockingQueue<Socket> gameRequestQueue;
    private final Gson gson = new Gson();

    public GameRequestHandler(BlockingQueue<Socket> gameRequestQueue) {
        this.gameRequestQueue = gameRequestQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = gameRequestQueue.take();
                handleGameRequest(clientSocket);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleGameRequest(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)) {

            // Read and parse request
            String jsonRequest = in.readLine();
            DTO requestDTO = gson.fromJson(jsonRequest, DTO.class);

            // Simulate game response
            ResponseDTO response = new ResponseDTO();
            response.setSuccess(true);
            response.setMessage("Game request processed successfully.");

            String jsonResponse = gson.toJson(response);
            out.println(jsonResponse);

            System.out.println("Processed game request.");
        } finally {
            clientSocket.close();
        }
    }
}