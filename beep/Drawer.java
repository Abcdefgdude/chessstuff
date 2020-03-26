import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
class Drawer extends JPanel {
    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Chess time!");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Board(5, 5), BorderLayout.CENTER);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

}