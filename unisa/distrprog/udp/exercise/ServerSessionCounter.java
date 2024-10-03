package unisa.distrprog.udp.exercise;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerSessionCounter implements Runnable {
    public static final int PORT = 33333;
    private volatile static ConcurrentHashMap<String,Integer> sessionCounter = new ConcurrentHashMap<>();
    private DatagramSocket socket;
    private DatagramPacket packet;

    public ServerSessionCounter(DatagramSocket socket, DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            String hostname = this.packet.getAddress().getHostAddress();

            if (sessionCounter.containsKey(hostname))
                sessionCounter.put(hostname, sessionCounter.get(hostname) + 1);
            else
                sessionCounter.put(hostname, 1);

            this.packet = new DatagramPacket(new byte[1024], 1024,
                    this.packet.getAddress(), this.packet.getPort());
            this.socket.send(this.packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[0], 0);
                try {
                    socket.receive(packet);
                    Thread t = new Thread(new ServerSessionCounter(socket, packet));
                    t.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
