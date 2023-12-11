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

public class LeaderBoard extends CustomIcon {

    public LeaderBoard() {
        setTitle("Leaderboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Retrieve player data from the database
        List<UserData> players = retrievePlayerData();

        // Sort players based on points (you can choose another criterion)
        Collections.sort(players, Comparator.comparingInt(UserData::getPoints).reversed());

        // Create a table to display the leaderboard
        String[] columnNames = {"Username", "Wins", "Losses", "Points"};
        Object[][] data = new Object[players.size()][4];

        for (int i = 0; i < players.size(); i++) {
            UserData player = players.get(i);
            data[i][0] = player.getUser();
            data[i][1] = player.getWins();
            data[i][2] = player.getLosses();
            data[i][3] = player.getPoints();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private List<UserData> retrievePlayerData() {
        List<UserData> players = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT username, wins, losses, points FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

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
            e.printStackTrace();
        }

        return players;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LeaderBoard::new);
    }
}
