package unisa.distrprog.socket;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class StringsStoreClient {
    private static final int PORT = 33333;

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        Socket socket = new Socket(host, PORT);

        // Open a connection to the server,
        // read a message from stdin and write it in the socket
        OutputStream oss = socket.getOutputStream();
        Writer wr = new OutputStreamWriter(oss);
        PrintWriter pwr = new PrintWriter(wr, true);

        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();
        pwr.println(msg);

        // Read the connection from the server
        InputStreamReader iss = new InputStreamReader(socket.getInputStream(), "UTF-8");
        BufferedReader br = new BufferedReader(iss);
        System.out.println("[unisa.distrprog.socket.Server response]:");
        br.lines().forEach(System.out::println);

        // Bye!
        socket.close();
    }
}
