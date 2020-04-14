import java.util.*;

public class RandomWarnsdorff extends Algorithm {
    public Board b;
    HashMap<Integer, String> nextMoves;
    public RandomWarnsdorff(Board b) {
        this.b = b;
    }
    // returns the tile that has the fewest options 
    
    public String getNextMove(ArrayList<String> moves) {
        if (moves.size() == 0)
            return "";
        nextMoves = new HashMap<Integer, String>();
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).equals(b.getKnightPosition()))
                continue;
            if (nextMoves.containsKey(b.getPossibleMoves(moves.get(i)).size()))
                nextMoves.put(b.getPossibleMoves(moves.get(i)).size(), resolve(moves.get(i), nextMoves.get(b.getPossibleMoves(moves.get(i)).size())));                
            else nextMoves.put(b.getPossibleMoves(moves.get(i)).size(), moves.get(i));
        }
        int i = 0;
        while (nextMoves.get(i) == null && i <= 8)
             i++;
        return nextMoves.get(i);
    }
    
    // resolves situation when there are two equal situations
    public String resolve(String pos1, String pos2) {
        return ((int)(Math.random() * 2)) == 1 ? pos1 : pos2;
    }
    
    public String toString() {
        return "(Random) Warnsdorff's Rule";
    }
}