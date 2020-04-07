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

    private Color currentColor;
    private static Color deadColor = Color.BLACK;
    private static Color canVisitColor = Color.GREEN;
    private static Color selectedColor = new Color(182, 252, 3);
    private Board parent;

    public Tile(Board b) {
        wasVisited = false;
        isNull = false;
        inRange = false;
        currentColor = Color.BLACK;
        isSelected = false;
        parent = b;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (inRange) {
                    // System.out.println(parent);
                    // System.out.println("click in range");
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
    }

    public void visit() {
        add(new KnightIcon(), BorderLayout.CENTER);
        validate();
        repaint();
    }

    public void leave() {
        removeAll();
        wasVisited = true;
    }
    
    public boolean isVisitable() {
        return !(isNull || wasVisited);
    }

    public void isInRange(boolean in) {
        inRange = in;
    }
    
    public Color getState() {
        if (!isVisitable())
            return deadColor;
        if (inRange)
            if (isSelected) 
                return selectedColor;    
            else return canVisitColor;
        return currentColor;
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(getState());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}