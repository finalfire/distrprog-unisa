package unisa.distrprog.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReceiveMulticast {
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(7777);
        InetAddress address = InetAddress.getByName("224.0.0.1");
        socket.joinGroup(address);

        byte[] message = new byte[1024];
        DatagramPacket packet = new DatagramPacket(message, message.length, address, 7777);
        socket.receive(packet);
        System.out.println(new String(packet.getData(), 0, packet.getLength()));
    }
}
