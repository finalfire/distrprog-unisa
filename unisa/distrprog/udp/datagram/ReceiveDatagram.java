package unisa.distrprog.udp.datagram;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveDatagram {
    private static final int PORT = 33333;

    public static void main(String[] args) throws IOException {
        int bufferLength = 1024;
        byte[] buffer = new byte[bufferLength];
        DatagramPacket packet = new DatagramPacket(buffer, bufferLength);

        DatagramSocket socket = new DatagramSocket(PORT);
        socket.receive(packet);
        System.out.println("Received " + packet.getLength() + " bytes from " + packet.getAddress());

        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("The message is: " + message);
    }
}
