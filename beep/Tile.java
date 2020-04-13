import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import knight.*;

@SuppressWarnings("serial")
public class Tile extends JPanel {
    
    private boolean wasVisited;
    private boolean isNull;
    private boolean inRange;
    private boolean isSelected;

    private Color highlightColor;
    private Color currentColor;
    private static Color deadColor;
    private static Color canVisitColor;
    private static Color selectedColor;
    private Board parent;

    public Tile(Board b) {
        
        wasVisited = false;
        isNull = false;
        inRange = false;
        isSelected = false;
        parent = b;

    }
    // this MUST be called before trying to draw a tile object
    public void initUI() {

        selectedColor = new Color(182, 252, 3);
        canVisitColor = new Color(140, 237, 95);
        deadColor = new Color(40, 40, 40);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (inRange) {
                    ActionEvent ae = new ActionEvent(e.getComponent(), e.getID(), e.paramString());
                    parent.actionPerformed(ae);
                }
            }

            public void mouseEntered(MouseEvent e) {
                if (inRange) {
                    isSelected = true;
                    repaint();
                }
            }

            public void mouseExited(MouseEvent e) {
                isSelected = false;
                repaint();
            }
        });
    }

    public void setColor(Color c) {
        currentColor = c;
        // repaint();
    }
    
    public void setHighlight(Color c) {
        highlightColor = c;
    }
    public void visit() {
        KnightIcon knight = new KnightIcon();
        knight.setPreferredSize(new Dimension(parent.getTileSize(), parent.getTileSize()));
        add(knight, BorderLayout.CENTER);
        validate();
    }

    public void leave(int n) {
        removeAll();
        JLabel num = new JLabel("" + n);
        num.setFont(new Font("Corbel", Font.BOLD, parent.getTileSize() / 2));
        num.setForeground(Color.GRAY);
        add(num, BorderLayout.CENTER);
        validate();
        repaint();
        wasVisited = true;
    }
    
    public boolean isVisitable() {
        return !(isNull || wasVisited);
    }

    public void isInRange(boolean in) {
        inRange = in;
    }
    
    public Color getState() {
        if (highlightColor != null)
            return highlightColor;
        if (!isVisitable())
            return deadColor;
        if (inRange)
            if (isSelected) 
                return selectedColor;    
            else return canVisitColor;
        return currentColor;
    }
    
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        g.setColor(getState());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}