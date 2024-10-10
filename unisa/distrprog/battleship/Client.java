package unisa.distrprog.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private HashSet<String> shipsToPlace;
    private final Scanner stdin;

    public Client() throws IOException {
        this.socket = new Socket("localhost", Server.PORT);
        this.shipsToPlace = new HashSet<>(ShipsCatalog.ships.keySet());
        this.stdin = new Scanner(System.in);
    }

    public String readFrom() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return in.readLine();
    }

    public void writeTo(String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
    }

    public String writeAndRead(String message) throws IOException {
        this.writeTo(message);
        return this.readFrom();
    }

    public void placeShips() throws IOException {
        while (!this.shipsToPlace.isEmpty()) {
            System.out.println("Provide the ship to be placed and its coordinate: SHIP, X, Y, D");
            System.out.println("Remaining ships to be placed: " + String.join(", ", this.shipsToPlace));

            String message = this.stdin.nextLine();
            String ship = message.split(", ")[0];
            String response = this.writeAndRead(message);
            System.out.println("[from server] " + response);
            if (response.startsWith("Ship correctly placed"))
                this.shipsToPlace.remove(ship);
        }
    }

    public void play() throws IOException {
        String response = this.readFrom();
        String delim = ", ";
        boolean gameOver = false;

        if (response.equals(Message.GAME_SESSION)) {
            System.out.println(Message.GAME_SESSION);
            while (!gameOver) {
                // the game starts here
                // the client can now send the guessed coordinate in the form X, Y
                String playerAction = this.stdin.nextLine();

                if (playerAction.equals(Message.GAME_BOARD)) {
                    this.writeTo(Message.GAME_BOARD);

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    for (int i = 0; i < Board.HEIGHT + 1; i++) {
                        String line = in.readLine();

                        if (line.equals(Message.GAME_LOST)) {
                            System.out.println("You lost, sorry...");
                            break;
                        }

                        System.out.println(line);
                    }

                    continue;
                }

                Coordinate c = Coordinate.fromString(playerAction, delim);
                String message = this.writeAndRead(c.toString(delim));

                switch (message) {
                    case Message.GAME_MISS -> System.out.println("This was a miss :(");
                    case Message.GAME_HIT -> System.out.println("This was a hit :)");
                    case Message.GAME_SHIP -> System.out.println("You sunk a ship :D");
                    case Message.GAME_WON -> { System.out.println("You won, congrats!!!"); gameOver = true; }
                    case Message.GAME_LOST -> { System.out.println("You lost, sorry..."); gameOver = true; }
                    default -> gameOver = true;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();

        // Send username
        String username = args[0];
        System.out.println(c.writeAndRead(username));

        // The client now waits for the placing phase
        String message = c.readFrom();
        if (message.equals(Message.PLACING_PHASE)) {
            System.out.println("Ready to place ship.");
            c.placeShips();
            c.play();
        }
    }
}
