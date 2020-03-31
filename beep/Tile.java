import java.awt.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tile extends JPanel{
    
    private boolean isVisited;
    private boolean hasKnight;
    private boolean isNull;
    private boolean inRange;
    
    private Color currentColor;
    private static Color deadColor = Color.BLACK;
    private static Color canVisitColor = Color.GREEN;


    public Tile() {
        isVisited = false;
        hasKnight = false;
        isNull = false;
        inRange = false;
        currentColor = Color.BLACK;
        setSize(new Dimension(20, 20));
    }
    
    public void setColor(Color c) {
        currentColor = c;
    }

    public void visit() {
        hasKnight = true;
    }
    
    public void leave() {
        removeAll();
        isVisited = true;
        hasKnight = false;
        currentColor = Color.BLACK;
    }
    
    public boolean isVisitable() {
        return !(isNull || isVisited);
    }

    public void isInRange(boolean in) {
        inRange = in;
    }
    
    public Color getState() {
        if (!isVisitable())
            return deadColor;
        if (inRange)
            return canVisitColor;
        return currentColor;
    }
    
    public void paint(Graphics g) {
        g.setColor(getState());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        if (hasKnight) {
            add(new KnightIcon(), BorderLayout.CENTER);
            paintComponents(g);
        }
    }
}