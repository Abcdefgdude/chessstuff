import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Controller extends JPanel {
    
    private Board b;
    MyComboBox chooser;

    public Controller(Board bb) {
        b = bb;
        setSize(b.getWidth(), 150);
        JPanel main = new JPanel();
        add(main);
        
        main.setLayout(new FlowLayout());
        main.setPreferredSize(new Dimension(b.getWidth(), 150));
        main.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(209, 209, 209), Color.BLACK));
        
        JLabel moveNumber = new JLabel();
        chooser = new MyComboBox(bb);
        JButton button = new JButton("next move");
        Timer test = new Timer(100, new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                go();
                b.getPossibleMoves();
            }
        });
        
        button.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                go();
                b.getPossibleMoves();
                test.start();
            }
        });
        
        main.add(moveNumber);
        main.add(chooser);
        main.add(button);


    }

    public void go() {
        String next = chooser.go(b.getPossibleMoves());
        System.out.println(next);
        b.goTo(next);
    }
}