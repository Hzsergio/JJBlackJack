package org.JackJumpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserData class represents the data associated with a user, including wins, losses, and points.
 */
public class UserData {
    private int wins;
    private int losses;
    private int points;
    private final String user;

    /**
     * Constructs a UserData object with the specified values.
     *
     * @param wins     The number of wins for the user.
     * @param losses   The number of losses for the user.
     * @param points   The number of points for the user.
     * @param username The username of the user.
     */
    public UserData(int wins, int losses, int points, String username) {
        this.wins = wins;
        this.losses = losses;
        this.points = points;
        this.user = username;
    }

    /**
     * Retrieves user data from the database based on the provided username.
     *
     * @param username The username of the user to retrieve data for.
     * @return A UserData object representing the user's data, or null if retrieval fails.
     */
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
            System.err.println("Failed to get data for user: " + username);
        }
        return null; // Return null if user data retrieval fails
    }

    /**
     * Checks if the user has sufficient points to place a bet.
     *
     * @param betAmount The amount of points to bet.
     * @return True if the user can place the bet; false otherwise.
     */
    public boolean canBet(int betAmount) {
        return points >= betAmount;
    }

    /**
     * Gets the number of wins for the user.
     *
     * @return The number of wins.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Gets the number of losses for the user.
     *
     * @return The number of losses.
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Gets the number of points for the user.
     *
     * @return The number of points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUser() {
        return user;
    }

    /**
     * Increments the number of wins for the user.
     */
    public void incrementWin() {
        wins++;
    }

    /**
     * Increments the number of losses for the user.
     */
    public void incrementLosses() {
        losses++;
    }

    /**
     * Updates the user's statistics in the database.
     * If the user's points are non-positive, sets them to 100.
     */
    public void updateStats() {
        if (points <= 0) points = 100;
        UserStatistics.updateUserStats(user, wins, losses, points);
    }

    /**
     * Decreases the user's points by the specified bet amount.
     *
     * @param betAmount The amount of points to bet.
     */
    public void placeBet(int betAmount) {
        points -= betAmount;
    }

    /**
     * Increases the user's points based on a winning bet.
     *
     * @param currentBet The amount of the current bet.
     */
    public void winningBet(int currentBet) {
        points += (currentBet * 2);
    }

    /**
     * Increases the user's points based on a tie bet.
     *
     * @param currentBet The amount of the current bet.
     */
    public void tieBet(int currentBet) {
        points += currentBet;
    }
}
