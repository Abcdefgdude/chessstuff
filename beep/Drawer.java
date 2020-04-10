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
        // d.Test(1, 1);
        // d.initUI(8, 10);
        // d.bigTest(6, 6, 10, 10);
         d.bigTest(10, 10, 20, 20);
    }
    
    public void initUI(int w, int h) {
        frame = new JFrame("Chess time!");
        JPanel pane = new JPanel();
        b = new Board(w, h);
        b.initUI();
        pane.add(b);
        pane.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(209, 209, 209), Color.BLACK));
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        c = new Controller(b);
        c.initUI();
        
        JButton button = new JButton("RESET");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                initUI(w, h);
            }
        });
        
        c.add(button);
        
        JButton button2 = new JButton("INFO");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(b);
            }
        });

        c.add(button2);

        frame.getContentPane().add(c, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }
    
    public void initUI(int w, int h, String start) {
        frame = new JFrame("Chess time!");
        JPanel pane = new JPanel();
        b = new Board(w, h, start);
        pane.add(b);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.pack();
        c = new Controller(b);
        frame.getContentPane().add(c, BorderLayout.SOUTH);
        c.start();
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // tests all starting positions on a board of W x H dimensions
    public synchronized void Test(int w, int h) {
        ArrayList<String> fails = new ArrayList<String>();
        
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                String current = "" + (char)(i + 65) + (j + 1);
                // System.out.println(current + " starting ...");
                initUI(w, h, current);                
                try {
                    //wait((int)(w * h * 2.5));
                    wait(10);
                }
                catch (Exception e) {
                    System.out.println("heck :(");
                }
                if (!c.isSuccess())
                    fails.add(current);
                frame.dispose();
            }
        if (fails.size() == w * h)
            System.out.println("impossible with board size : " + w + " x " + h);
        else if(fails.size() == 0)
            System.out.println("perfect with board size : "+ w + " x " + h + "!");
            // else  System.out.println(fails + " fail with board size : " + w + " x " + h);

    }
    // tests all starting positions on boards in range from w1 x h1 -> w2 x h2
    public void bigTest(int w1, int h1, int w2, int h2) {
        for (int w = w1; w <= w2; w++)
            for (int h = h1; h <= h2; h++)
                Test(w, h);
        System.out.println("done!");
    }
}