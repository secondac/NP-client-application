package core.$__testserver;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class LoginRequestHandler implements Runnable {
    private final BlockingQueue<Socket> loginQueue;

    public LoginRequestHandler(BlockingQueue<Socket> loginQueue) {
        this.loginQueue = loginQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = loginQueue.take(); // Wait for a request
                System.out.println("Processing Login Request from: " + clientSocket);
                // Handle the login logic
                clientSocket.close(); // Close connection after handling
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}