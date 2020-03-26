import java.awt.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tile extends JPanel{
    private boolean isVisited;
    private boolean hasKnight;
    private boolean isNull;
    private Color color;
    
    public Tile() {
        isVisited = false;
        hasKnight = false;
        isNull = false;
        color = Color.BLACK;
        setSize(new Dimension(20, 20));
    }
    
    public void visit() {
        isVisited = true;
        hasKnight = true;
    }
    
    public boolean canVisit() {
        return !(hasKnight || isNull || isVisited);
    }
    public void leave() {
        hasKnight = false;
    }
    public void setColor(Color c) {
        color = c;
    }

    public void paint(Graphics g) {
        g.setColor(canVisit() ? color : Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if (hasKnight)
            add(new KnightIcon(), BorderLayout.CENTER);
        //System.out.println("tile painted");
    }
}