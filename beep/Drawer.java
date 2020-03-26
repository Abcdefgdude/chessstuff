import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
class Drawer extends JPanel {
    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Chess time!");
        
        JPanel knightPanel = new JPanel();
        JPanel boardPanel = new JPanel();
        
        knightPanel.setOpaque(false);
        knightPanel.add(new KnightIcon(), BorderLayout.CENTER);
        knightPanel.setSize(500, 500);
        
        boardPanel.add(new Board(5, 5), BorderLayout.CENTER);
        boardPanel.setSize(500, 500);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Board(5, 5), BorderLayout.CENTER);

        frame.getLayeredPane().add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        frame.getLayeredPane().add(knightPanel, JLayeredPane.PALETTE_LAYER);
        
        System.out.println(frame.getLayeredPane().getComponentCount());
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

}