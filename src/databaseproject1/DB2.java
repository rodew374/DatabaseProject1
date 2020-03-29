package databaseproject1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB2 extends javax.swing.JFrame {

    private JTextField input;
    private JButton insertPleaseButton;
    private JPanel DB2;
    private JPanel namePane;
    private JPanel insertPane;
    private JTextField ageInput;
    private JPanel agePane;
    private JTable table;
    private JScrollPane dataPane;
    private JButton refreshButton;
    private JPanel refreshPane;
    Connection con;

    public DB2() {
        createConnection();

        insertPleaseButton.addActionListener(this::insert);
        refreshButton.addActionListener(this::refresh);
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
        frame.pack();
        frame.setVisible(true);
    }

    private void insert(ActionEvent e) {
        String name = input.getText();
        int age = Integer.parseInt(ageInput.getText());

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO USERS2 VALUES(?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, age);

            stmt.execute();
            System.out.println("Insertion Completed");
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    private void refresh(ActionEvent e) {
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(new Object[]{"Name", "Age"});

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS2");

            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");

                System.out.println(name + " age = " + age);

                tableModel.addRow(new Object[]{name, age});

            }

            table.setModel(tableModel);
            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
}
