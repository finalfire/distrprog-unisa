package unisa.distrprog.udp.exercise;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientReadLine {
    public static void main(String[] args) throws IOException {
        String request = "myfile.txt 4";
        byte[] buffer = new byte[1024];

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(
                request.getBytes(), request.getBytes().length,
                InetAddress.getByName("localhost"), ServerReadLine.PORT);
        socket.send(packet);

        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String response = new String(packet.getData(), 0, packet.getLength());
        System.out.println(response);
    }
}
