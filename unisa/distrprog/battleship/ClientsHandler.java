package unisa.distrprog.battleship;

import java.io.*;
import java.net.Socket;

public class ClientsHandler {
    public Socket clientA;
    public Socket clientB;
    public String clientNameA;
    public String clientNameB;

    public ClientsHandler() {
        this.clientA = new Socket();
        this.clientB = new Socket();
    }

    public boolean isEmpty() {
        return clientA == null || clientB == null;
    }

    public void add(Socket client) {
        if (!clientA.isBound()) {
            this.clientA = client;
            // ask for the client name
            new Thread(() -> {
                try {
                    BufferedReader buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    this.clientNameA = buff.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            return;
        }
        // ask for the client name
        this.clientB = client;
        // ask for the client name
        new Thread(() -> {
            try {
                BufferedReader buff = new BufferedReader(new InputStreamReader(client.getInputStream()));
                this.clientNameB = buff.readLine();
                if (this.clientNameA.equals(this.clientNameB)) {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println("Sorry, the username is already being used!");
                    this.clientB.close();
                    this.clientB = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
