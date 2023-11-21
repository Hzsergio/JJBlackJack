package org.JackJumpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BlackJackUI extends JFrame {
    private JButton hitButton;
    private JButton standButton;
    private JButton restartButton;
    private JButton exit;
    private JLabel playerHandArea;
    private JLabel dealerHandArea;
    private JLabel playerHandValue;
    private JLabel dealerHandValue;
    private JLabel gameResult;

    private BlackJackGame currentGame;

    private int imageCounter = 4;
    private List<ImagePanel> dynamicImagePanels = new ArrayList<>();

    public BlackJackUI(BlackJackGame game) {
        this.currentGame = game;
    }


    public void createUI() {
        setTitle("Jack Jumpers Blackjack" + currentGame.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        String card = "https://deckofcardsapi.com/static/img/6H.png";
        // Load the background image from a URL (replace with your image URL)
        Image backgroundImage = loadImageFromURL("https://assets.codepen.io/74045/green.jpg");

        // Create the custom ImageBackgroundPanel with the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        };
        backgroundPanel.setLayout(null); // You can use a layout manager or set bounds manually

        // Set the green background color
        backgroundPanel.setBackground(new Color(70, 122, 53));

        // Create ImagePanels for each image URL
        ImagePanel imagePanel1 = new ImagePanel(card);
        ImagePanel imagePanel2 = new ImagePanel(card);
        ImagePanel imagePanel3 = new ImagePanel(card);
        ImagePanel imagePanel4 = new ImagePanel(card);

        // Set bounds for ImagePanels with empty borders for spacing
        imagePanel1.setBounds(100, 50, 114, 158);
        imagePanel2.setBounds(230, 50, 114, 158);
        imagePanel3.setBounds(100, 230, 114, 158);
        imagePanel4.setBounds(230, 230, 114, 158);

        // Create labels
        dealerHandArea = new JLabel(currentGame.calculateDealerHand() + " Dealer");
        playerHandArea = new JLabel(currentGame.calculatePlayerHand()+ " Player");

        // Set font for labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        dealerHandArea.setFont(labelFont);
        playerHandArea.setFont(labelFont);

        // Set bounds for labels
        dealerHandArea.setBounds(10, 90, 100, 50);
        playerHandArea.setBounds(10, 270, 100, 50);
        dealerHandArea.setForeground(Color.WHITE);

// Change font color for label2 (example: blue)
        playerHandArea.setForeground(Color.YELLOW);
        // Create buttons
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        restartButton = new JButton("Play Again");
        restartButton.setVisible(false);
        exit = new JButton("Exit");



        // Set bounds for buttons
        hitButton.setBounds(10, 450, 150, 30);
        standButton.setBounds(170, 450, 150, 30);
        restartButton.setBounds(330, 450, 150, 30);
        exit.setBounds(490, 450, 150, 30);



        // Add ActionListener to button1
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndAddImagePanel(); // Create a new ImagePanel and add it
                if (currentGame.calculatePlayerHand() > 21) {
                    // Disable the buttons
                    hitButton.setEnabled(false);
                    standButton.setEnabled(false);

                    //Determine Winner
                    currentGame.determineWinner();
                    currentGame.updateWinLoss();
                    // Show the restart button when the game is over
                    restartButton.setVisible(true);
                }
            }
        });

        // Add ActionListener to button2
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resetToDefault(); // Reset the page to the default state
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                standButton.setEnabled(false);

                // Handle dealers stand action
                currentGame.dealerTurn();
                updateHandLabels();
                //Calculate winner
                currentGame.determineWinner();
                // Show the restart button when the game is over
                restartButton.setVisible(true);
                currentGame.updateWinLoss();
            }
        });

        // Add components to the background panel
        backgroundPanel.add(imagePanel1);
        backgroundPanel.add(imagePanel2);
        backgroundPanel.add(imagePanel3);
        backgroundPanel.add(imagePanel4);
        backgroundPanel.add(dealerHandArea);
        backgroundPanel.add(playerHandArea);
        backgroundPanel.add(hitButton);
        backgroundPanel.add(standButton);
        backgroundPanel.add(restartButton);
        backgroundPanel.add(exit);


        // Set up the frame with the custom background panel
        setContentPane(backgroundPanel);
        setSize(750, 520);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createAndAddImagePanel() {
        currentGame.Hit();
        updateHandLabels();
        ImagePanel newImagePanel = new ImagePanel(currentGame.latestImage());
        newImagePanel.setBounds(100 + (imageCounter - 2) * 130, 230, 114, 158);
        imageCounter++;
        add(newImagePanel);
        dynamicImagePanels.add(newImagePanel);
        revalidate();
        repaint();


    }

    private void resetToDefault() throws URISyntaxException, IOException, InterruptedException {
        for (ImagePanel dynamicImagePanel : dynamicImagePanels) {
            remove(dynamicImagePanel);
        }
        dynamicImagePanels.clear();
        imageCounter = 4;
        currentGame.reset();
        restartButton.setVisible(false);
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        // Update the display
        updateHandLabels();
        BlackJackGame.startBet();
        repaint();
    }

    private Image loadImageFromURL(String imageUrl) {
        try {
            return new ImageIcon(new URL(imageUrl)).getImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ImagePanel extends JPanel {
        private final String imageUrl;

        public ImagePanel(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                Image originalImage = new ImageIcon(new URL(imageUrl)).getImage();
                int newWidth = (int) (originalImage.getWidth(this) * 0.5);
                int newHeight = (int) (originalImage.getHeight(this) * 0.5);
                g.drawImage(originalImage, 0, 0, newWidth, newHeight, this);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateHandLabels() {
        playerHandArea.setText(currentGame.calculatePlayerHand() + " Player");
        dealerHandArea.setText(currentGame.calculateDealerHand() + " Dealer");
//        playerHandValue.setText("Player current score: " + currentGame.calculatePlayerHand());
//        dealerHandValue.setText("Dealer current score: " + currentGame.calculateDealerHand());
    }
}
