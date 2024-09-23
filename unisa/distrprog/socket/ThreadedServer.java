package unisa.distrprog.socket;

import java.io.*;
import java.net.*;

public class ThreadedServer implements Runnable {
    public static final int PORT = 7777;
    private final Socket socket;

    public ThreadedServer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader brd = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream(), "UTF-8")
            );
            String message = brd.readLine();
            System.out.println("[unisa.distrprog.socket.Client sent] " + message);

            PrintWriter prw = new PrintWriter(
                    new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8")
            );
            prw.println("Hello, unisa.distrprog.socket.Client!");
            prw.flush();
        } catch (IOException e) {
            System.err.println("I/O Exception in reading/writing the socket :(");
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                System.err.println("I/O Exception in closing the socket :(");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                ThreadedServer server = new ThreadedServer(socket);
                Thread thread = new Thread(server);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("I/O Exception starting the server :(");
        }
    }
}
