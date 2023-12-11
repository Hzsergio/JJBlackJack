package org.JackJumpers;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


class LoginWindowTest {

    @Test
    public void testAuthenticationSuccess() {
        // Simulate user input and click the login buttonl
        LoginWindow loginWindow = new LoginWindow(new JFrame());
        loginWindow.getUsernameField().setText("user");
        loginWindow.getPasswordField().setText("test");
        loginWindow.getLoginButton().doClick(); // Simulate button click

        // Verify that the frame is disposed after successful authentication
        assertFalse(loginWindow.getFrame().isVisible());
    }

}