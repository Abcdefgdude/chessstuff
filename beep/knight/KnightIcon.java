package knight;

import java.awt.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class KnightIcon extends JPanel {
    static String imageURL = "C:\\Users\\Ben\\Documents\\Factorio\\beep\\knight-chess-piece-silhouette-3.png";
    static Image img;
    public KnightIcon() {   
        setPreferredSize(new Dimension(100, 100));
        img = Toolkit.getDefaultToolkit().getImage(imageURL);
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
        }
}