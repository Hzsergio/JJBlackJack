package org.JackJumpers;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a login window for BlackJack.
 */
public class LoginWindow extends CustomIcon {
    private final JFrame frame;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton backButton;

    /**
     * Constructs a login window.
     *
     * @param previousFrame The frame to go back to.
     */
    public LoginWindow(JFrame previousFrame) {
        frame = new JFrame("BlackJack Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 2));
        setCustomIcon(frame);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(backButton);
        frame.setVisible(true);

        loginButton.addActionListener(e -> authenticateUser());
        backButton.addActionListener(e -> goBack(previousFrame));
    }

    /**
     * Authenticates the user based on the provided username and password.
     */
    private void authenticateUser() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (UserAuthentication.authenticateUser(username, password)) {
                loginButton.setEnabled(false);
                backButton.setEnabled(false);
                frame.setTitle("Logging in...");
                startGame(username);
                Timer delayTimer = new Timer(2000, e -> {
                    frame.dispose(); // Close the authentication window after the delay

                });

                delayTimer.setRepeats(false); // Execute only once
                delayTimer.start();
            } else {
                JOptionPane.showMessageDialog(frame, "Authentication failed. Please try again.");
                // Clear the password field after a failed attempt
                passwordField.setText("");
            }
        } catch (SQLException ex) {
            System.err.println("Authentication failed for user: " + username);
            // Handle database connection error
        } catch (URISyntaxException | IOException | InterruptedException | UnsupportedAudioFileException |
                 LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Navigates back to the previous frame.
     *
     * @param previousFrame The frame to go back to.
     */
    private void goBack(JFrame previousFrame) {
        frame.dispose(); // Close the current window
        previousFrame.setVisible(true); // Show the previous frame (menu)
    }

    /**
     * Starts the BlackJack game.
     *
     * @param username The username of the player.
     * @throws URISyntaxException         If the URI is invalid.
     * @throws IOException                If an I/O error occurs.
     * @throws InterruptedException       If the thread is interrupted.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws LineUnavailableException    If a line cannot be opened because it is unavailable.
     */
    public void startGame(String username) throws URISyntaxException, IOException, InterruptedException,
            UnsupportedAudioFileException, LineUnavailableException {
        BlackJackGame game = new BlackJackGame(username);
        BlackJackUI ui = new BlackJackUI(game);
        ui.createUI();
        ui.setVisible(true);
    }

    /**
     * Main method to create and display the login window.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame previousFrame = new JFrame();
            new LoginWindow(previousFrame);
        });
    }

    /**
     * Getter for the login window frame.
     *
     * @return The login window frame.
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Getter for the username text field.
     *
     * @return The username text field.
     */
    public JTextField getUsernameField() {
        return usernameField;
    }

    /**
     * Getter for the password field.
     *
     * @return The password field.
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Getter for the login button.
     *
     * @return The login button.
     */
    public JButton getLoginButton() {
        return loginButton;
    }
}
