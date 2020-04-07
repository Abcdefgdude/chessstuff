import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class Controller extends JPanel {
    
    private Board b;
    
    public Controller(Board bb) {
        b = bb;
        setSize(b.getWidth(), 150);
        JPanel main = new JPanel();
        add(main);
        
        main.setLayout(new FlowLayout());
        main.setPreferredSize(new Dimension(b.getWidth(), 150));
        main.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, new Color(209, 209, 209), Color.BLACK));
        JLabel moveNumber = new JLabel();
        JComboBox<String> chooser = new JComboBox<String>();
        chooser.addItem("test");
        //chooser.
        
        main.add(moveNumber);
        main.add(chooser);
        
    } 
}