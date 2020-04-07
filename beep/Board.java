import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
    
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
        
        Font font = new Font("Corbel", Font.PLAIN, 32);
        setLayout(new GridLayout(boardArr.length + 1, boardArr[0].length + 1));
        setPreferredSize(new Dimension(w * 100 + 20, h * 100 + 20));
        
        // Init board array / tiles and set row labels
        for (int row = boardArr.length - 1; row >= 0; row--) {
            JLabel sideLabel = new JLabel("" + (row + 1) + "   ", SwingConstants.TRAILING);
            sideLabel.setFont(font);
            add(sideLabel);
            for (int col = 0; col < boardArr[row].length; col++) {    
                boardArr[row][col] = new Tile(this);
                boardArr[row][col].setColor((col + row) % 2 == 0 ? Color.LIGHT_GRAY : Color.darkGray);
                add(boardArr[row][col]);
                
                String current = ("" + (char)(65 + col) + (row + 1));
                positionIndexMap.put(current, "" + col + "" + row);
            }
        }
        // Bottom row of labels (A - x);
        add(new JLabel());
        for (int col = 0; col < boardArr[0].length; col++) {
            JLabel bottomLabel = new JLabel("" + (char)(col + 65), SwingConstants.CENTER);
            bottomLabel.setFont(font);
            add(bottomLabel);
        }
        
        test();
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);
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
        repaint();
    }
    
    // Precondition :: 2 char string representing collumn - row of board 
    // ex. "00" = top left corner
    protected void visit(String index) {
    
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
                if (currentTile.isVisitable()) {
                    if ((row == KnightY + 2 || row == KnightY - 2) &&
                    (collumn == KnightX + 1 || collumn == KnightX - 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + row);
                    }
                    else if ((collumn == KnightX + 2 || collumn == KnightX -2) &&
                    (row == KnightY - 1 || row == KnightY + 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + (row + 1));
                    }
                    else currentTile.isInRange(false);
                }
            }
        
        if (out.size() == 0)
            stuck();        
        return out;
    }

    public ArrayList<String> getPossibleMoves(int r, int c) {
        ArrayList<String> out = new ArrayList<String>();

        int KnightX = c;
        int KnightY = r;

        for (int row = 0; row < boardArr.length; row++)
            for (int collumn = 0; collumn < boardArr[row].length; collumn++) {
                Tile currentTile = boardArr[row][collumn];
                if (currentTile.isVisitable()) {
                    if ((row == KnightY + 2 || row == KnightY - 2) &&
                    (collumn == KnightX + 1 || collumn == KnightX - 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + row);
                    }
                    else if ((collumn == KnightX + 2 || collumn == KnightX -2) &&
                    (row == KnightY - 1 || row == KnightY + 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + (row + 1));
                    }
                    else currentTile.isInRange(false);
                }
            }
        
        if (out.size() == 0)
            stuck();        
        return out;
    }

    public void test() {
        goTo("A1");
    }

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        char posX = (char)(((source.getBounds().x)/ 100) + 65);
        int posY = ((this.getHeight() - 100) / 100) - (source.getBounds().y / 100);
        goTo(posX + "" + posY);
    }
    
    public void stuck() {
        System.out.println("stuck");
        System.out.println(moveList);
        System.out.println("Moved : " + moveNumber + " times.");
        System.out.println("You were " + ((double)moveNumber / ((this.getHeight() / 100) * (this.getWidth() / 100)) * 100) + "% complete!");
    }
}