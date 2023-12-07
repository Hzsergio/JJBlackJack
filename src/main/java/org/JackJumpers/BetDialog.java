package org.JackJumpers;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BetDialog extends JDialog {
    private int betAmount;
    private final JTextField betTextField;

    public BetDialog(JFrame parent, int initialBetAmount) {
        super(parent, "Place Your Bet:", true);
        setSize(350, 300);
        setLocationRelativeTo(parent);

        // Components
        JLabel titleLabel = new JLabel("Current Points: " + initialBetAmount);
        JLabel nameLabel = new JLabel("Enter Bet Amount:"); // Add a second label for the player name
        betTextField = new JTextField("0", 10);
        JPanel buttonPanel = createButtonPanel();
        JButton enterButton = new JButton("Deal");
        enterButton.addActionListener(new EnterButtonListener());

        // Set layout to null
        setLayout(null);

        // Set positions of components
        titleLabel.setBounds(10, 10, 300, 20);
        nameLabel.setBounds(10, 30, 300, 20); // Position the second label beneath the first one
        betTextField.setBounds(10, 60, 150, 20);
        buttonPanel.setBounds(10, 90, 300, 120);
        enterButton.setBounds(10, 220, 80, 30);

        // Add components to the dialog
        add(titleLabel);
        add(nameLabel);
        add(betTextField);
        add(buttonPanel);
        add(enterButton);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Bet amount buttons
        int[] amounts = {1, 5, 25, 50, 100, 500, 1000};
        for (int amount : amounts) {
            JButton betButton = new JButton(Integer.toString(amount));
            betButton.addActionListener(new BetButtonListener(amount));
            buttonPanel.add(betButton);
        }

        return buttonPanel;
    }

    public int getBetAmount() {
        return betAmount;
    }

    private class BetButtonListener implements ActionListener {
        private final int amount;

        public BetButtonListener(int amount) {
            this.amount = amount;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            betAmount += amount;
            betTextField.setText(Integer.toString(betAmount));
        }
    }

    private class EnterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // Close the dialog
        }
    }

    public static int getBetAmount(JFrame parent, int initialBetAmount) {
        BetDialog betDialog = new BetDialog(parent, initialBetAmount);
        betDialog.setVisible(true);
        return betDialog.getBetAmount();
    }


}
