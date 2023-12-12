package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a leaderboard displaying user statistics.
 */
public class LeaderBoard extends CustomIcon {

    /**
     * Constructs a Leaderboard JFrame displaying user statistics.
     */
    public LeaderBoard() {
        setTitle("Leaderboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Retrieve player data from the database
        List<UserData> players = retrievePlayerData();

        // Sort players based on points in descending order
        Collections.sort(players, Comparator.comparingInt(UserData::getPoints).reversed());

        // Create a table to display the leaderboard
        String[] columnNames = {"Rank", "Username", "Wins", "Losses", "Points"};
        Object[][] data = new Object[players.size()][5];

        // Populate the data array with player information
        for (int i = 0; i < players.size(); i++) {
            UserData player = players.get(i);
            data[i][0] = i + 1; // Rank
            data[i][1] = player.getUser();
            data[i][2] = player.getWins();
            data[i][3] = player.getLosses();
            data[i][4] = player.getPoints();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Retrieves player data from the database.
     *
     * @return A list of UserData representing player statistics.
     */
    private List<UserData> retrievePlayerData() {
        List<UserData> players = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT username, wins, losses, points FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Process each row in the result set
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int wins = resultSet.getInt("wins");
                    int losses = resultSet.getInt("losses");
                    int points = resultSet.getInt("points");

                    UserData player = new UserData(wins, losses, points, username);
                    players.add(player);
                }
            }
        } catch (SQLException e) {
            System.err.println("Unable to retrieve player data");
        }

        return players;
    }

    /**
     * Main method to create and display the Leaderboard.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LeaderBoard::new);
    }
}
