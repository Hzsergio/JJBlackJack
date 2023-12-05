package org.JackJumpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData {
    private int wins;
    private int losses;
    private static int points;

    private final String user;

    public UserData(int wins, int losses, int points, String username) {
        this.wins = wins;
        this.losses = losses;
        UserData.points = points;
        this.user = username;
    }

    public static UserData getUserData(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT username, wins, losses, points FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String user = resultSet.getString("username");
                        int wins = resultSet.getInt("wins");
                        int losses = resultSet.getInt("losses");
                        int points = resultSet.getInt("points");

                        return new UserData(wins, losses, points, user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if user data retrieval fails
    }

    public static boolean canBet(int betAmount) {
        return points >= betAmount;
    }


    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getPoints() {
        return points;
    }

    public String getUser() {
        return user;
    }

    public void incrementWin() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public void updateStats() {
        if (points <= 0) points = 1000;
        UserStatistics.updateUserStats(user, wins, losses, points);

    }

    public void placeBet(int betAmount) {
        points -= betAmount;

    }

    public void winningBet(int currentBet) {
        points = points + (currentBet * 2);
    }

    public void tieBet(int currentBet) {
        points += currentBet;
    }
}