package unisa.distrprog.udp.exercise;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class ClientCalculator {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");

        while (true) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            byte[] command = in.readLine().getBytes();
            DatagramPacket packet = new DatagramPacket(command, command.length, address, ServerCalculator.PORT);
            socket.send(packet);

            byte[] response = new byte[ServerCalculator.MAX_BUFF_LENGTH];
            packet = new DatagramPacket(response, response.length);
            socket.receive(packet);
            double result = ByteBuffer.wrap(packet.getData()).getDouble();
            System.out.println(result);
        }
    }
}
