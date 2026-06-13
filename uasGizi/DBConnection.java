package uasGizi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection conn;
    public static final String USER = "root";
    public static final String PASS = "";
    public static final String DB_NAME = "pemantauan_gizi";

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/" + DB_NAME, USER, PASS
            );
            System.out.println("Connection Established");
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
        return conn;
    }
}