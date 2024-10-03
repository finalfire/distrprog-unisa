package unisa.distrprog.udp.exercise;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientCounter {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();

        Socket socket = new Socket(InetAddress.getByName(Util.unicastHostname), Util.PORT);
        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),
                true);
        out.println(value);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int sum = Integer.parseInt(in.readLine());
        System.out.println(sum);
    }
}
