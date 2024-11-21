package core.$__testserver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class GameRequestHandler implements Runnable {
    private final BlockingQueue<Socket> gameRequestQueue;

    public GameRequestHandler(BlockingQueue<Socket> gameRequestQueue) {
        this.gameRequestQueue = gameRequestQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = gameRequestQueue.take(); // Wait for a request
                System.out.println("Processing Game Request from: " + clientSocket);
                // Handle game request logic
                clientSocket.close(); // Close connection after handling
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
