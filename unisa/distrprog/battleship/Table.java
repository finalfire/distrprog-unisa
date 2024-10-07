package unisa.distrprog.battleship;

import java.net.Socket;

public class Table {
    public ClientsHandler clients;

    public void addClient(Socket clientSocket) {
        this.clients.add(clientSocket);
    }
}
