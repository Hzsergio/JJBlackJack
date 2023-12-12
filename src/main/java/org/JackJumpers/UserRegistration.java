package org.JackJumpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The UserRegistration class provides methods for user registration and username validation.
 */
public class UserRegistration {

    /**
     * Registers a new user by adding their username and password to the database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return True if the registration is successful; false otherwise.
     */
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
            System.err.println("Failed to add user: " + username);
            return false;
        }
    }

    /**
     * Checks if a username is already taken by querying the database.
     *
     * @param username The username to be checked.
     * @return True if the username is already taken; false otherwise.
     */
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
            System.err.println("Error checking username: " + username);
        }
        return false;
    }
}
