package unisa.distrprog.socket;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ModernStringsStoreServer {
    private static final int PORT = 33333;
    private static final int THREAD_POOL_SIZE = 100;
    private static final String EXIT_MESSAGE = "EXIT";

    private Queue<String> storage = new ConcurrentLinkedQueue<>();
    private final ExecutorService threadPool;
    private volatile boolean running;

    public ModernStringsStoreServer() {
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.running = true;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (running) {
                try (Socket clientSocket = serverSocket.accept()) {
                    threadPool.submit(() -> handleClientConnection(clientSocket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // this bit gracefully shutdowns the server, and all the
            // threads currently waiting
            running = false;
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                threadPool.shutdownNow();
            }
        }
    }

    public void handleClientConnection(Socket clientSocket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("[Received] " + message);
                if (EXIT_MESSAGE.equals(message))
                    break;
                this.storage.add(message);
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ModernStringsStoreServer server = new ModernStringsStoreServer();
        // This attaches a hook (e.g., a distinct thread) that executes when the server
        // gets shutdown; it needs a method of the class (e.g., shutdown) to be executed
        // in the shutdown process.
        // Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.start();
    }
}
