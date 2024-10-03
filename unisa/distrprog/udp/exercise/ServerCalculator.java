package unisa.distrprog.udp.exercise;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class ServerCalculator {
    public static final int PORT = 33333;
    public static final int MAX_BUFF_LENGTH = 1024;

    public static byte[] parseCommand(String command) {
        String[] parts = command.split("\\s+");

        if (parts.length < 2 || parts.length > 3) {
            throw new IllegalArgumentException("Invalid command format");
        }

        String operation = parts[0].toLowerCase();
        double a = Double.parseDouble(parts[1]);
        double b = (parts.length == 3) ? Double.parseDouble(parts[2]) : 0;

        double result = switch (operation) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new IllegalArgumentException("Nope!");
                yield a / b;
            }
            case "min" -> Math.min(a, b);
            case "max" -> Math.max(a, b);
            case "avg" -> (a + b) / 2;
            case "pow" -> Math.pow(a, b);
            case "sqrt" -> {
                if (parts.length != 2) throw new IllegalArgumentException("Only one operand");
                if (a < 0) throw new IllegalArgumentException("No negative numbers");
                yield Math.sqrt(a);
            }
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };

        return ByteBuffer.allocate(8).putDouble(result).array();
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[MAX_BUFF_LENGTH];

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            byte[] result = parseCommand(new String(packet.getData(), 0, packet.getLength()));
            DatagramPacket response = new DatagramPacket(result, result.length, packet.getAddress(), packet.getPort());
            socket.send(response);
        }
    }
}
