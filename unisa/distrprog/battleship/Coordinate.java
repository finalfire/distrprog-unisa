package unisa.distrprog.battleship;

public record Coordinate(int x, int y) {
    public static Coordinate fromString(String coordinate, String delim) {
        String[] coordinates = coordinate.split(delim);
        return new Coordinate(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
    }

    public String toString(String delim) {
        return this.x + delim + this.y;
    }
}
