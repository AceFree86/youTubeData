package sample;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConector {

    public static Connection connect() {
        Connection connect;

        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:ytd.db");
            return connect;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

    }
}
