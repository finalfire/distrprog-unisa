package unisa.distrprog.socket;

import java.io.*;
import java.net.*;

public class Server {
    public static final int PORT = 7777;

    public static void main(String[] args) throws IOException {
        // note: no host!
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("[unisa.distrprog.socket.Server] In attesa di una connessione...");
        Socket sock = serverSocket.accept();

        InputStreamReader isr = new InputStreamReader(sock.getInputStream(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String message = br.readLine();
        System.out.println("[unisa.distrprog.socket.Server] Il client ha inviato " + message);

        OutputStreamWriter os = new OutputStreamWriter(sock.getOutputStream(), "UTF-8");
        PrintWriter prw = new PrintWriter(os);
        prw.println("Hello, unisa.distrprog.socket.Client!");
        prw.flush();

        sock.close();
        serverSocket.close();
    }
}
