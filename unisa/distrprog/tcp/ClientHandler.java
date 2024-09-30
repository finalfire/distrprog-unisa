import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private PrintWriter out;
    private String currentClient;
    private final Map<String,PrintWriter> clients;

    private static final String UNRECOGNIZED_MESSAGE = "ERROR: unrecognized message";

    public ClientHandler(Socket socket, Map<String,PrintWriter> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        // Lifecycle of the ClientHandler:
        // (1) manages the input and output streams of the socket,
        // (2) accepts a message that handles via the handleMessage
        // (3) if handleMessage returns false, something went wrong (e.g., the client closed the connection)
        //     and we close the socket; TODO: design a better way to error handling!
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String message = in.readLine();
                if (!this.handleMessage(message))
                    return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean handleMessage(String message) {
        // This could happen for a variety of reason
        if (message == null)
            return false;

        System.out.println("[Server] Received: " + message);

        // We follow the pre-decided syntax
        String[] elements = message.split(" ");

        // If not enough elements, we notify the client
        if (elements.length < 2) {
            this.out.println(UNRECOGNIZED_MESSAGE);
            return true;
        }

        // We deal with the message
        return this.handleCommand(elements);
    }

    private boolean handleCommand(String[] elements) {
        return switch (elements[0]) {
            case "LOGIN" -> this.handleLogin(elements[1]);
            case "LOGOUT" -> this.handleLogout(elements[1]);
            case "1TO1" -> this.handleSingleMessage(elements[1], this.retrieveTextMessage(elements, 2));
            case "BROADCAST" -> this.handleBroadcast(this.retrieveTextMessage(elements, 1));
            default -> false;
        };
    }

    private String retrieveTextMessage(String[] elements, int start) {
        return String.join(" ", Arrays.copyOfRange(elements, start, elements.length));
    }

    private boolean handleLogin(String client) {
        if (this.clients.containsKey(client)) {
            this.clients.get(client).println("You're already logged in!");
            return true;
        }

        this.currentClient = client;
        this.clients.put(this.currentClient, this.out);
        this.clients.get(this.currentClient).println("You're logged in!");
        return true;
    }

    private boolean handleLogout(String client) {
        if (!this.clients.containsKey(client)) {
            this.out.println("You're not logged in!");
            return true;
        }

        this.clients.get(client).println("Bye!");
        this.clients.remove(client);
        return false;
    }

    private boolean handleSingleMessage(String receiver, String message) {
        if (!this.clients.containsKey(this.currentClient) || !this.clients.containsKey(receiver)) {
            this.clients.get(this.currentClient).println("Clients not valid.");
            return true;
        }

        this.clients.get(receiver).println(this.currentClient + ": " + message);
        return true;
    }

    private boolean handleBroadcast(String message) {
        if (!this.clients.containsKey(this.currentClient)) {
            this.clients.get(this.currentClient).println("Client not valid.");
            return true;
        }

        String broadcastMessage = "[B] " + this.currentClient + ": " + message;
        this.clients.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(this.currentClient))
                .forEach(entry -> entry.getValue().println(broadcastMessage));
        return true;
    }
}
