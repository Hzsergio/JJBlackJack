package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticationWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AuthenticationWindow() {

        frame = new JFrame("BlackJack Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.setVisible(true);
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
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame(String username) throws URISyntaxException, IOException, InterruptedException {
        // Assuming you have a method to start the game with the authenticated user
        BlackJackGame game = new BlackJackGame(username);
        BlackJackUI ui = new BlackJackUI(game);
        ui.createUI();
        ui.setVisible(true);
        BlackJackGame.startBet();
    }
}