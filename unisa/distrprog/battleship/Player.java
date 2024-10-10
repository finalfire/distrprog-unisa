package unisa.distrprog.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {
    private final Table table;
    private final Board board;
    private int shipsToPlace = ShipsCatalog.ships.size();

    public String username;
    public Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;


    public Player(String username, Socket clientSocket, Table table) {
        this.username = username;
        this.clientSocket = clientSocket;
        this.table = table;

        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.board = new Board();
    }

    @Override
    public void run() {
        System.out.println("unisa.distrprog.battleship.Player " + username + " thread spawned!");
        try {
            this.startPlacing();
            this.table.getBarrier().await();

            System.out.println("Ships placing terminated. Now starting game session.");
            this.startGameSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startPlacing() {
        // this implements the placing ships phase
        this.sendMessage(Message.PLACING_PHASE);
        while (this.shipsToPlace > 0) {
            boolean isPlaced = this.parseAndPlaceShip(this.receiveMessage());
            if (isPlaced) {
                this.sendMessage("Ship correctly placed, you have " + --this.shipsToPlace + " ships to place.");
                // System.out.println("unisa.distrprog.battleship.Board of " + this.username);
                // System.out.println(this.board);
            } else
                this.sendMessage("You cannot place your ship at this location. Please try with a different one");
        }
    }

    public void startGameSession() {
        // this implements the game session
        // at each request from the client, we check if the game has terminated from the other thread
        // the thread that terminates the game set the new value for the atomic boolean
        String message;
        boolean youWin = false;
        this.sendMessage(Message.GAME_SESSION);

        while (!this.table.getGameOver().get()) {
            //System.out.println("I'm " + this.username + " and game state is " + this.table.getGameOver().get());
            message = this.receiveMessage();

            // this is needed in case the other thread already closed the game by winning
            if (this.table.getGameOver().get())
                break;

            // manage the board request
            if (message.equals(Message.GAME_BOARD)) {
                this.sendMessage(this.board.toString());
                continue;
            }

            // here goes the logic of the game (check and communicate if the coord was a hit/miss/ship)
            Board opponentBoard = this.table.getOpponent(this).getBoard();
            Board.AttackResult result = opponentBoard.checkAttack(Coordinate.fromString(message, ", "));

            // send the result
            switch (result.result()) {
                case Board.AttackResult.Result.HIT -> this.sendMessage(Message.GAME_HIT);
                case Board.AttackResult.Result.SHIP -> {
                    // the game state can be over only after the final ship has been destroyed
                    if (opponentBoard.isOver()) {
                        this.table.getGameOver().set(true);
                        youWin = true;
                        break;
                    }

                    this.sendMessage(Message.GAME_SHIP);
                }
                case Board.AttackResult.Result.MISS -> this.sendMessage(Message.GAME_MISS);
                default -> this.sendMessage(Message.GAME_INVALID_COORDINATE);
            }
        }

        // At this point, the game is over, check the winning player
        if (youWin)
            this.sendMessage(Message.GAME_WON);
        else
            this.sendMessage(Message.GAME_LOST);
    }

    // Parses a string in the form "SHIP, X, Y, D"
    private boolean parseAndPlaceShip(String message) {
        String[] elements = message.split(", ");
        Coordinate coordinate = new Coordinate(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
        Direction dir = elements[3].equals("V") ? Direction.VERTICAL : Direction.HORIZONTAL;
        return this.board.placeShip(elements[0], coordinate, dir);
    }

    public Board getBoard() {
        return this.board;
    }

    public void sendMessage(String message) {
        this.out.println(message);
    }

    public String receiveMessage() {
        try {
            return this.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Message.GENERIC_ERROR;
    }
}
