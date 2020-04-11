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
        for (int i = 0; i < moves.size(); i++) {
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
        int pos1Sum = sumOfPossibilites(pos1);
        int pos2Sum = sumOfPossibilites(pos2);
        if (pos1Sum > pos2Sum)
            return pos1;
        else return pos2;
    }
    // helper method for resolve
    
    public int sumOfPossibilites(String pos) {
        ArrayList<String> moves = b.getPossibleMoves(pos);
        int sum = 0;
        for (String m : moves) 
            sum += b.getPossibleMoves(m).size();
        return sum;
    }

    public String toString() {
        return "Warnsdorff's Rule";
    }
}