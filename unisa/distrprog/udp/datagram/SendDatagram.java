package unisa.distrprog.udp.datagram;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendDatagram {
    private static final int PORT = 33333;

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        byte[] message = "Hello World".getBytes();

        // defining address and the packet
        InetAddress address = InetAddress.getByName(host);
        DatagramPacket packet = new DatagramPacket(message, message.length, address, PORT);

        // creating a socket and send the packet through it
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }
}
