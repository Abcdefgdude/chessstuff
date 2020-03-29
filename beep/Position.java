// Defines a tile on the chess board using a letter and a number
public class Position {
    String pos;
    public Position(String collumn, int row) {
        pos = collumn + row;
    }
    // Two character string in format ex. "A1"
    public Position(String str) {
        pos = str;
    }
    public static Position integerToPosistion(int collumn, int row) {
        String c = "PLACEHOLDER";
        int r = 0; // PLACEHOLDER 
        return new Position(c, r);
    }
    public String toString() {
        return pos;
    }
}