import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.util.*;
/**@author Ben Young
 * @version 04.13.2020
 */
class Drawer {
    private Controller c;
    private Board b;
    private JFrame frame;
    private MyGlassPane glassPane;
    private int width;
    private int height;
    
    public static void main(final String[] args) {
        Drawer d = new Drawer();
        // d.Test(6, 6);
        d.createAndShowGUI(5, 5);
        // d.bigTest(6, 6, 10, 10);
        // d.bigTest(3, 3, 5, 5);
    }

    public void createAndShowGUI(int w, int h) {
        
        width = w;
        height = h;
        
        frame = new JFrame("Chess time!");
        JPanel boardPane = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel buttonPanel = createButtonPanel();
        boardPane.setBackground(Color.WHITE);
        b = new Board(w, h, "C01");
        // b = new Board(w, h);
        b.initUI();
        boardPane.add(b);
        
        glassPane = new MyGlassPane(b);
        frame.setGlassPane(glassPane);
        
        frame.getContentPane().add(boardPane, BorderLayout.CENTER);
        frame.pack();

        c = new Controller(b);
        c.initUI();
        
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.add(c, BorderLayout.EAST);
        controlPanel.add(buttonPanel);
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
        
        boardPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(209, 209, 209), Color.BLACK));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
    
    /* lightweight memory version with no visual elements */
    public void initQuietTest(int w, int h, String start) {
        this.b = new Board(w, h, start);
        this.c = new Controller(this.b);
        this.c.start();
    }
    
    /* tests all starting positions on a board of W x H dimensions */
    public synchronized ArrayList<String> Test(int w, int h) {
        ArrayList<String> fails = new ArrayList<String>();
        
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                String current = "" + (char)(i + 65) + (j + 1 >= 10 ? "" + (j + 1) : "0" + (j + 1));
                Drawer draw = new Drawer();
                draw.initQuietTest(w, h, current);                
                try {
                    wait(10);
                }
                catch (Exception e) {
                    System.out.println("heck :(");
                }
                if (!draw.c.isSuccess())
                    fails.add(current);
            }
        if (fails.size() == w * h)
            System.out.println("impossible with board size : " + w + " x " + h);
        else if(fails.size() == 0)
            System.out.println("perfect with board size : "+ w + " x " + h + "!");
        // else  System.out.println(fails + " fail with board size : " + w + " x " + h);
        return fails;
    }
    
    /* tests all starting positions on boards in range from w1 x h1 -> w2 x h2 */
    public void bigTest(int w1, int h1, int w2, int h2) {
        for (int w = w1; w <= w2; w++)
            for (int h = h1; h <= h2; h++)
                Test(w, h);
        System.out.println("done!");
    }
    
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton button = new JButton("RESET");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createAndShowGUI(width, height);
            }
        });
        
        JButton button2 = new JButton("INFO");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(b);
            }
        });
        
        JButton button3 = new JButton("LINE TOGGLE");
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                glassPane.update();
                glassPane.setVisible(!glassPane.isVisible());
            }
        });
        
        JButton button4 = new JButton("HIGHLIGHT");
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> highlight = Test(width, height);
                b.highlightTiles(highlight, new Color(201, 72, 62));
            }
        });
        
        BoxLayout box = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        buttonPanel.setLayout(box);

        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(button2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(button3);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(button4);
        
        return buttonPanel;
    }
}