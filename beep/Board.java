import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
    int n, m;
    public Board() {
        n = 8;
        m = 8;
    }
    public Board(int w, int h) {
        n = w;
        m = h;      
    }
    
    public void paint(Graphics g) {
        int length = Math.min(this.getWidth() / n, this.getHeight() / n);
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < m; j++) {
                if ((i + j) % 2 == 0) g.setColor(Color.LIGHT_GRAY);
                else g.setColor(Color.DARK_GRAY);
                g.fillRect(i * length, j * length, length, length);
            }
    }
}