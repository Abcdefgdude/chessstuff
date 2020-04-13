import javax.swing.*;
import java.awt.*;
import java.util.*;

@SuppressWarnings("serial")
public class MyGlassPane extends JComponent {
    private Board b;
    private int height;
    private int tileSize;
    private int offset;
    private ArrayList<String> moveList;
    private HashMap<String, String> positionIndexMap;
    
    public MyGlassPane(Board bb) {
        b = bb;
        setVisible(false);
    }
    
    public void update() {
        moveList = b.getMoveList();
        height = b.getH();
        tileSize = b.getTileSize();
        positionIndexMap = b.getPositionMap();
        offset = b.getBounds().x + (tileSize / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(5));
        for (String move : moveList) {
            String pos1 = positionIndexMap.get(move.substring(0, 3));
            String pos2 = positionIndexMap.get(move.substring(7, 10));
            int x1 = Integer.parseInt(pos1.substring(0, 2)) * tileSize + tileSize + offset;
            int x2 = Integer.parseInt(pos2.substring(0, 2)) * tileSize + tileSize + offset;
            int y1 = (height * tileSize) - Integer.parseInt(pos1.substring(2)) * tileSize - (tileSize / 2);
            int y2 = (height * tileSize) - Integer.parseInt(pos2.substring(2)) * tileSize - (tileSize / 2);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}