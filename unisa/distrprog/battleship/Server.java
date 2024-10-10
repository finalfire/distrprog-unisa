package unisa.distrprog.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;

public class Server extends Thread {
    public static final int PORT = 33333;

    // maintains the whole set of current clients connected to the server
    public static final HashSet<String> currentClients = new HashSet<>();
    public static final LinkedList<Table> tables = new LinkedList<>();

    @Override
    public void run() {
        try (ServerSocket socket = new ServerSocket(PORT)) {
            while (true) {
                // accepts a connection and expects for a username
                Socket candidateClient = socket.accept();
                String username = Server.getContent(candidateClient);
                System.out.println("Received user: " + username);

                // we refuse the connection if the username already exists
                if (currentClients.contains(username)) {
                    Server.writeContent(candidateClient, "This username already exists, sorry!");
                    candidateClient.close();
                    continue;
                }

                // we check for a free table, and we add a unisa.distrprog.battleship.Player to it
                // if the table is ready, we also start it
                boolean gotTable = false;
                for (Table table : tables) {
                    System.out.println("Scanning table " + table.getTableID());
                    if (table.isFree()) {
                        gotTable = true;
                        table.addPlayer(new Player(username, candidateClient, table));
                        System.out.println(username + " has been added to table " + table.getTableID());
                        Server.writeContent(candidateClient, "You have been added to table " + table.getTableID());
                        if (table.isReady()) {
                            Thread t = new Thread(table);
                            t.start();
                        }
                        break;
                    }
                }

                // if no tables are available, we start a new table
                if (!gotTable) {
                    tables.add(new Table(tables.size()));
                    tables.getLast().addPlayer(new Player(username, candidateClient, tables.getLast()));
                    System.out.println(username + " has been added to a new table!");
                    Server.writeContent(candidateClient, "You have been added to a new table!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getContent(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }

    private static void writeContent(Socket socket, String content) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(content);
    }

    public static void main(String[] args) {
        new Server().start();
    }
}