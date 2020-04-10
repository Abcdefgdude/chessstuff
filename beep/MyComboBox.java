import java.awt.event.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyComboBox extends JComboBox<Algorithm> {
    private Algorithm current;
    private Board b;
    public MyComboBox(Board bb) {
        b = bb;
        Algorithm algo1 = new Warnsdorff(b);
        addItem(algo1);
        addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                current = (Algorithm) getSelectedItem();
            }
        });
        setSelectedItem(algo1);
    
    }

    
    public String go(ArrayList<String> moves) {
        return current.getNextMove(moves);
    }
}

