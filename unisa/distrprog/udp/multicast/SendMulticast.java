package unisa.distrprog.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendMulticast {
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket();
        byte[] message = "This is a multicast message!".getBytes();

        // 224.0.0.1 is a reference for "all hosts on this subnet"
        InetAddress address = InetAddress.getByName("224.0.0.1");
        DatagramPacket packet = new DatagramPacket(message, message.length, address, 7777);
        socket.send(packet);
        socket.close();
    }
}
