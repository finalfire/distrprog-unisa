package unisa.distrprog.socket;

import java.io.*;
import java.net.*;

public class Client {
    public static final int PORT = 7777;

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";  // localhost
        Socket socket = new Socket(host, PORT);

        // Send to the server
        OutputStream out = socket.getOutputStream();
        Writer wr = new OutputStreamWriter(out, "UTF-8");
        PrintWriter prw = new PrintWriter(wr, true);
        prw.println("Hello, unisa.distrprog.socket.Server, this is the second client...");
        prw.flush();

        // Read the response from the server
        InputStream is = socket.getInputStream();
        Reader rdr = new InputStreamReader(is, "UTF-8");
        BufferedReader brdr = new BufferedReader(rdr);
        String response = brdr.readLine();
        System.out.println("[Response] " + response);

        socket.close();
    }
}
