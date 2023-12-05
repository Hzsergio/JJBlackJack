package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow {
    private final JFrame frame;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton signupButton;

    public SignUpWindow() {
        frame = new JFrame("Signup");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocationRelativeTo(null);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        signupButton = new JButton("Signup");

        // Add action listener to the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSignup();
            }
        });

        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("Password:"));
        frame.add(passwordField);
        frame.add(new JLabel()); // Empty label for spacing
        frame.add(signupButton);

        frame.setVisible(true);
    }

    private void performSignup() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // TODO: Call the addUser method to register the user
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu(); // Open the main menu window
            }
        });
    }
}
