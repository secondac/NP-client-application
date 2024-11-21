package core.$__testserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestServer {

    private static final int SERVER_PORT = 10001;

    public static void main(String[] args) {
        BlockingQueue<Socket> loginQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Socket> roomRequestQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Socket> gameRequestQueue = new LinkedBlockingQueue<>();

        new Thread(new LoginRequestHandler(loginQueue)).start();
        new Thread(new RoomRequestHandler(roomRequestQueue)).start();
        new Thread(new GameRequestHandler(gameRequestQueue)).start();

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started on port: " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                RequestType requestType = determineRequestType(clientSocket);

                switch (requestType) {
                    case LOGIN -> loginQueue.add(clientSocket);
                    case ROOMREQUEST -> roomRequestQueue.add(clientSocket);
                    case GAMEREQUEST -> gameRequestQueue.add(clientSocket);
                    default -> {
                        System.out.println("Unknown request type. Closing connection.");
                        clientSocket.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RequestType determineRequestType(Socket socket) {
        // Simulate determining request type
        // In a real scenario, parse client input to decide request type
        return RequestType.LOGIN; // Example: Defaulting to LOGIN for demonstration
    }
}

