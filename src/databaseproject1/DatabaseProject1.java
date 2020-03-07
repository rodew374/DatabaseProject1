package databaseproject1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProject1 {

    private static Connection con;

    public static void main(String[] args) throws SQLException {
        DatabaseProject1 pro = new DatabaseProject1();
        pro.createConnection();
        printData("users");
        printData("users2");

    }

    static void printData(String table) throws SQLException {

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
        ResultSetMetaData md = rs.getMetaData();
        if (md.getColumnCount()==1) {
            while(rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);

            }

        } else {
            while(rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name + " age = " + age);

            }

        }

        stmt.close();
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
}
