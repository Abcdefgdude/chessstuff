package knight;

import java.awt.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class KnightIcon extends JPanel {
    static String imageURL = "C:\\Users\\Ben\\Documents\\Factorio\\beep\\knight\\knight-chess-piece-silhouette-3.png";
    static Image img;
    
    public KnightIcon() {   
        img = Toolkit.getDefaultToolkit().getImage(imageURL);
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);
        // System.out.println(getSize());
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 2, 0, this.getWidth()-4, this.getHeight() - 4, this);
        }
}