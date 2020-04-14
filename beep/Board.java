import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
    
    Tile[][] boardArr;
    private int width;
    private int height;
    private int tileSize = 100;
    
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
        test();
    }

    public Board(int w, int h, String startPos) {
        initBoard(w, h);
        goTo(startPos);
    }
    /**
     * Initializes internal board array + necessary components
     * @param w board width
     * @param h board height
     * @see #initUI
     */
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
                
                String x = col >= 100 ? "" + col : col >= 10 ? "0" + col : "00" + col;
                String y = row >= 100 ? "" + row : row >= 10 ? "0" + row : "00" + row;
                String sx = row + 1 >= 100 ? "" + (row + 1) : (row + 1) >= 10 ? "0" + (row + 1) : "00" + (row + 1);
                
                String current = ("" + (char)(65 + col) + sx);
                positionIndexMap.put(current, x + y);
            }
    }
    /** Initializes visual elements */
    public void initUI() {
        
        if (width >= 10 || height >= 8)
            tileSize = 75;
        Font font = new Font("Corbel", Font.BOLD, 36);
        setLayout(new GridLayout(boardArr.length + 1, boardArr[0].length + 1));
        setPreferredSize(new Dimension((width * tileSize) + tileSize, (height * tileSize) + tileSize));
        setBackground(Color.WHITE);

        Color lightColor = new Color(237, 223, 197);
        Color darkColor = new Color(92, 58, 0);
        // Init tiles and set row labels
        for (int row = boardArr.length - 1; row >= 0; row--) {
            JLabel sideLabel = new JLabel("" + (row + 1) + "   ", SwingConstants.TRAILING);
            sideLabel.setFont(font);
            add(sideLabel);
            for (int col = 0; col < boardArr[row].length; col++) {    
                boardArr[row][col].initUI();
                boardArr[row][col].setColor((col + row) % 2 == 0 ? darkColor : lightColor );
                add(boardArr[row][col]);
            }
        }
        // Bottom row of labels (A - X);
        add(new JLabel());
        for (int col = 0; col < boardArr[0].length; col++) {
            JLabel bottomLabel = new JLabel("" + (char)(col + 65), SwingConstants.CENTER);
            bottomLabel.setFont(font);
            add(bottomLabel);
        }
    validate();
    }

    public void test() {
        int y = (int)(Math.random() * height + 1);
        String strY = y >= 100 ? "" + y : y >= 10 ? "0" + y : "00" + y;
        String strX = "" + (char)((int)(Math.random() * width + 65)); 
        goTo(strX + strY);
    }
    
    public void test(String pos) {
        goTo(pos);
    }
    
    /** @param p is in char - number format ex. "A001"  */  
    public void goTo(String p) {
        String move = KnightPosition + " -> " + p;
        
        if (KnightPosition != null) {
            String numericPos = positionIndexMap.get(KnightPosition);
            boardArr[Integer.valueOf(numericPos.substring(3))][Integer.valueOf(numericPos.substring(0,3))].leave(moveNumber);
            moveList.add(move);
        }
        moveNumber++;

        KnightPosition = p;
        visit(positionIndexMap.get(p));
        getPossibleMoves();
        repaint();
    }
    
    /**  
     * @param index Precondition :: 4 char string representing collumn/row of board 
     * ex. "000000" = top left corner */
    protected void visit(String index) {
        int collumn = Integer.valueOf(index.substring(0, 3));
        int row = Integer.valueOf(index.substring(3));
        boardArr[row][collumn].visit();
    }
    /** @param pos is in char format ex "A001" */
    public ArrayList<String> getPossibleMoves(String pos) {
        ArrayList<String> out = new ArrayList<String>();
        
        String numericPos = positionIndexMap.get(pos);
        int KnightX = Integer.valueOf(numericPos.substring(0, 3));
        int KnightY = Integer.valueOf(numericPos.substring(3));
        
        for (String n : knightRange) {
            int dx = KnightX + Integer.parseInt(n.substring(0, 2));
            int dy = KnightY + Integer.parseInt(n.substring(2));
            if (isValid(dx, dy))
                if (boardArr[dy][dx].isVisitable()) {
                    boardArr[dy][dx].isInRange(true);
                    dy = dy + 1;
                    out.add("" + (char)(dx + 65) + (dy >= 100 ? "" + dy : dy >= 10 ? "0" + dy : "00" + dy));
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
        int posY = (this.getHeight() / tileSize) - (source.getBounds().y / tileSize) - 1;
        String strY = posY >= 100 ? "" + posY : posY >= 10 ? "0" + posY : "00" + posY; 
        clear();
        goTo(posX + "" + strY);
    }
    
    /** returns true if position at dx, dy is within the bounds of the board */
    public boolean isValid(int dx, int dy) {
        return !((dx < 0 || dy < 0) || (dx >= width || dy >= height));
    }

    public boolean completed() {
        return moveNumber == height * width;
    }
    
    public boolean isClosed() {
        if (completed()) {
            String numericPos = positionIndexMap.get(KnightPosition);
            int KnightX = Integer.valueOf(numericPos.substring(0, 3));
            int KnightY = Integer.valueOf(numericPos.substring(3));
            
            String start = moveList.get(0).substring(0, 3);
            String startPos = positionIndexMap.get(start);
            int startX = Integer.valueOf(startPos.substring(0, 3));
            int startY = Integer.valueOf(startPos.substring(3));
             
            for (String n : knightRange) {
                int dx = KnightX + Integer.parseInt(n.substring(0, 3));
                int dy = KnightY + Integer.parseInt(n.substring(3));
                if (isValid(dx, dy))
                    if (startX + dx == KnightX && startY + dy == KnightY)
                        return true;
                    }
            }
        return false;
    }

    /**
     * highlights all tiles in list  
     * @param color color to highlight tiles
     * @param list which tiles to highlight in char format ex. "A001"
     */
    public void highlightTiles(ArrayList<String> tileList, Color c) {
        for (String s : tileList) {
            String numericPos = positionIndexMap.get(s);
            int x = Integer.valueOf(numericPos.substring(0, 3));
            int y = Integer.valueOf(numericPos.substring(3));
            // System.out.println("" + x + y);
            boardArr[y][x].setHighlight(c);
            // repaint();
        }
        repaint();
    }
    /** IMPORTANT: in range function in the 'getPossibleMoves' method must be disabled 
     * @see #getPossibleMoves
    */
    public void highlightWeights() {
        for (int row = boardArr.length - 1; row >= 0; row--) 
            for (int col = 0; col < boardArr[row].length; col++) {    
                String sx = row + 1 >= 100 ? "" + (row + 1) : (row + 1) >= 10 ? "0" + (row + 1) : "00" + (row + 1);
                String current = ("" + (char)(65 + col) + sx);
                int weight = getPossibleMoves(current).size();
                boardArr[row][col].drawLabel(weight);
            }
    }

    public ArrayList<String> getMoveList() {
        return moveList;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getH() {
        return height;
    }

    public HashMap<String, String> getPositionMap() {
        return positionIndexMap;
    }
    
    public String getKnightPosition() {
        return KnightPosition;
    }
    
    public String getintKnightPosition() {
        return positionIndexMap.get(KnightPosition);
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        super.paintComponent(g);
    }   
    
    @Override
    public String toString() {
        String out = "";
        out += "Moves : " + moveNumber + "\n";
        out += "Movelist : " + moveList + "\n";
        // out += "This size : " + getSize() + "\n";
        // out += "position map : " + positionIndexMap + "\n";
        out += "Possible moves : " + getPossibleMoves() + "\n";
        return out;
    }
}