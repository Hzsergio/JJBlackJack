package org.JackJumpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserStatistics {
    public static void updateUserStats(String username, int wins, int losses, int points) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE users SET wins = ?, losses = ?, points = ? WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, wins);
                preparedStatement.setInt(2, losses);
                preparedStatement.setInt(3, points);
                preparedStatement.setString(4, username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

