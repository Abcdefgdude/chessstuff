import javax.swing.*;
import java.awt.*;
import java.util.*;

@SuppressWarnings("serial")
public class MyGlassPane extends JComponent {
    private Board b;
    private int height;
    private int tileSize;
    private ArrayList<String> moveList;
    private HashMap<String, String> positionIndexMap;
    
    public MyGlassPane(Board bb) {
        b = bb;
        setOpaque(false);
        setVisible(false);
    }
    
    public void update() {
        moveList = b.getMoveList();
        height = b.getHeight();
        tileSize = b.getTileSize();
        positionIndexMap = b.getPositionMap();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        for (String move : moveList) {
            String pos1 = positionIndexMap.get(move.substring(0, 2));
            String pos2 = positionIndexMap.get(move.substring(6, 8));
            int x1 = Integer.parseInt(pos1.substring(0, 1)) * tileSize + tileSize;
            int x2 = Integer.parseInt(pos2.substring(0, 1)) * tileSize + tileSize;
            int y1 = (height * tileSize) - Integer.parseInt(pos1.substring(1)) * tileSize;
            int y2 = (height * tileSize) - Integer.parseInt(pos2.substring(1)) * tileSize;
/*          System.out.println("x1 : " + x1);
            System.out.println("x2 : " + x2);
            System.out.println("y1 : " + y1);
            System.out.println("y2 : " + y2);
*/
            g.drawLine(x1, y1, x2, y2);
        }
    }
}