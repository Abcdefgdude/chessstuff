import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
class Drawer extends JPanel {
    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Chess time!");
        JPanel pane = new JPanel();
        pane.add(new Board(5, 9));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add(new Board(8, 8), BorderLayout.CENTER);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }

}