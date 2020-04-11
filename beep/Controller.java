import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Controller extends JPanel {
    
    private Board b;
    MyComboBox chooser;
    Timer test;
    JLabel moveNumber;
    JPanel main;

    public Controller(Board bb) {
        b = bb;
        chooser = new MyComboBox(bb);
    }

    public void go() {
        String next = chooser.go(b.getPossibleMoves());
        if (!next.equals(""))
            b.goTo(next);
        else {
            test.stop();
        }
        b.clear();
    }
    
    public void auto() {
        String next = chooser.go(b.getPossibleMoves());
        while (!next.equals("")) {
            b.goTo(next);
            next = chooser.go(b.getPossibleMoves());
        }
    }

    public boolean isSuccess() {
        return b.completed();
    }
    
    public void start() {
        auto();
    }
    
    public void initUI() {
        JPanel main = new JPanel();
        add(main);
        int i = 0;
        
        JButton button1 = new JButton("auto-complete");
        JButton button2 = new JButton("next move");
        JButton button3 = new JButton("STOP!");
        
        setSize(b.getWidth(), 150);
        main.setLayout(new FlowLayout());
        main.setPreferredSize(new Dimension(b.getWidth(), 150));
        main.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(209, 209, 209), Color.BLACK));
        
        moveNumber = new JLabel("" + 0);
        moveNumber.setText("" + i);
        
        test = new Timer(1, new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                go();
                // b.getPossibleMoves();
            }
        });
        
        button1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                go();
                b.getPossibleMoves();
                test.start();
            }
        });
        
        button2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                go();
                b.getPossibleMoves();
            }
        });
        
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                test.stop();
            }
        });

        main.add(button1);
        main.add(button2);
        main.add(button3);
        main.add(moveNumber);
        main.add(chooser);
    }
}

