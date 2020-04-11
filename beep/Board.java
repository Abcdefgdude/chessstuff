import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
    
    Tile[][] boardArr;
    private int width;
    private int height;
    private final int tileSize = 100;
    
    ArrayList<String> moveList;
    int moveNumber;
    String KnightPosition;
    HashMap<String, String> positionIndexMap;

    private static String[] knightRange = {"0102", "0201", "01-2", "02-1", "-102", "-201", "-1-2", "-2-1"};
    
    public Board() {
        initBoard(8, 8);
    }
    
    public Board(int w, int h) {
        initBoard(w, h);
        String test = "" + (char)((int)(Math.random() * w) + 65) + ((int)(Math.random() * h) + 1);
        test(test);
    }

    public Board(int w, int h, String startPos) {
        initBoard(w, h);
        test(startPos);
    }

    public void initBoard(int w, int h) {
        
        width = w;
        height = h;

        boardArr = new Tile[height][width];
        moveNumber = 0;
        moveList = new ArrayList<String>();
        positionIndexMap = new HashMap<String, String>();
        KnightPosition = null;

        for (int row = boardArr.length - 1; row >= 0; row--) 
            for (int col = 0; col < boardArr[row].length; col++) {    
                boardArr[row][col] = new Tile(this);
                String current = ("" + (char)(65 + col) + (row + 1));
                positionIndexMap.put(current, "" + col + "" + row);
            }
    }
    
    public void initUI() {
        
        Font font = new Font("Corbel", Font.PLAIN, 32);
        setLayout(new GridLayout(boardArr.length + 1, boardArr[0].length + 1));
        setPreferredSize(new Dimension(width * tileSize + tileSize, height * tileSize + tileSize));
        
        // Init board array / tiles and set row labels
        for (int row = boardArr.length - 1; row >= 0; row--) {
            JLabel sideLabel = new JLabel("" + (row + 1) + "   ", SwingConstants.TRAILING);
            sideLabel.setFont(font);
            add(sideLabel);
            for (int col = 0; col < boardArr[row].length; col++) {    
                boardArr[row][col].initUI();
                boardArr[row][col].setColor((col + row) % 2 == 0 ? Color.LIGHT_GRAY : Color.darkGray);
                add(boardArr[row][col]);
            }
        }
        // Bottom row of labels (A - x);
        add(new JLabel());
        for (int col = 0; col < boardArr[0].length; col++) {
            JLabel bottomLabel = new JLabel("" + (char)(col + 65), SwingConstants.CENTER);
            bottomLabel.setFont(font);
            add(bottomLabel);
        }
    }

    public void test() {
        goTo("A1");
    }
    
    public void test(String pos) {
        goTo(pos);
    }
    
    /* 'p' is in char - number format ex. "A1"*/    
    public void goTo(String p) {
        String move = KnightPosition + " -> " + p;
        
        if (KnightPosition != null) {
            String numericPos = positionIndexMap.get(KnightPosition);
            boardArr[Integer.valueOf(numericPos.substring(1))][Integer.valueOf(numericPos.substring(0,1))].leave(moveNumber);
            moveList.add(move);
        }
        moveNumber++;

        KnightPosition = p;
        visit(positionIndexMap.get(p));
        getPossibleMoves();
        repaint();
    }
    
    /* Precondition :: 2 char string representing collumn - row of board 
    ex. "00" = top left corner */
    protected void visit(String index) {
        int collumn = Integer.valueOf(index.substring(0, 1));
        int row = Integer.valueOf(index.substring(1));
        boardArr[row][collumn].visit();
    }
    /* 'pos' is in char format */
    public ArrayList<String> getPossibleMoves(String pos) {
        ArrayList<String> out = new ArrayList<String>();
        
        String numericPos = positionIndexMap.get(pos);
        int KnightX = Integer.valueOf(numericPos.substring(0, 1));
        int KnightY = Integer.valueOf(numericPos.substring(1));
        
        for (String n : knightRange) {
            int dx = KnightX + Integer.parseInt(n.substring(0, 2));
            int dy = KnightY + Integer.parseInt(n.substring(2));
            if (isValid(dx, dy))
                if (boardArr[dy][dx].isVisitable()) {
                    boardArr[dy][dx].isInRange(true);
                    out.add("" + (char)(dx + 65) + (dy + 1));
                }
        }
        return out;
    }
    
    public ArrayList<String> getPossibleMoves() {
        return getPossibleMoves(KnightPosition);
    }

    public void clear() {
        for (Tile[] arr : boardArr)
            for (Tile t : arr)
                t.isInRange(false);            
    }

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        char posX = (char)(((source.getBounds().x - tileSize)/ tileSize) + 65);
        int posY = ((this.getHeight()) / tileSize) - (source.getBounds().y / tileSize) - 1;
        clear();
        goTo(posX + "" + posY);
    }
    
    /* returns if position at dx, dy is within the bounds of the board */
    public boolean isValid(int dx, int dy) {
        return !((dx < 0 || dy < 0) || (dx >= width || dy >= height));
    }

    public boolean completed() {
        return moveNumber == height * width;
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);
    }   

    public ArrayList<String> getMoveList() {
        return moveList;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getHeight() {
        return height;
    }

    public HashMap<String, String> getPositionMap() {
        return positionIndexMap;
    }
    @Override
    public String toString() {
        String out = "";
        out += "Moves : " + moveNumber + "\n";
        out += "Movelist : " + moveList + "\n";
        out += "This size : " + getSize() + "\n";
        return out;
    }
}