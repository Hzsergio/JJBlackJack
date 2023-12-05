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

        leaderboardsButton.addActionListener(e -> {
            // Implement leaderboards logic
            JOptionPane.showMessageDialog(frame, "Leaderboards feature coming soon!");
        });

        createAccountButton.addActionListener(e -> openSignupWindow());


    }

    private void openLoginWindow() {
        frame.dispose(); // Close the main menu window

        // Assuming AuthenticationWindow is for login
        SwingUtilities.invokeLater(AuthenticationWindow::new);
    }

    private void openSignupWindow() {
        frame.dispose(); // Close the main menu window

        // Create a new window for signup
        SwingUtilities.invokeLater(SignUpWindow::new);
    }

}
