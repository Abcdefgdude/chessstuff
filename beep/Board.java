import java.awt.*;
import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
    
    Tile[][] boardArr;
    int moveNumber;
    ArrayList<Move> moveList;
    HashMap<Position, String> positionIndexMap;
    HashMap<String, Position> indexPositionMap;

    Position KnightPosistion;
    
    public Board() {
        initBoard(8, 8);
    }
    
    public Board(int w, int h) {
        initBoard(w, h);
    }

    public void initBoard(int w, int h) {
        
        boardArr = new Tile[w][h];
        moveNumber = 0;
        moveList = new ArrayList<Move>();
        positionIndexMap = new HashMap<Position, String>();
        indexPositionMap = new HashMap<String, Position>();

        setLayout(new GridLayout(boardArr[0].length, boardArr.length));
        setPreferredSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));
        
        // Initiliazing tile array to fill boardArr and creating maps
        
        for (int row = boardArr.length - 1; row >= 0; row--) {
            for (int col = 0; col < boardArr[row].length; col++) {    
                boardArr[row][col] = new Tile();
                boardArr[row][col].setColor((col + row) % 2 == 0 ? Color.LIGHT_GRAY : Color.darkGray);
                add(boardArr[row][col]);
                
                Position current = new Position("" + (char)(65 + col), row + 1);
                positionIndexMap.put(current, "" + col + "" + row);
                indexPositionMap.put("" + col + "" + row, current); 
            }
        }
        System.out.println(positionIndexMap.get(new Position("A" + boardArr.length)));
        // visit(positionIndexMap.get("A" + boardArr.length));
        System.out.println(positionIndexMap);
    }

    public void paint(Graphics g) {        
        paintComponents(g);
    }
    
    public void goTo(Position p) {
        
        moveNumber++;
        Move move = new Move(KnightPosistion, p);
        moveList.add(move);

    }
    // Precondition :: 2 char string representing collumn - row of board
    public void visit(String index) {
        
        int row = Integer.valueOf(index.substring(0, 1));
        int collumn = Integer.valueOf(index.substring(1));
        
        boardArr[row][collumn].visit();
    }
}