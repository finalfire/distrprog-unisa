package unisa.distrprog.udp.exercise;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerDaytime {
    public static final int PORT = 33333;
    public static final int MAX_BUFF_LEN = 1024;

    public static String currentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(PORT);
        // TODO: make it multithreaded
        while (true) {
            DatagramPacket packet = new DatagramPacket(new byte[0], 0);
            socket.receive(packet);

            byte[] now = currentDateTime().getBytes();
            DatagramPacket response = new DatagramPacket(now, now.length, packet.getAddress(), packet.getPort());
            socket.send(response);
        }
    }
}
