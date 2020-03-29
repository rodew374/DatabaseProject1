package databaseproject1;

import javax.swing.*;

public class Test {
    private JPanel contentPane;
    private JPanel inputField;
    private JTextField input;
    private JPanel insertButton;
    private JButton insert;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setContentPane(new Test().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
