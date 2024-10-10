package unisa.distrprog.battleship;

import java.util.ArrayList;

public class Board {
    public record AttackResult(Result result) {
        public enum Result {HIT, MISS, SHIP, INVALID}
    }
    record PlacedShip(int id, String ship, Coordinate coordinate, Direction direction) {}

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private final ArrayList<PlacedShip> placedShips;
    private final int[][] map;

    public Board() {
        this.map = new int[WIDTH][HEIGHT];
        this.placedShips = new ArrayList<>();
    }

    // this places a ship on the table
    public boolean placeShip(String ship, Coordinate c, Direction d) {
        if (!this.isValidShip(ship))
            return false;

        int w = ShipsCatalog.ships.get(ship).weight();
        if (!this.isValidPosition(w, c, d))
            return false;

        // we use 0 to denote ocean cell
        int nextShipID = this.placedShips.size() + 1;
        this.placedShips.add(new PlacedShip(nextShipID, ship, c, d));
        for (int i = 0; i < w; i++)
            this.map[d == Direction.HORIZONTAL ? c.x() : c.x() + i]
                    [d == Direction.HORIZONTAL ? c.y() + i : c.y()] = nextShipID;

        return true;
    }

    public AttackResult checkAttack(Coordinate c) {
        if (!this.isValidCoordinate(c) || this.map[c.x()][c.y()] == 'X')
            return new AttackResult(AttackResult.Result.INVALID);

        if (this.map[c.x()][c.y()] == 0)
            return new AttackResult(AttackResult.Result.MISS);

        // c hits a ship, check if we got the whole ship or just a part of it
        // we sign the hit with a X on the map
        int shipID = this.map[c.x()][c.y()] - 1;
        this.map[c.x()][c.y()] = 'X';

        // shipID is the i-th ship placed on the board, w is its length
        PlacedShip ship = this.placedShips.get(shipID);
        int cx = ship.coordinate.x();
        int cy = ship.coordinate.y();
        int w = ShipsCatalog.ships.get(ship.ship()).weight();

        boolean wrecked = true;
        for (int i = 0; i < w && wrecked; i++) {
            int x = ship.direction == Direction.HORIZONTAL ? cx : cx + i;
            int y = ship.direction == Direction.HORIZONTAL ? cy + i : cy;
            System.out.println("Checking (" + x + "," + y + ") = " + this.map[x][y] + ", for ship " + shipID);
            if (this.map[x][y] != 'X')
                wrecked = false;
        }
        // if all cells are X, then the ship is totally wrecked
        if (wrecked)
            return new AttackResult(AttackResult.Result.SHIP);

        // otherwise, the attack is a hit
        return new AttackResult(AttackResult.Result.HIT);
    }

    public boolean isOver() {
        // the game is over iff the map contains only X and 0
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                if (this.map[i][j] != 'X' && this.map[i][j] != 0)
                    return false;
        return true;
    }

    private boolean isValidShip(String ship) {
        return ShipsCatalog.ships.containsKey(ship);
    }

    private boolean isValidCoordinate(Coordinate c) {
        return (c.x() >= 0 && c.x() < HEIGHT) && (c.y() >= 0 && c.y() < WIDTH);
    }

    private boolean isValidPosition(int w, Coordinate c, Direction d) {
        if (!this.isValidCoordinate(c))
            return false;

        if (d == Direction.HORIZONTAL)
            if (c.y() + w < WIDTH)
                return true;
        return c.x() + w < HEIGHT;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("   ");
        for (int j = 0; j < HEIGHT; j++) {
            result.append(j).append(" ");
        }
        result.append("\n");

        for (int i = 0; i < WIDTH; i++) {
            result.append(String.format("%2d", i)).append(" ");
            for (int j = 0; j < HEIGHT; j++) {
                if (this.map[i][j] == 0)
                    result.append("· ");
                else if (this.map[i][j] == 'X')
                    result.append("X ");
                else
                    result.append(ShipsCatalog.ships.get(this.placedShips.get(this.map[i][j] - 1).ship).symbol())
                            .append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
