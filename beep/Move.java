
// Defines 2 pairs of ints that describe a move made by the knight
// from pos pair1 to pos pair2 using chess notation
public class Move {
    Position pos1;
    Position pos2;

    public Move(Position p1, Position p2) {
        pos1 = p1; pos2 = p2;
    }
    
    public String toString() {
        return pos1 + " -> " + pos2; 
    }
}