package org.JackJumpers;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class BetDialogTest {
    @Test //Testing if the bet button is working correctly
    public void testBetButtonListener() {
        BetDialog betDialog = new BetDialog(null, 100);
        BetDialog.BetButtonListener listener = betDialog.new BetButtonListener(50);

        // Simulate button click
        listener.actionPerformed(null);

        assertEquals(50, betDialog.getBetAmount());
    }

    @Test//Testing the ALL IN button
    public void testAllInButton() {
        BetDialog betDialog = new BetDialog(null, 50);

        // Simulate clicking the "All In" button
        JButton allInButton = (JButton) betDialog.getButtonPanel().getComponent(betDialog.getButtonPanel().getComponentCount() - 1);
        allInButton.doClick();

        // Verify that betAmount is set to total points
        assertEquals(100, betDialog.getBetAmount());
    }

    @Test//Testing that the correct bet is being returned
    public void testGetBetAmount() {
        // Create a JFrame (parent frame) for the dialog
        JFrame parentFrame = new JFrame();
        parentFrame.setSize(400, 400);
        parentFrame.setLocationRelativeTo(null);

        // Set an initial bet amount
        int initialBetAmount = 50;

        // Create a BetDialog with the initial bet amount
        BetDialog betDialog = new BetDialog(parentFrame, initialBetAmount);

        // Set the bet amount in the dialog to a new value
        int newBetAmount = 75;
        betDialog.getBetTextField().setText(Integer.toString(newBetAmount));

        // Simulate closing the dialog
        SwingUtilities.invokeLater(betDialog::dispose);

        // Call the getBetAmount method after the dialog is closed
        int returnedBetAmount = BetDialog.getBetAmount(parentFrame, initialBetAmount);

        // Verify that the returned bet amount matches the expected value
        assertEquals(newBetAmount, returnedBetAmount);
    }

}