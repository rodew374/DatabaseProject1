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
    private JButton editButton;
    private JPanel editPane;
    private JPanel spacerRight;
    private JPanel spacerLeft;
    private JPanel spacerTop;
    private JPanel editAgePane;
    private JPanel editNamePane;
    private JTextField editAge;
    private JTextField editName;
    private JPanel updatePane;
    private JButton updateButton;
    private JPanel deletePane;
    private JPanel spacerBottom;
    private JPanel spacerTableLower;
    private JPanel spacerTableUpper;
    private JButton deleteButton;
    Connection con;

    public DB2() {
        createConnection();

        deleteButton.addActionListener(this::delete);
        editButton.addActionListener(this::edit);
        insertPleaseButton.addActionListener(this::insert);
        refreshButton.addActionListener(this::refresh);
        updateButton.addActionListener(this::update);

        refreshButton.doClick();

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DB2");
        frame.setContentPane(new DB2().DB2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "7@AyCXG3");

            System.out.println("Database Connection Success\n");

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void delete(ActionEvent e) {
        int row = table.getSelectedRow();
        String name = table.getValueAt(row,0).toString();

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM USERS2 WHERE NAME=?");
            stmt.setString(1, name);

            stmt.executeUpdate();
            refreshButton.doClick();
            System.out.println("Deletion Completed\n");

            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    private void edit(ActionEvent e) {
        DefaultTableModel model;
        int row;
        String name, age;

        model = (DefaultTableModel) table.getModel();
        row = table.getSelectedRow();
        name = (String) model.getValueAt(row, 0);
        age = model.getValueAt(row, 1).toString();

        editName.setText(name);
        editAge.setText(age);

    }

    private void insert(ActionEvent e) {
        String name = input.getText();
        int age = Integer.parseInt(ageInput.getText());

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO USERS2 VALUES(?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, age);

            stmt.execute();
            refreshButton.doClick();
            System.out.println("Insertion Completed\n");

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

                tableModel.addRow(new Object[]{name, age});

            }

           table.setModel(tableModel);

            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

    private void update(ActionEvent e) {

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE USERS2 SET AGE = ? WHERE NAME = ?");
            stmt.setInt(1, Integer.parseInt(editAge.getText()));
            stmt.setString(2, editName.getText());

            stmt.executeUpdate();
            refreshButton.doClick();
            System.out.println("Update Completed\n");

            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }
}
