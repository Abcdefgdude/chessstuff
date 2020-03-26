import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
    Tile[][] boardArr;
    
    public Board() {
        boardArr = new Tile[8][8];
        initBoard();
    }
    
    public Board(int w, int h) {
        boardArr = new Tile[w][h];
        initBoard();
    }

    public void initBoard() {
        
        setLayout(new GridLayout(boardArr[0].length, boardArr.length));
        setPreferredSize(new Dimension(500, 500));
        setMaximumSize(new Dimension(500, 500));
        
        // Initiliazing tile array to fill boardArr
        
        for (int j = 0; j < boardArr.length; j++) {
            for (int i = 0; i < boardArr[j].length; i++) {    
                boardArr[j][i] = new Tile();
                boardArr[j][i].setColor((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.darkGray);
                add(boardArr[j][i]);
            }
        }
        boardArr[0][0].visit();
        boardArr[0][0].leave();
        boardArr[2][0].visit();
    }

    public void paint(Graphics g) {        
        paintComponents(g);
    }
}