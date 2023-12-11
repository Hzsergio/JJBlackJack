package org.JackJumpers;

import javax.swing.*;
import java.awt.*;

public class SignUpWindow extends CustomIcon{
    private final JFrame frame;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public SignUpWindow(JFrame previousFrame) {
        frame = new JFrame("Signup");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocationRelativeTo(null);
        setCustomIcon(frame);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton signupButton = new JButton("Signup");
        JButton backButton = new JButton("Back");

        // Add action listener to the signup button

        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("Password:"));
        frame.add(passwordField);
        frame.add(signupButton);
        frame.add(backButton);

        frame.setVisible(true);
        backButton.addActionListener(e -> goBack(previousFrame));
        signupButton.addActionListener(e -> performSignup());


    }

    private void performSignup() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        boolean signupSuccessful = UserRegistration.addUser(username, password);

        if (signupSuccessful) {
            JOptionPane.showMessageDialog(frame, "Signup successful! You can now log in.");
            frame.dispose(); // Close the signup window
            openMainMenu(); // Open the main menu window
        } else {
            JOptionPane.showMessageDialog(frame, "Signup failed. Please try again.");
        }
    }

    private void openMainMenu() {
        // Open the main menu window
        SwingUtilities.invokeLater(Menu::new);
    }
    private void goBack(JFrame previousFrame) {
        frame.dispose(); // Close the current window
        previousFrame.setVisible(true); // Show the previous frame (menu)
    }
}
