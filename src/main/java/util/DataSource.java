package util;
import java.sql.Connection;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private  String url="jdbc:mysql://localhost:3306/ahc";
        private String login="root";
    private String pwd="";

private Connection connection;

private static DataSource instance;


    public class ProjetService {
        private Connection connection;

        public ProjetService(Connection connection) {
            this.connection = connection;
        }

        // Other methods in ProjetService class...
    }
    private DataSource() {
        try {
            connection= DriverManager.getConnection(url, login ,pwd);
            System.out.println("connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static DataSource getInstance(){
        if(instance==null)
            instance = new DataSource();
        return instance ;
    }

    public Connection getConnection() {
        return connection;
    }
}

