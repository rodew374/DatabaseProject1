package databaseproject1;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProject1 {

    private static Connection con;

    public static void main(String[] args) throws SQLException {
        DatabaseProject1 pro;

        pro = new DatabaseProject1();
        pro.createConnection();
        pro.printData("users");
        pro.printData("users2");

//      pro.createTable("users3");
//      pro.addBatch();
        pro.callableExample();
        pro.olderThan(50);

    }

    private void addBatch() {
        PreparedStatement stmt;
        int [] ar;

        try {
            stmt = con.prepareStatement("INSERT INTO USERS2 VALUES(?, ?)");

            stmt.setString(1, "EDGAR");
            stmt.setInt(2, 4);
            stmt.addBatch();

            stmt.setString(1, "FREDERICK");
            stmt.setInt(2, 62);
            stmt.addBatch();

            stmt.setString(1, "GASTON");
            stmt.setInt(2, 95);
            stmt.addBatch();

            stmt.setString(1, "ISAAC");
            stmt.setInt(2, 79);
            stmt.addBatch();

            stmt.setString(1, "JACQUELINE");
            stmt.setInt(2, 80);
            stmt.addBatch();

/*
//          Alternatively, we can use a Statement instead of Prepared Statement
            Statement stmt = con.createStatement();
            stmt.addBatch("INSERT INTO USERS2 VALUES('User1', 25)");
*/

            ar = stmt.executeBatch();

            for (int i : ar) {
                System.out.println(i);

            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
/*  This method prints the name of all users in the table 'USERS2'
 */
    private void callableExample() {
        CallableStatement stmt;
        Boolean hasResult;
        ResultSet rs;

        try {
            stmt = con.prepareCall("{call simple()}");
            hasResult = stmt.execute();

            if(hasResult) {
                rs = stmt.getResultSet();

                while(rs.next()) {
                    System.out.println(rs.getString("name"));

                }
            }

            System.out.println();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
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

    private void createTable(String tableName) {
        Statement stmt;
        String q;

        q = "CREATE TABLE "
                + tableName
                + "("
                + "name varchar(100),"
                + "age int,"
                + "salary float"
                + ");";
        try {
            stmt = con.createStatement();
            stmt.execute(q);

            System.out.println("Table Sucessfully Created");
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void olderThan(int age) {
        Boolean hasResult;
        CallableStatement stmt;
        ResultSet rs;

        try {
            stmt = con.prepareCall("{call olderThan(?)}");
            stmt.setInt(1, age);
            hasResult = stmt.execute();

            if(hasResult) {
                rs = stmt.getResultSet();

                while(rs.next()) {
                    System.out.println(rs.getString("name"));

                }
            }

            System.out.println();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printData(String table) throws SQLException {
        ResultSet rs;
        ResultSetMetaData md;
        Statement stmt;

        stmt = con.createStatement();
        rs = stmt.executeQuery("SELECT * FROM " + table);
        md = rs.getMetaData();
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

        System.out.println();
        stmt.close();
    }
}
