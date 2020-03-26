import java.awt.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class KnightIcon extends JPanel {
    static String imageURL = "C:\\Users\\Ben\\Documents\\Factorio\\beep\\knight-chess-piece-silhouette-3.png";
    public KnightIcon() {   
        setPreferredSize(new Dimension(100, 100));
        setOpaque(false);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
        g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
}