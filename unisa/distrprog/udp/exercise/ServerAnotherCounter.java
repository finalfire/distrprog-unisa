package unisa.distrprog.udp.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAnotherCounter implements Runnable {
    private volatile static int sessions = 0;
    private Socket socket;

    public ServerAnotherCounter(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            this.increase();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(sessions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void increase() {
        sessions += 1;
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(Util.PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread t = new Thread(new ServerAnotherCounter(socket));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
