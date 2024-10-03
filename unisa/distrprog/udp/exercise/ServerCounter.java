package unisa.distrprog.udp.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCounter implements Runnable {
    private volatile static int sum = 0;
    private Socket socket;

    public ServerCounter(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int value = Integer.parseInt(in.readLine());  // the protocol is fixed
            this.increase(value);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void increase(int value) {
        sum += value;
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(Util.PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread t = new Thread(new ServerCounter(socket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
