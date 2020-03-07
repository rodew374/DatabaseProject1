package databaseproject1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProject1 {

    public static void main(String[] args)  {
        DatabaseProject1 pro = new DatabaseProject1();
        pro.createConnection();

    }

    void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "7@AyCXG3");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users/* WHERE name like 'A%'*/");
            while(rs.next()) {
                String name = rs.getString("name");
                System.out.println(name);

            }

            System.out.println("Database Connection Success");

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseProject1.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
}
