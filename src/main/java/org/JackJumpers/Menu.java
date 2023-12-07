package org.JackJumpers;

import javax.swing.*;
import java.awt.*;


public class Menu {
    private final JFrame frame;

    public Menu() {
        frame = new JFrame("Main Menu");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocationRelativeTo(null);

        JButton loginButton = new JButton("Login");
        JButton leaderboardsButton = new JButton(("Leaderboards"));
        JButton createAccountButton = new JButton("Create Account");

        frame.add(loginButton);
        frame.add(leaderboardsButton);
        frame.add(createAccountButton);

        frame.setVisible(true);

        // Add action listeners to the buttons
        loginButton.addActionListener(e -> openLoginWindow());

        leaderboardsButton.addActionListener(e -> openLeaderboard());

        createAccountButton.addActionListener(e -> openSignupWindow());


    }

    private void openLoginWindow() {
        frame.dispose(); // Close the main menu window

        // Assuming AuthenticationWindow is for login
//        SwingUtilities.invokeLater(LoginWindow::new);
        SwingUtilities.invokeLater(() -> {
            LoginWindow login = new LoginWindow(frame);
        });
    }

    private void openSignupWindow() {
        frame.dispose(); // Close the main menu window

        // Create a new window for signup
        SwingUtilities.invokeLater(() -> {
            SignUpWindow signup = new SignUpWindow(frame);
        });
    }
    private void openLeaderboard() {
        // Implement the logic to display the leaderboard
        // Instantiate the BlackjackLeaderboard class
        SwingUtilities.invokeLater(LeaderBoard::new);
    }

}
