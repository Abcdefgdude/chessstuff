import java.util.*;

public class Warnsdorff extends Algorithm {
    Board b;
    
    public Warnsdorff(Board b) {
        this.b = b;
    }
    // returns the tile that has the fewest options 
    public String getNextMove(ArrayList<String> moves) {
        if (moves.size() == 0)
            return "";
        HashMap<Integer, String> nextMoves = new HashMap<Integer, String>();
        for (int i = 0; i < moves.size(); i++) 
            nextMoves.put(b.getPossibleMoves(moves.get(i)).size(), moves.get(i));
        int i = 0;
        while (nextMoves.get(i) == null && i <= 8)
             i++;
        return nextMoves.get(i);
    }

    public String toString() {
        return "Warnsdorff's Rule";
    }
}