package unisa.distrprog.udp.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ClientLastTwoMessages {
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getByName("localhost");
        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(5000);
            while (true) {
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                byte[] message = stdin.readLine().getBytes();

                DatagramPacket packet = new DatagramPacket(message, message.length, address, ServerLastTwoMessages.PORT);
                socket.send(packet);

                try {
                    DatagramPacket received = new DatagramPacket(new byte[ServerLastTwoMessages.PORT], ServerLastTwoMessages.PORT);
                    socket.receive(received);
                    String receivedMessage = new String(received.getData(), 0, received.getLength());
                    System.out.println("Received: " + receivedMessage);
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout occured");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
