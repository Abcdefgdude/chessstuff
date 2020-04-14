import java.util.*;

public class MoveOrdering extends Algorithm{
    private Board b;
    
    private static int[][] moveOrders = {
        {1, 2, 3, 4, 5, 6, 7, 8},
        {2,1,3,4,5,6,7,8},
        {3,1,2,4,5,6,7,8},
        {1,3,2,4,5,6,7,8},
        {2,3,1,4,5,6,7,8},
        {3,2,1,4,5,6,7,8},
        {4,2,1,3,5,6,7,8},
        {2,4,1,3,5,6,7,8},
        {1,4,2,3,5,6,7,8},
        {4,1,2,3,5,6,7,8},
        {2,1,4,3,5,6,7,8},
        {1,2,4,3,5,6,7,8},
        {1,3,4,2,5,6,7,8},
        {3,1,4,2,5,6,7,8},
        {4,1,3,2,5,6,7,8},
        {1,4,3,2,5,6,7,8},
        {3,4,1,2,5,6,7,8},
        {4,3,1,2,5,6,7,8},
        {4,3,2,1,5,6,7,8}  
    };
    private static String[] knightRange = {"0102", "0201", "02-1", "01-2", "-1-2",  "-2-1", "-201", "-102",};

    private int[] currentOrder;
    public MoveOrdering(Board bb, int moveOrder) {
        b = bb;
        currentOrder = moveOrders[moveOrder];
    }
    
    public String getNextMove(ArrayList<String> moves) {
        int min = 8;
        for (String m : moves) 
            min = Math.min(min, b.getPossibleMoves(m).size());
        final int fmin = min;
        moves.removeIf(n -> b.getPossibleMoves(n).size() != fmin);
        if (moves.size() == 1)
            return moves.get(0);
        else return resolve(moves); 
    }

    public String resolve(ArrayList<String> moves) {
        return "";
    }
    /**
     * Returns possible knight moves in order according the current moveOrder
     *
     * @return all positions where the knight can reach
     */
    public ArrayList<String> getKnightRange() {
        ArrayList<String> out = new ArrayList<String>();
        String currentPos = b.getintKnightPosition();
        int currentPosX = Integer.valueOf(currentPos.substring(0, 3));
        int currentPosY = Integer.valueOf(currentPos.substring(3));      
        for (int i : currentOrder) {
            String currD = knightRange[i];
            int dx = Integer.valueOf(currD.substring(0, 2));
            int dy = Integer.valueOf(currD.substring(2));
            // String current = 
            // out.add()
        }
        return out;
    }
}