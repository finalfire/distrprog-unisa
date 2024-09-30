package unisa.distrprog.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        byte[] outBuffer = new byte[1024];
        byte[] inBuffer = new byte[10];

        BufferedReader readerStdin = new BufferedReader(new InputStreamReader(System.in));
        String message = readerStdin.readLine();
        outBuffer = message.getBytes();
        System.out.println("[Client] Buff length: " + outBuffer.length);

        InetAddress hostIP = InetAddress.getByName("localhost");
        int hostPort = 33333;

        // We create a datagramsocket and we sent a packet through it
        DatagramSocket clientSocket = new DatagramSocket();
        DatagramPacket outPacket = new DatagramPacket(new byte[77777], 77777, hostIP, hostPort);
        clientSocket.send(outPacket);

        // we now receive a packet through the same socket
        DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        clientSocket.receive(inPacket);
        String receivedMessage = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println("Received: " + receivedMessage);
    }
}
