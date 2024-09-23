package unisa.distrprog.socket;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class StringsStoreServer {
    private static final int PORT = 33333;
    private static final String LIMIT_REACHED = "** LIMIT REACHED **";
    private static final String RESET_COMMAND = "RESET";
    private static final String RESET_RESPONSE = "OK";
    private static final int MAX_STRINGS = 5;

    private Queue<String> storage = new LinkedList<>();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    BufferedReader io = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String message = io.readLine().strip();
                    System.out.println("[Received] " + message);

                    String response = this.handleMessage(message);
                    out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] unisa.distrprog.socket.Server error");
            e.printStackTrace();
        }
    }

    private String handleMessage(String message) {
        if (RESET_COMMAND.equals(message)) {
            this.storage.clear();
            return RESET_RESPONSE;
        }

        if (this.storage.size() == MAX_STRINGS)
            return LIMIT_REACHED;

        this.storage.add(message);
        return String.join("\n", this.storage);  // we python here
    }

    public static void main(String[] args) {
        StringsStoreServer server = new StringsStoreServer();
        server.start();
    }
}
