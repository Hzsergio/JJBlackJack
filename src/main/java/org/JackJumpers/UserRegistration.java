package org.JackJumpers;

import java.sql.*;

public class UserRegistration {
    //demo
    public static boolean addUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if the username is already taken
            if (isUsernameTaken(username)) {
                System.out.println("Username already taken. Please choose a different username.");
                return false;
            }

            // Insert the new user into the database
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("User successfully registered.");

                    // Retrieve the auto-generated id
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("Generated ID: " + generatedId);
                    }
                    return true;
                } else {
                    System.out.println("User registration failed.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isUsernameTaken(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkQuery = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
