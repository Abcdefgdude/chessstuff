import java.awt.*;
import java.util.Scanner;

import javax.swing.*;

@SuppressWarnings("serial")
class Drawer extends JPanel {
    Board b;
    public static void main(final String[] args) {

        final JFrame frame = new JFrame("Chess time!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new Board(), BorderLayout.CENTER);
        frame.pack();

        frame.setVisible(true);
        frame.setSize(600, 600);

    }

    public void initBoard(final int n, final int m) {
        b = new Board(n, m);
    }

    public void initBoard() {
        final Scanner key = new Scanner(System.in);
        System.out.println("How many rows?");
        final int n = key.nextInt();
        System.out.println("How many columns?");
        final int m = key.nextInt();
        b = new Board(n, m);
        key.close();

    }
    

}