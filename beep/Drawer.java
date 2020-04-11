import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.event.*;
import java.util.*;

class Drawer {
    Controller c;
    Board b;
    JFrame frame;
    public static void main(final String[] args) {
        Drawer d = new Drawer();
        // d.Test(5, 5);
        d.createAndShowGUI(5, 5);
        // d.bigTest(6, 6, 10, 10);
        // d.bigTest(3, 3, 5, 5);
    }
    
    public void createAndShowGUI(int w, int h) {
        frame = new JFrame("Chess time!");
        JPanel boardPane = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        boardPane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(209, 209, 209), Color.BLACK));

        b = new Board(w, h);
        b.initUI();

        frame.getContentPane().add(b);
        frame.pack();

        
/*         boardPane.add(b);
        
        frame.getContentPane().add(boardPane, BorderLayout.CENTER);
        frame.pack();
 */
        //frame.getContentPane().add(b, BorderLayout.CENTER);

/*     c = new Controller(b);
        c.initUI();
        
        MyGlassPane glassPane = new MyGlassPane(b);
        frame.setGlassPane(glassPane);
        JButton button = new JButton("RESET");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                createAndShowGUI(w, h);
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
                glassPane.setVisible(!glassPane.isVisible());
            }
        });
        
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        
        BoxLayout box = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        buttonPanel.setLayout(box);

        buttonPanel.add(button);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(button2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(button3);
        
        controlPanel.add(c, BorderLayout.EAST);
        controlPanel.add(buttonPanel, BorderLayout.WEST);
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
        frame.pack(); */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }
    /* lightweight memory version with no visual elements */
    public void initQuietTest(int w, int h, String start) {
        b = new Board(w, h, start);
        c = new Controller(b);
        c.start();
    }
    
    /* tests all starting positions on a board of W x H dimensions */
    public synchronized void Test(int w, int h) {
        ArrayList<String> fails = new ArrayList<String>();
        
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                String current = "" + (char)(i + 65) + (j + 1);
                initQuietTest(w, h, current);                
                try {
                    wait(10);
                }
                catch (Exception e) {
                    System.out.println("heck :(");
                }
                if (!c.isSuccess())
                    fails.add(current);
            }
        if (fails.size() == w * h)
            System.out.println("impossible with board size : " + w + " x " + h);
        else if(fails.size() == 0)
            System.out.println("perfect with board size : "+ w + " x " + h + "!");
        else  System.out.println(fails + " fail with board size : " + w + " x " + h);
    }
    
    /* tests all starting positions on boards in range from w1 x h1 -> w2 x h2 */
    public void bigTest(int w1, int h1, int w2, int h2) {
        for (int w = w1; w <= w2; w++)
            for (int h = h1; h <= h2; h++)
                Test(w, h);
        System.out.println("done!");
    }
}