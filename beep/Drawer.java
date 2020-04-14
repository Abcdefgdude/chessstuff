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
    
    
    /** 
     * @param args
     */
    public static void main(final String[] args) {
        Drawer d = new Drawer();
        // d.Test(6, 6);
        d.createAndShowGUI(6, 4);
        // d.bigRNGTest(6, 3, 6, 3);
        // d.bigTest(3, 3, 5, 5);
        // d.RNGTest(5, 3, 5, 3, "A001", 100);
        // d.RNGTestSquare(150, 200, 30);
    }

    
    /** 
     * @param w
     * @param h
     */
    public void createAndShowGUI(int w, int h) {
        
        width = w;
        height = h;
        
        frame = new JFrame("Chess time!");
        JPanel boardPane = new JPanel();
        JPanel controlPanel = new JPanel();
        JPanel buttonPanel = createButtonPanel();
        boardPane.setBackground(Color.WHITE);
        b = new Board(w, h, "A002");
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
    
    /** lightweight memory version with no visual elements
     * @see #createAndShowGUI
     */
    public void initQuietTest(int w, int h, String start) {
        this.b = new Board(w, h, start);
        this.c = new Controller(this.b);
        this.c.start();
    }
    
    
    /** 
     * @param w
     * @param h
     * @return ArrayList<String>
     */
    /* tests all starting positions on a board of W x H dimensions */
    public synchronized ArrayList<String> Test(int w, int h) {
        ArrayList<String> fails = new ArrayList<String>();
        
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++) {
                int y = j + 1;
                String current = "" + (char)(i + 65) + (y >= 100 ? "" + y : y >= 10 ? "0" + y : "00" + y);
                Drawer draw = new Drawer();
                draw.initQuietTest(w, h, current);                
                try {
                    // wait(5);
                }
                catch (Exception e) {
                    System.out.println("heck :(");
                }
                if (!draw.c.isSuccess())
                    fails.add(current);
                // else if (draw.b.isClosed())
                    // System.out.println("Closed tour on board size : " + w + " x " + h + " and starting position : " + current);
            }
        if (fails.size() == w * h)
            System.out.println("impossible with board size : " + w + " x " + h);
        else if(fails.size() == 0)
            System.out.println("perfect with board size : "+ w + " x " + h + "!");
        // else  System.out.println(fails + " fail with board size : " + w + " x " + h);
        return fails;
    }
    
    
    /** 
     * @param w1
     * @param h1
     * @param w2
     * @param h2
     */
    /* tests all starting positions on boards in range from w1 x h1 -> w2 x h2 */
    public void bigTest(int w1, int h1, int w2, int h2) {
        for (int w = w1; w <= w2; w++)
            for (int h = h1; h <= h2; h++)
                Test(w, h);
        System.out.println("done!");
    }
    
    /** Tests all starting positions on boards in range from w1 x h1 -> w2 x h2
     *  Uses random algorithm, tests each board 10 times */  
    public void bigRNGTest(int w1, int h1, int w2, int h2) {
        ArrayList<String> data = new ArrayList<String>();
        String percents = "";
        String boards = "";
        int successPercent = 0;
        int total = 0;
        for (int w = w1; w <= w2; w++)
            for (int h = h1; h <= h2; h++) {
                for (int i = 0; i < 10; i++) {
                    successPercent = 100 - (int)((Test(w, h).size() / (double)(w * h)) * 100);
                    // System.out.println(successPercent);
                    if (successPercent == 100) {
                        System.out.println("skipping...");
                        total = 100 * 10;
                        break;
                    }
                    total += successPercent;
                    System.out.println("total : " + total);
                }
                // System.out.println(total);
                total /= 10;
                data.add("" + total + "% percent of trials at board " + w + " x " + h + " were successful");
                percents += total + "\n";
                boards += w + " x " + h + "\n"; 
                total = 0;  

            }
        System.out.println(data);
        Writer w = new Writer();
        w.toTxt(percents + boards);
        System.out.println("done!");
    }

    /** Tests starting position start on boards in range from w1 x h1 -> w2 x h2
     *  Uses random algorithm, tests each board t times */  
    public void RNGTest(int w1, int h1, int w2, int h2, String start, int t) {
        ArrayList<String> data = new ArrayList<String>();
        String percents = "";
        String boards = "";
        int total = 0;
        for (int w = w1; w <= w2; w++)
            for (int h = h1; h <= h2; h++) {
                for (int i = 0; i < t; i++) {
                    total += TestStart(w, h, start) ? 100 : 0;
                    // System.out.println("total : " + total);
                }
                System.out.println("" + total + "% percent of trials at board " + w + " x " + h + " were successful");
                total /= t;
                data.add("" + total + "% percent of trials at board " + w + " x " + h + " were successful \n");
                percents += total + "\n";
                boards += w + " x " + h + "\n"; 
                total = 0;  
            }
        System.out.println(data);
        Writer w = new Writer();
        w.toTxt(percents + boards);
        System.out.println("done!");
    }
    
    /** Tests random start position in range from w1 x w1 -> w2 x w2 
     *  Uses random algorithm, tests each board t times */  
    public void RNGTestSquare(int w1, int w2, int t) {
        String percents = "";
        double total = 0;
        for (int w = w1; w <= w2; w +=2) {
            for (int i = 0; i < t; i++) {
                int y = (int)(Math.random() * w + 1);
                // System.out.println("y : " + y);
                String strY = y >= 100 ? "" + y : y >= 10 ? "0" + y : "00" + y; 
                String strX = "" + (char)((int)(Math.random() * width + 65)); 
                String start = strX + strY;
                // System.out.println("start : " + start);
                total += TestStart(w, w, start) ? 100 : 0;

            }
            total /= t;
            System.out.println("" + total + "% percent of trials at board " + w + " x " + w + " were successful");
            percents += total + "\n";
            total = 0;  
        }
        Writer writer = new Writer();
        writer.toTxt(percents);
        System.out.println("done!");
    }


    /**
     * Tests knight tour on board size wxh from starting position start
     * @param w board width
     * @param h board height
     * @param start starting position, in "A01" format
     * @return if starting position was successful
     */
    public boolean TestStart(int w, int h, String start) {
        Drawer draw = new Drawer();
        draw.initQuietTest(w, h, start);                
        return draw.c.isSuccess();
        
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
                /* ArrayList<String> highlight = Test(width, height);
                b.highlightTiles(highlight, new Color(201, 72, 62)); */
                // b.highlightWeights();
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