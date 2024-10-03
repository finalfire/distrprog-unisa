package unisa.distrprog.udp.exercise;

import java.io.IOException;
import java.net.*;

public class MulticastClientReadLine {
    private static final int MAX_RESPONSES = 20;

    public static void main(String[] args) throws IOException {
        InetAddress groupAddress = InetAddress.getByName(MulticastServerReadline.GROUPNAME);

        SocketAddress sockaddr = new InetSocketAddress(MulticastServerReadline.GROUPNAME, MulticastServerReadline.PORT);
        MulticastSocket socket = new MulticastSocket(MulticastServerReadline.PORT);
        socket.joinGroup(sockaddr, NetworkInterface.getByName("en0"));
        socket.joinGroup(sockaddr, NetworkInterface.getByName("en1"));

        for (int i = 0; i < MAX_RESPONSES; i++) {
            DatagramPacket packet = new DatagramPacket(
                    new byte[MulticastServerReadline.MAX_BUFF_LENGTH],
                    MulticastServerReadline.MAX_BUFF_LENGTH);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received in multicast:");
            System.out.println(message);
        }
    }
}
