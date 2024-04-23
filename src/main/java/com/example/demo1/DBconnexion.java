package com.example.demo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnexion {
    static String url = "jdbc:mysql://localhost:3306/crud?useSSL=false";
    static String user = "root";
    static String password = "";
    static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getCon() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC driver class not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error establishing connection to the database", e);
        }
        return con;
    }
}
