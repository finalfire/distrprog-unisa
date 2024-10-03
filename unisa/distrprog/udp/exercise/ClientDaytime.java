package unisa.distrprog.udp.exercise;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientDaytime {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(
                new byte[0], 0, InetAddress.getByName("localhost"), ServerDaytime.PORT);
        socket.send(packet);

        DatagramPacket response = new DatagramPacket(new byte[ServerDaytime.MAX_BUFF_LEN], ServerDaytime.MAX_BUFF_LEN);
        socket.receive(response);
        System.out.println(new String(response.getData(), 0, response.getLength()));
    }
}
