package org.JackJumpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserAuthentication class provides methods for authenticating user credentials.
 */
public class UserAuthentication {

    /**
     * Authenticates a user by checking the provided username and password against the stored credentials in the database.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return True if the authentication is successful; false otherwise.
     */
    public static boolean authenticateUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Retrieve the password from the database
                    String storedPassword = resultSet.getString("password");

                    // Compare the provided password with the stored password
                    return password.equals(storedPassword);
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to authenticate" + username);
        }
        return false;
    }
}
