import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
class Drawer extends JPanel {
    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Chess time!");
        JPanel pane = new JPanel();
        Board b = new Board(5, 5);
        pane.add(b);
        pane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(209, 209, 209), Color.BLACK));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        frame.getContentPane().add(new Controller(b), BorderLayout.SOUTH);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }

}