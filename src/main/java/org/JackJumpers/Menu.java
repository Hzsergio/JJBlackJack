package org.JackJumpers;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Menu {
    private final JFrame frame;

    public Menu() {
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel with a background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // Load the background image from a URL
                    URL imageURL = new URL("https://i.postimg.cc/76xPY18K/menuimage.png");
                    Image backgroundImage = ImageIO.read(imageURL);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Set layout for the background panel
        backgroundPanel.setLayout(null);

        // Create buttons
        JButton loginButton = new JButton("Login");
        JButton leaderboardsButton = new JButton("Leaderboards");
        JButton createAccountButton = new JButton("Create Account");

        // Set x-y coordinates for the buttons
        loginButton.setBounds(50, 450, 100, 30);
        leaderboardsButton.setBounds(200, 450, 150, 30);
        createAccountButton.setBounds(400, 450, 150, 30);

        // Add buttons to the background panel
        backgroundPanel.add(loginButton);
        backgroundPanel.add(leaderboardsButton);
        backgroundPanel.add(createAccountButton);

        // Set the background panel as the content pane
        frame.setContentPane(backgroundPanel);

        frame.setSize(550, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Add action listeners to the buttons
        loginButton.addActionListener(e -> openLoginWindow());
        leaderboardsButton.addActionListener(e -> openLeaderboard());
        createAccountButton.addActionListener(e -> openSignupWindow());
    }

    private void openLoginWindow() {
        frame.dispose(); // Close the main menu window

        // Assuming AuthenticationWindow is for login
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu());
    }
}
