import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;

public class ChatServer {
    private static final int PORT = 7777;
    private static final Map<String, PrintWriter> connectedClients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // 1. the server waits for a new connection,
        // 2. the new connection is handled by spawning a thread being an instance a ClientHandler class
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket, connectedClients));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
