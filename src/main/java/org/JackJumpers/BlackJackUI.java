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

public class BlackJackUI extends JFrame implements CardListener{
    private JButton hitButton;
    private JButton standButton;
    private JButton restartButton;
    private JButton exitButton;
    private JLabel playerHandArea;
    private JLabel dealerHandArea;
    private JLabel playerHandValue;
    private JLabel dealerHandValue;
    private JLabel gameResult;

    private JPanel backgroundPanel;

    private BlackJackGame currentGame;
    ImagePanel imagePanel1 = null;
    ImagePanel imagePanel2 = null;
    ImagePanel imagePanel3 = null;
    ImagePanel imagePanel4 = null;

    private int imageCounter = 4;
    private int imageCounterDealer = 4;

    private List<ImagePanel> dynamicImagePanels = new ArrayList<>();
    private List<ImagePanel> dynamicImagePanelsDealer = new ArrayList<>();

    private List<ImagePanel> dealImagePanels = new ArrayList<>();


    public BlackJackUI(BlackJackGame game) {
        this.currentGame = game;
        game.setCardListener((CardListener) this);

    }


    public void createUI() {
        setTitle("Jack Jumpers Blackjack" + currentGame.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        // Load the background image from a URL (replace with your image URL)
        Image backgroundImage = loadImageFromURL("https://assets.codepen.io/74045/green.jpg");

        // Create the custom ImageBackgroundPanel with the background image
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, this);
            }
        };
        backgroundPanel.setLayout(null); // You can use a layout manager or set bounds manually

        // Set the green background color
        backgroundPanel.setBackground(new Color(70, 122, 53));

        //Images were here
        initialDealImages();

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
        exitButton = new JButton("Exit");



        // Set bounds for buttons
        hitButton.setBounds(10, 450, 150, 30);
        standButton.setBounds(170, 450, 150, 30);
        restartButton.setBounds(330, 450, 150, 30);
        exitButton.setBounds(490, 450, 150, 30);


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display a confirmation dialog before exiting
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // Perform the exit action
                    System.out.println("Exiting the game..."); // Replace with your exit logic
                    System.exit(0);
                }
            }
        });
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
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }

                currentGame.determineWinner();
                // Show the restart button when the game is over
                restartButton.setVisible(true);
                currentGame.updateWinLoss();
            }
        });

        // Add components to the background panel

        backgroundPanel.add(dealerHandArea);
        backgroundPanel.add(playerHandArea);
        backgroundPanel.add(hitButton);
        backgroundPanel.add(standButton);
        backgroundPanel.add(restartButton);
        backgroundPanel.add(exitButton);


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
    private void initialDealImages(){

        List<String> playersCurrentCards = currentGame.getPlayerImages();
        List<String> dealersCurrentCards = currentGame.getDealerImages();

        // Create ImagePanels for each image URL


//        for(String image : playersCurrentCards) {
            imagePanel3 = new ImagePanel(playersCurrentCards.get(0));
            dealImagePanels.add(imagePanel3);
            imagePanel4 = new ImagePanel(playersCurrentCards.get(1));
            dealImagePanels.add(imagePanel4);


//        for(String image : dealersCurrentCards) {
            imagePanel1 = new ImagePanel(dealersCurrentCards.get(0));
            imagePanel2 = new ImagePanel(dealersCurrentCards.get(1));
        dealImagePanels.add(imagePanel1);
        dealImagePanels.add(imagePanel2);

        // Set bounds for ImagePanels with empty borders for spacing
        imagePanel1.setBounds(100, 50, 114, 158);
        imagePanel2.setBounds(230, 50, 114, 158);
        imagePanel3.setBounds(100, 230, 114, 158);
        imagePanel4.setBounds(230, 230, 114, 158);

        backgroundPanel.add(imagePanel1);
        backgroundPanel.add(imagePanel2);
        backgroundPanel.add(imagePanel3);
        backgroundPanel.add(imagePanel4);

    }

    private void resetToDefault() throws URISyntaxException, IOException, InterruptedException {
        for (ImagePanel dynamicImagePanel : dynamicImagePanels) {
            remove(dynamicImagePanel);
        }
        dynamicImagePanels.clear();
        imageCounter = 4;
        for (ImagePanel ImagePanel : dealImagePanels) {
            remove(ImagePanel);
        }
        dealImagePanels.clear();
        for (ImagePanel dynamicImagePanel : dynamicImagePanelsDealer) {
            remove(dynamicImagePanel);
        }
        dynamicImagePanelsDealer.clear();
        imageCounter = 4;
        currentGame.reset();
        initialDealImages();
        restartButton.setVisible(false);
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        // Update the display
        updateHandLabels();
        revalidate();
        repaint();
//        Thread.sleep(1000);
        BlackJackGame.startBet();


    }

    private Image loadImageFromURL(String imageUrl) {
        try {
            return new ImageIcon(new URL(imageUrl)).getImage();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCardDrawn(Card card) {
        System.out.println("Card drawn: " + card.getRank() + " of " + card.getSuit());

        updateHandLabels();
        ImagePanel newImagePanel = new ImagePanel(card.getUrl());
        newImagePanel.setBounds(100 + (imageCounter - 2) * 130, 50, 114, 158);
        imageCounterDealer++;
        add(newImagePanel);
        dynamicImagePanelsDealer.add(newImagePanel);
        revalidate();
        repaint();

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
