package unisa.distrprog.battleship;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Table implements Runnable {
    private final int tableID;
    private Player player1;
    private Player player2;

    private final CyclicBarrier barrier;
    private final AtomicBoolean gameOver;

    // creates a table
    public Table(int tableID) {
        this.tableID = tableID;
        this.barrier = new CyclicBarrier(2);
        this.gameOver = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        System.out.println("unisa.distrprog.battleship.Table " + this.tableID + " started...");
        this.player1.start();
        this.player2.start();
        try {
            this.player1.join();
            this.player2.join();
            System.out.println("The game session at " + this.tableID + " has ended.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a player to the table
    public void addPlayer(Player player) {
        if (this.player1 == null) {
            this.player1 = player;
            return;
        }
        this.player2 = player;
    }

    public boolean isFree() {
        return this.player1 == null || this.player2 == null;
    }

    public boolean isReady() {
        return !this.isFree();
    }

    public Player getOpponent(Player player) {
        if (this.player1 == player)
            return this.player2;
        return this.player1;
    }

    public int getTableID() {
        return tableID;
    }

    public CyclicBarrier getBarrier() {
        return this.barrier;
    }

    public AtomicBoolean getGameOver() {
        return this.gameOver;
    }
}
