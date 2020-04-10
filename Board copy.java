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
        String test = "" + (char)((int)(Math.random() * w) + 65) + ((int)(Math.random() * h) + 1);
        test(test);
    }

    public Board(int w, int h, String startPos) {
        initBoard(w, h);
        test(startPos);
    }

    public void initBoard(int w, int h) {
        
        boardArr = new Tile[h][w];
        moveNumber = 0;
        moveList = new ArrayList<String>();
        positionIndexMap = new HashMap<String, String>();
        KnightPosition = null;
        
        Font font = new Font("Corbel", Font.PLAIN, 32);
        setLayout(new GridLayout(boardArr.length + 1, boardArr[0].length + 1));
        setPreferredSize(new Dimension(w * 100 + 100, h * 100 + 100));
        
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
    }


    public void test() {
        goTo("A1");
    }
    
    public void test(String pos) {
        goTo(pos);
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);
    }
    
    // 'p' is in char - number format ex. "A1"
    public void goTo(String p) {
        String move = KnightPosition + " -> " + p;
        
        if (KnightPosition != null) {
            String numericPos = positionIndexMap.get(KnightPosition);
            boardArr[Integer.valueOf(numericPos.substring(1))][Integer.valueOf(numericPos.substring(0,1))].leave(moveNumber);
        }
        moveNumber++;

        KnightPosition = p;
        moveList.add(move);
        visit(positionIndexMap.get(p));
        getPossibleMoves();
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

        for (int row = Math.max(0, KnightY - 5); row < Math.min(boardArr.length, KnightY + 5); row++)
            for (int collumn = Math.max(0, KnightX - 5); collumn < Math.min(boardArr[row].length, KnightX + 5); collumn++) {
                Tile currentTile = boardArr[row][collumn];
                if (currentTile.isVisitable()) {
                    if ((row == KnightY + 2 || row == KnightY - 2) &&
                    (collumn == KnightX + 1 || collumn == KnightX - 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + (row + 1));
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

    public ArrayList<String> getPossibleMoves(String pos) {
        ArrayList<String> out = new ArrayList<String>();
        String numericPos = positionIndexMap.get(pos);
        int KnightX = Integer.valueOf(numericPos.substring(0, 1));
        int KnightY = Integer.valueOf(numericPos.substring(1));

        for (int row = Math.max(0, KnightY - 5); row < Math.min(boardArr.length, KnightY + 5); row++)
            for (int collumn = Math.max(0, KnightX - 5); collumn < Math.min(boardArr[row].length, KnightX + 5); collumn++) {
                Tile currentTile = boardArr[row][collumn];
                if (currentTile.isVisitable()) {
                    if ((row == KnightY + 2 || row == KnightY - 2) &&
                    (collumn == KnightX + 1 || collumn == KnightX - 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + (row + 1));
                    }
                    else if ((collumn == KnightX + 2 || collumn == KnightX -2) &&
                    (row == KnightY - 1 || row == KnightY + 1)) {
                        currentTile.isInRange(true);
                        out.add("" + (char)(collumn + 65) + (row + 1));
                    }
                    else currentTile.isInRange(false);
                }
            }
        return out;
    }

    public void actionPerformed(ActionEvent e) {
        Component source = (Component)e.getSource();
        char posX = (char)(((source.getBounds().x - 100)/ 100) + 65);
        int posY = ((this.getHeight()) / 100) - (source.getBounds().y / 100) - 1;
        goTo(posX + "" + posY);
    }
    
    public void stuck() {
        // System.out.println(moveList);
        // System.out.println("Moved : " + moveNumber + " times.");
        // System.out.print(moveNumber + " / ");
    }

    public boolean completed() {
        return ((double)moveNumber / (((this.getHeight() - 20) / 100) * ((this.getWidth() - 20) / 100))) * 100 == 100.0;
    }

    public String toString() {
        String out = "";
        out += "Moves : " + moveNumber + "\n";
        out += "Movelist : " + moveList + "\n";
        out += "This size : " + getSize() + "\n";
        return out;
    }
}