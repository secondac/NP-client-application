package core.$__testserver;

import com.google.gson.Gson;
import core.model.dto.ResponseDTO;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class RoomRequestHandler implements Runnable {

    private final BlockingQueue<Socket> roomRequestQueue;
    private final Gson gson = new Gson();

    public RoomRequestHandler(BlockingQueue<Socket> roomRequestQueue) {
        this.roomRequestQueue = roomRequestQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = roomRequestQueue.take();
                handleRoomRequest(clientSocket);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRoomRequest(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)) {

            // Read and parse request
            String jsonRequest = in.readLine();
            DTO requestDTO = gson.fromJson(jsonRequest, DTO.class);

            // Simulate room response
            ResponseDTO response = new ResponseDTO();
            response.setSuccess(true);
            response.setMessage("Room list fetched successfully.");

            String jsonResponse = gson.toJson(response);
            out.println(jsonResponse);

            System.out.println("Processed room request.");
        } finally {
            clientSocket.close();
        }
    }
}