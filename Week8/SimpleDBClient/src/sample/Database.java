package sample;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Database() {

    }

    public static Connection getDBConnection() throws SQLException {
        Connection connection = null;


        String conn_url = "jdbc:mysql://localhost:3306/employees?user=root&password=";

        try {
            connection = DriverManager.getConnection(conn_url);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}