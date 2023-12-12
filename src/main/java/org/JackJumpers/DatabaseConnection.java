package org.JackJumpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a connection to the PostgreSQL database.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://blackjackjj-db.clboclk3becx.us-west-1.rds.amazonaws.com:5432/blackjackdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dbpassword";

    /**
     * Gets a connection to the PostgreSQL database.
     *
     * @return A connection to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
