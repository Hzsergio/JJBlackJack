package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JFrame frame;
    private JButton loginButton;
    private JButton leaderboardsButton;
    private JButton createAccountButton;

    public Menu() {
        frame = new JFrame("Main Menu");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocationRelativeTo(null);

        loginButton = new JButton("Login");
        leaderboardsButton = new JButton(("Leaderboards"));
        createAccountButton = new JButton("Create Account");

        // Add action listeners to the buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginWindow();
            }
        });

        leaderboardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement leaderboards logic
                JOptionPane.showMessageDialog(frame, "Leaderboards feature coming soon!");
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignupWindow();
            }
        });

        frame.add(loginButton);
        frame.add(leaderboardsButton);
        frame.add(createAccountButton);

        frame.setVisible(true);
    }

    private void openLoginWindow() {
        frame.dispose(); // Close the main menu window

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthenticationWindow(); // Assuming AuthenticationWindow is for login
            }
        });
    }

    private void openSignupWindow() {
        frame.dispose(); // Close the main menu window

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignUpWindow(); // Create a new window for signup
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        });
    }
}
