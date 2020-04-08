import java.awt.event.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MyComboBox extends JComboBox<Algorithm> {
    private Algorithm current;
    private Board b;
    public MyComboBox(Board bb) {
        b = bb;
        addItem(new Warnsdorff(b));
        addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                current = (Algorithm) getSelectedItem();
            }
        });        
    }
    
    public String go(ArrayList<String> moves) {
        return current.getNextMove(moves);
    }
}

