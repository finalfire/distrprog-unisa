package unisa.distrprog.battleship;

import java.util.HashMap;

public class ShipsCatalog {
    record ShipProperty(int weight, String symbol) {}

    public static HashMap<String, ShipProperty> ships;

    static {
        ships = new HashMap<>();
        //ships.put("Aircraft Carrier", new ShipProperty(5, "■"));
        //ships.put("Battleship", new ShipProperty(4, "◆"));
        //ships.put("Destroyer", new ShipProperty(3, "▲"));
        ships.put("Submarine", new ShipProperty(3, "★"));
        //ships.put("Patrol Boat", new ShipProperty(2, "○"));
    }
}
