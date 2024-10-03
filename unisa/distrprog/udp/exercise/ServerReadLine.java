package unisa.distrprog.udp.exercise;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

record Request(String filename, int lineNo) {}

public class ServerReadLine {
    public static int PORT = 33333;
    public static int MAX_BUFF_LENGTH = 1024;

    public static Request parse(String message) throws Exception {
        String[] parts = message.split(" ");
        if (parts.length > 2)
            throw new Exception("Invalid format!");
        return new Request(parts[0], Integer.parseInt(parts[1]));
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[MAX_BUFF_LENGTH];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            try {
                Request req = ServerReadLine.parse(message);
                byte[] response = Files.lines(Paths.get(req.filename()))
                        .skip(req.lineNo())
                        .findFirst()
                        .orElseGet(() -> "500: LINE ERROR")
                        .getBytes();

                socket.send(new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
