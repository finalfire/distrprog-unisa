package unisa.distrprog.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(33333);
        byte[] inBuff = new byte[10];

        while (true) {
            // prepare a packet to receive from the socket
            DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
            socket.receive(inPacket);

            System.out.println("[Server] Buffer:");
            for (int i = 0; i < inBuff.length; i++) {
                System.out.print(inBuff[i]);
                System.out.print(" ");
            }
            System.out.println();

            // parse and use the content
            String message = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println("[Server] parsed message: " + message);
            String response = message.toUpperCase();
            byte[] responseData = response.getBytes();

            // we now send the response to the client; to do so,
            // we need the inet and port
            InetAddress address = inPacket.getAddress();
            int port = inPacket.getPort();
            DatagramPacket outPacket = new DatagramPacket(responseData, responseData.length, address, port);
            socket.send(outPacket);
        }
    }
}
