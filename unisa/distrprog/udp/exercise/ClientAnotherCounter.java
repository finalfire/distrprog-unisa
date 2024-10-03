package unisa.distrprog.udp.exercise;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientAnotherCounter {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getByName(Util.unicastHostname), Util.PORT);
        OutputStream os = socket.getOutputStream();
        os.write(new byte[0]);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int sessions = Integer.parseInt(in.readLine());
        System.out.println(sessions);
    }
}
