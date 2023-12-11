package org.JackJumpers;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginWindow extends CustomIcon{
    private final JFrame frame;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    private JButton loginButton;

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


        JButton backButton = new JButton("Back");

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

    private void authenticateUser() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (UserAuthentication.authenticateUser(username, password)) {
                frame.dispose(); // Close the authentication window
                startGame(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Authentication failed. Please try again.");
                // Clear the password field after a failed attempt
                passwordField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection error
        } catch (URISyntaxException | IOException | InterruptedException | UnsupportedAudioFileException |
                 LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private void goBack(JFrame previousFrame) {
        frame.dispose(); // Close the current window
        previousFrame.setVisible(true); // Show the previous frame (menu)
    }

    public void startGame(String username) throws URISyntaxException, IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
        BlackJackGame game = new BlackJackGame(username);
        BlackJackUI ui = new BlackJackUI(game);
        ui.createUI();
        ui.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame previousFrame = new JFrame(); // You can customize the previous frame as needed
            new LoginWindow(previousFrame);
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

}