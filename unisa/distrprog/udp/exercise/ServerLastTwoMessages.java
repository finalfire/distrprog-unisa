package unisa.distrprog.udp.exercise;

import unisa.distrprog.udp.Server;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

public class ServerLastTwoMessages extends Thread {
    public static final int PORT = 33333;
    public static final int MAX_BUFF_LENGTH = 1024;

    private final Queue<String> messages;

    public ServerLastTwoMessages() {
        this.messages = new LinkedList<>();
    }

    @Override
    public void run() {
        byte[] buffer = new byte[MAX_BUFF_LENGTH];

        while (true) {
            try (DatagramSocket socket = new DatagramSocket(PORT)) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);     // overwrite the previous content on buffer, so it is safe
                String received = new String(packet.getData(), 0, packet.getLength());

                System.out.println(String.join("\n", this.messages));
                byte[] message = String.join("\n", this.messages).getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                        message, message.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);

                if (this.messages.size() == 2)
                    this.messages.poll();
                this.messages.add(received);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ServerLastTwoMessages().start();
    }
}
