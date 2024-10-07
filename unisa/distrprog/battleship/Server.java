package unisa.distrprog.battleship;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// D. Knuth, "Pre-optimization is the root of all devil"

public class Server extends Thread {
    public static final int PORT = 33333;
    public static List<Table> tables = new ArrayList<>();

    public Server() {}

    @Override
    public void run() {
        System.out.println("unisa.distrprog.battleship.Server started");
    }

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                boolean foundTable = false;
                // check for a free table
                Socket socket = serverSocket.accept();
                for (Table table: tables) {
                    if (table.clients.isEmpty()) {
                        table.addClient(socket);
                        table.startGame();
                        foundTable = true;
                        break;
                    }
                }

                // otherwise we create a new one
                if (!foundTable) {
                    Table table = new Table();
                    table.addClient(socket);
                    tables.add(table);
                }
            }
        }
    }
}