import java.awt.*;
import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
    
    Tile[][] boardArr;
    int moveNumber;
    ArrayList<String> moveList;
    HashMap<String, String> positionIndexMap;
    String KnightPosition;

    public Board() {
        initBoard(8, 8);
    }
    
    public Board(int w, int h) {
        initBoard(w, h);
    }

    public void initBoard(int w, int h) {
        
        boardArr = new Tile[h][w];
        moveNumber = 0;
        moveList = new ArrayList<String>();
        positionIndexMap = new HashMap<String, String>();
        KnightPosition = null;

        setLayout(new GridLayout(boardArr.length, boardArr[0].length));
        setPreferredSize(new Dimension(w * 100, h * 100));
        setMaximumSize(new Dimension(500, 500));
        
        // Initiliazing tile array to fill boardArr and creating maps
        for (int row = boardArr.length - 1; row >= 0; row--) {
            for (int col = 0; col < boardArr[row].length; col++) {    
                boardArr[row][col] = new Tile();
                boardArr[row][col].setColor((col + row) % 2 == 0 ? Color.LIGHT_GRAY : Color.darkGray);
                add(boardArr[row][col]);
                
                String current = ("" + (char)(65 + col) + (row + 1));
                positionIndexMap.put(current, "" + col + "" + row);
            }
        }
        test();
    }
    @Override
    public void paint(Graphics g) {        
        paintComponents(g);
    }
    
    // 'p' is in char - number format ex. "A1"
    public void goTo(String p) {
        moveNumber++;
        String move = KnightPosition + " -> " + p + "\n";
        if (KnightPosition != null) {
            String numericPos = positionIndexMap.get(KnightPosition);
            boardArr[Integer.valueOf(numericPos.substring(1))][Integer.valueOf(numericPos.substring(0,1))].leave();
        }
        KnightPosition = p;
        moveList.add(move);
        visit(positionIndexMap.get(p));
        System.out.println("Can move to :: " + getPossibleMoves());
    }
    
    // Precondition :: 2 char string representing collumn - row of board 
    // ex. "00" = top left corner
    protected void visit(String index) {
        
        //System.out.println(index);
        int collumn = Integer.valueOf(index.substring(0, 1));
        int row = Integer.valueOf(index.substring(1));
       
        boardArr[row][collumn].visit();
    }

    public ArrayList<String> getPossibleMoves() {
        ArrayList<String> out = new ArrayList<String>();
        String numericPos = positionIndexMap.get(KnightPosition);
        int KnightX = Integer.valueOf(numericPos.substring(0, 1));
        int KnightY = Integer.valueOf(numericPos.substring(1));

        for (int row = 0; row < boardArr.length; row++)
            for (int collumn = 0; collumn < boardArr[row].length; collumn++) {
                Tile currentTile = boardArr[row][collumn];
                if ((row == KnightY + 2 || row == KnightY - 2) &&
                (collumn == KnightX + 1 || collumn == KnightX - 1)) {
                    currentTile.isInRange(true);
                    out.add("" + row + collumn);
                }
                else if ((collumn == KnightX + 2 || collumn == KnightX -2) &&
                (row == KnightY - 1 || row == KnightY + 1)) {
                    currentTile.isInRange(true);
                    out.add("" + row + collumn);
                }
                else currentTile.isInRange(false);
            }
        return out;
    }

    public void test() {
        goTo("A5");
        goTo("C3");
        System.out.println(moveList);
    }
}