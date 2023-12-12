package org.JackJumpers;

import javax.swing.*;
import java.awt.*;

/**
 * The SignUpWindow class represents the window for user signup.
 */
public class SignUpWindow extends CustomIcon {

    private final JFrame frame;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    /**
     * Constructs a SignUpWindow object.
     *
     * @param previousFrame The frame of the previous window.
     */
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

    /**
     * Performs user signup by retrieving input values, attempting registration,
     * and displaying appropriate messages.
     */
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

    /**
     * Opens the main menu window.
     */
    private void openMainMenu() {
        SwingUtilities.invokeLater(Menu::new);
    }

    /**
     * Closes the current window and shows the previous frame (menu).
     *
     * @param previousFrame The frame of the previous window.
     */
    private void goBack(JFrame previousFrame) {
        frame.dispose(); // Close the current window
        previousFrame.setVisible(true); // Show the previous frame (menu)
    }
}
