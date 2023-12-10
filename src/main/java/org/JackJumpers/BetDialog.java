package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BetDialog extends JDialog {
    private int betAmount;
    private JTextField betTextField;

    public BetDialog(JFrame parent, int totalPoints) {
        super(parent, "Place Your Bet:", true);
        setSize(350, 300);
        setLocationRelativeTo(parent);

        // Components
        JLabel titleLabel = new JLabel("Current Points: " + totalPoints);
        JLabel nameLabel = new JLabel("Enter Bet Amount:");
        betTextField = new JTextField("0", 10);
        JPanel buttonPanel = createButtonPanel(totalPoints);
        JButton enterButton = new JButton("Deal");
        JButton clearButton = new JButton("Clear");

        enterButton.addActionListener(new EnterButtonListener());
        clearButton.addActionListener(new ClearButtonListener());

        // Set layout to null
        setLayout(null);

        // Set positions of components
        titleLabel.setBounds(10, 10, 300, 20);
        nameLabel.setBounds(10, 30, 300, 20);
        betTextField.setBounds(10, 60, 150, 20);
        buttonPanel.setBounds(10, 90, 300, 120);
        enterButton.setBounds(10, 220, 80, 30);
        clearButton.setBounds(100, 220, 80, 30);

        // Add components to the dialog
        add(titleLabel);
        add(nameLabel);
        add(betTextField);
        add(buttonPanel);
        add(enterButton);
        add(clearButton);
    }

    private JPanel createButtonPanel(int totalPoints) {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Bet amount buttons
        int[] amounts = {1, 5, 25, 50, 100, 500, 1000};
        for (int amount : amounts) {
            JButton betButton = new JButton(Integer.toString(amount));
            betButton.addActionListener(new BetButtonListener(amount));
            buttonPanel.add(betButton);
        }

        // All In button
        JButton allInButton = new JButton("All In");
        allInButton.addActionListener(e -> {
            betAmount = totalPoints;
            betTextField.setText(Integer.toString(betAmount));
        });
        buttonPanel.add(allInButton);

        return buttonPanel;
    }

    public int getBetAmount() {
        return betAmount;
    }

    private class BetButtonListener implements ActionListener {
        private int amount;

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

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            betAmount = 0;
            betTextField.setText("0");
        }
    }

    public static int getBetAmount(JFrame parent, int initialBetAmount) {
        BetDialog betDialog = new BetDialog(parent, initialBetAmount);
        betDialog.setVisible(true);
        return betDialog.getBetAmount();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            int initialBetAmount = 0;
            int betAmount = BetDialog.getBetAmount(frame, initialBetAmount);

            System.out.println("Selected Bet Amount: " + betAmount);
            System.exit(0);
        });
    }
}


