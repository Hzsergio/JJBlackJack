package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class BlackJackUI extends JFrame {
    private JButton hitButton;
    private JButton standButton;
    private JButton restartButton;
    private JTextArea playerHandArea;
    private JTextArea dealerHandArea;
    private JLabel playerHandValue;
    private JLabel dealerHandValue;
    private JLabel gameResult;

    private BlackJackGame currentGame;


    public BlackJackUI(BlackJackGame game) {
        this.currentGame = game;
    }

    public void createUI() {
        // Set up the frame
//        currentGame = new BlackJackGame(username);

        setTitle("Jack Jumpers Blackjack" + currentGame.getUsername());
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        playerHandArea = new JTextArea("Player's hand:\n" + currentGame.getPlayerHand());
        dealerHandArea = new JTextArea("Dealer's hand:\n" + currentGame.getDealerHand());
        playerHandValue = new JLabel("Player current score: " + currentGame.calculatePlayerHand());
        dealerHandValue = new JLabel("Dealer current score: " + currentGame.calculateDealerHand());
        gameResult = new JLabel("Hit or Stand");
        playerHandArea.setEditable(false);
        dealerHandArea.setEditable(false);

        // Initialize buttons
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        restartButton = new JButton("Play Again");
        restartButton.setVisible(false);

        // Create panels for layout control
        JPanel textAreaPanel = new JPanel(new GridLayout(1, 2));
        textAreaPanel.add(playerHandArea);
        textAreaPanel.add(dealerHandArea);

        JPanel labelPanel = new JPanel(new GridLayout(4, 1));
        labelPanel.add(playerHandValue);
        labelPanel.add(dealerHandValue);
        labelPanel.add(gameResult);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        // Create a container panel for all components using GridBagLayout
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        // Add textAreaPanel to the top
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        containerPanel.add(textAreaPanel, c);

        // Add labelPanel below textAreaPanel
        c.gridy = 1;
        c.gridwidth = 1;
        containerPanel.add(labelPanel, c);

        // Add buttonPanel below labelPanel
        c.gridx = 0;
        c.gridy = 2;
        containerPanel.add(buttonPanel, c);

        // Add restartButton in the fourth row
        c.gridy = 3;
        containerPanel.add(restartButton, c);

        // Add containerPanel to the frame
        add(containerPanel);
        updateHandLabels();
//        currentGame.startBet();

        pack(); // Adjust the frame size
        hitButton.addActionListener(e -> {
            // Handle player's hit action
            currentGame.Hit();
            updateHandLabels();
            //If player busts
            if (currentGame.calculatePlayerHand() > 21) {
                // Disable the buttons
                hitButton.setEnabled(false);
                standButton.setEnabled(false);

                //Determine Winner
                gameResult.setText(currentGame.determineWinner());
                currentGame.updateWinLoss();
                // Show the restart button when the game is over
                restartButton.setVisible(true);
                pack();
            }
        });

        standButton.addActionListener(e -> {
            // Disable the buttons
            hitButton.setEnabled(false);
            standButton.setEnabled(false);

            // Handle dealers stand action
            currentGame.dealerTurn();
            updateHandLabels();
            //Calculate winner
            gameResult.setText(currentGame.determineWinner());
            // Show the restart button when the game is over
            restartButton.setVisible(true);
            currentGame.updateWinLoss();
            pack();
        });
        restartButton.addActionListener(e -> {
            // Reset the game by creating a new deck and dealing new hands
            try {
                currentGame.reset();
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            // Hide the restart button and turn on buttons
            restartButton.setVisible(false);
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
            // Update the display
            updateHandLabels();
            gameResult.setText("Hit or Stand");
            pack(); // Adjust the frame size
            currentGame.startBet();

        });
    }//End of createUI

    private void updateHandLabels() {
        playerHandArea.setText("Player's hand:\n" + currentGame.getPlayerHand());
        dealerHandArea.setText("Dealer's hand:\n" + currentGame.getDealerHand());
        playerHandValue.setText("Player current score: " + currentGame.calculatePlayerHand());
        dealerHandValue.setText("Dealer current score: " + currentGame.calculateDealerHand());
        pack();
    }

//    private void bettingWindow(){
//        String betAmountString = JOptionPane.showInputDialog("Enter the bet amount:");
//
//    }
}//End of class
