package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mydatabase {
        private final String URL = "jdbc:mysql://localhost:3306/jobfloww";
    private final String USER = "root";
    private final String PSW = "";

    private Connection connection;
    private static Mydatabase instance;

    private Mydatabase()  {
        try {
            connection = DriverManager.getConnection(URL, USER,PSW);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Mydatabase getInstance(){
        if(instance == null)
            instance = new Mydatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
