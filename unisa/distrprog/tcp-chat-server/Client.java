import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;

public class Client {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 7777;

    private final Socket socket;
    private BufferedReader socketIn;
    private BufferedReader stdIn;
    private PrintWriter socketOut;

    public Client() throws IOException {
        this.socket = new Socket("localhost", 7777);
        this.start();
    }

    private void start() {
        try {
            this.socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.stdIn = new BufferedReader(new InputStreamReader(System.in));
            this.socketOut = new PrintWriter(this.socket.getOutputStream(), true);

            Thread inputThread = new Thread(this::handleInput);
            Thread outputThread = new Thread(this::handleOutput);

            inputThread.start();
            outputThread.start();

            try {
                inputThread.join();
                outputThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleInput() {
        try {
            while (true) {
                String message = this.socketIn.readLine();
                if (message == null)
                    return;
                System.out.println("[from Server] " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleOutput() {
        System.out.println("Welcome!");

        try {
            while (true) {
                String userMessage = this.stdIn.readLine();
                if (userMessage == null)
                    return;
                this.socketOut.println(userMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.stdIn.close();
                this.socketIn.close();
                this.socketOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        // The client should receive a message asynchronously, therefore we need to use threads
        // We cannot wrap everything in a while (true) otherwise we would be constrained
        // by the order of the operations withing the while block
        new Client();
    }
}
