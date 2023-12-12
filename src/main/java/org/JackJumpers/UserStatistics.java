package org.JackJumpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class for updating user statistics in the database.
 */
public class UserStatistics {

    /**
     * Updates the user statistics in the database.
     *
     * @param username The username of the user whose statistics are to be updated.
     * @param wins     The new number of wins for the user.
     * @param losses   The new number of losses for the user.
     * @param points   The new points for the user.
     */
    public static void updateUserStats(String username, int wins, int losses, int points) {
        // Try-with-resources ensures that the Connection and PreparedStatement are closed automatically
        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL query to update user statistics based on the provided parameters
            String query = "UPDATE users SET wins = ?, losses = ?, points = ? WHERE username = ?";
            // Try-with-resources ensures that the PreparedStatement is closed automatically
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set parameters in the PreparedStatement
                preparedStatement.setInt(1, wins);
                preparedStatement.setInt(2, losses);
                preparedStatement.setInt(3, points);
                preparedStatement.setString(4, username);
                // Execute the update query
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Print an error message if there's an issue updating user statistics
            System.err.println("Failed to update user statistics for user: " + username);
        }
    }
}
