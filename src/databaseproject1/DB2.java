package databaseproject1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB2 extends javax.swing.JFrame {

    private JTextField input;
    private JButton insertPleaseButton;
    private JPanel DB2;
    Connection con;

    public DB2() {
        createConnection();

        insertPleaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = input.getText();

                try {
                    Statement stmt = con.createStatement();
                    String dbop = "INSERT INTO USERS VALUES('" + name + "')";

                    stmt.execute(dbop);
                    stmt.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();

                }
            }
        });
    }

    void createConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "7@AyCXG3");

            System.out.println("Database Connection Success");

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DB2");
        frame.setContentPane(new DB2().DB2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
}
