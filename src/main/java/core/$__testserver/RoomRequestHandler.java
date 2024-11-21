package core.$__testserver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class RoomRequestHandler implements Runnable {
    private final BlockingQueue<Socket> roomRequestQueue;

    public RoomRequestHandler(BlockingQueue<Socket> roomRequestQueue) {
        this.roomRequestQueue = roomRequestQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = roomRequestQueue.take(); // Wait for a request
                System.out.println("Processing Room Request from: " + clientSocket);
                // Handle room request logic
                clientSocket.close(); // Close connection after handling
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}