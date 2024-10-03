package unisa.distrprog.udp.exercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MulticastServerReadline extends Thread {
    public static final int PORT = 33333;
    public static final int MAX_BUFF_LENGTH = 1024;
    public static final String GROUPNAME = "224.0.0.1";  // all hosts on this subnet
    private static final int DELAY = 2000;

    @Override
    public void run() {
        int lineNo = 0;
        try {
            List<String> lines = Files.lines(Paths.get("myfile.txt")).toList();

            try (MulticastSocket socket = new MulticastSocket()) {
                InetAddress groupAddress = InetAddress.getByName(GROUPNAME);
                while (true) {
                    String message = lines.get(lineNo);
                    DatagramPacket packet = new DatagramPacket(
                            message.getBytes(), message.getBytes().length, groupAddress, PORT);
                    socket.send(packet);
                    // start from the first line if we reach last one
                    lineNo = lineNo < lines.size() - 1 ? ++lineNo : 0;

                    this.sleep(DELAY);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readNextLine(int lineNo) {
        return "Hello!";
    }

    public static void main(String[] args) {
        new MulticastServerReadline().start();
    }
}
