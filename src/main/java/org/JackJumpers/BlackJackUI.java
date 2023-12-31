package org.JackJumpers;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * The BlackJackUI class represents the graphical user interface for the Blackjack game.
 * It extends the CustomIcon class and implements the CardListener interface.
 */
public class BlackJackUI extends CustomIcon implements CardListener {
    private JButton hitButton;
    private JButton standButton;
    private JButton restartButton;
    private JButton musicButton;
    private JButton doubleButton;
    private JLabel playerHandArea;
    private JLabel dealerHandArea;
    private JLabel pointInfo;
    private JPanel backgroundPanel;
    private final BlackJackGame currentGame;
    ImagePanel imagePanel1 = null;
    ImagePanel imagePanel2 = null;
    ImagePanel imagePanel3 = null;
    ImagePanel imagePanel4 = null;
    private int imageCounter = 4;
    private int imageCounterDealer = 4;

    private final List<ImagePanel> dynamicImagePanels = new ArrayList<>();
    private final List<ImagePanel> dynamicImagePanelsDealer = new ArrayList<>();
    private final List<ImagePanel> dealImagePanels = new ArrayList<>();
    private Timer timer;
    private int currentStep;

    /**
     * Constructs a BlackJackUI object.
     *
     * @param game The associated BlackJackGame object.
     */
    public BlackJackUI(BlackJackGame game) {
        this.currentGame = game;
        game.setCardListener(this);
    }

    /**
     * Creates and initializes the user interface for the Blackjack game.
     *
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    public void createUI() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setTitle("Jack Jumpers Blackjack Playing as: " + currentGame.getUsername());
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

        // Set up the frame with the custom background panel
        setContentPane(backgroundPanel);
        setSize(750, 520);
        setLocationRelativeTo(null);
        setVisible(true);

        callBet();
        SwingUtilities.invokeLater(this::showDoubleDown);
        initialDealImages();

        // Create labels
        dealerHandArea = new JLabel(currentGame.calculateHiddenHand() + " Dealer");
        playerHandArea = new JLabel(currentGame.calculatePlayerHand() + " Player");
        pointInfo = new JLabel("Current Bet: " + BlackJackGame.getCurrentBet() + "     Points: " + currentGame.getPoints());
        // Set font for labels
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        dealerHandArea.setFont(labelFont);
        playerHandArea.setFont(labelFont);
        pointInfo.setFont(labelFont);

        // Set bounds for labels
        dealerHandArea.setBounds(10, 90, 100, 50);
        playerHandArea.setBounds(10, 270, 100, 50);
        pointInfo.setBounds(10, 415, 400, 20);

        dealerHandArea.setForeground(Color.WHITE);
        pointInfo.setForeground(Color.YELLOW);
        playerHandArea.setForeground(Color.YELLOW);

        // Create buttons
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        restartButton = new JButton("Play Again");
        restartButton.setVisible(false);
        JButton exitButton = new JButton("Exit");
        musicButton = new JButton("Music");
        doubleButton = new JButton("Double Down");
        JButton leaderboardsButton = new JButton("Leaderboards");


        // Set bounds for buttons
        hitButton.setBounds(10, 450, 75, 30);
        standButton.setBounds(95, 450, 75, 30);
        restartButton.setBounds(330, 450, 100, 30);
        exitButton.setBounds(600, 450, 75, 30);
        musicButton.setBounds(600, 5, 100, 30);
        doubleButton.setBounds(180, 450, 125, 30);
        leaderboardsButton.setBounds(450, 5, 125, 30);


        // Add components to the background panel

        backgroundPanel.add(dealerHandArea);
        backgroundPanel.add(playerHandArea);
        backgroundPanel.add(pointInfo);
        backgroundPanel.add(hitButton);
        backgroundPanel.add(standButton);
        backgroundPanel.add(restartButton);
        backgroundPanel.add(exitButton);
        backgroundPanel.add(musicButton);
        backgroundPanel.add(doubleButton);
        backgroundPanel.add(leaderboardsButton);
        revalidate();
        repaint();
        // Initialize the timer
        timer = new Timer(500, e -> {
            try {
                handleTimerTick();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        currentStep = 0;



        exitButton.addActionListener(e -> {
            String iconPath = "resources/chip.png";

            ImageIcon icon = new ImageIcon(iconPath);

            // Display a confirmation dialog before exiting
            int choice = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit? \n (Placed bets will not be returned)",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
            if (choice == JOptionPane.YES_OPTION) {
                // Perform the exit action
                System.out.println("Exiting the game...");
                System.exit(0);
            }
        });
        hitButton.addActionListener(e -> {
            doubleButton.setVisible(false);
            try {
                createAndAddImagePanel(); // Create a new ImagePanel and add it
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            if (currentGame.calculatePlayerHand() > 21) {
                // Disable the buttons
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                doubleButton.setEnabled(false);
                SwingUtilities.invokeLater(this::revealDealerCards);
                //Determine Winner
                currentGame.determineWinner();
                currentGame.updateWinLoss();
                SwingUtilities.invokeLater(() -> {

                    Timer timer = new Timer(800, e1 -> {
                        try {
                            gameEndDisplay();
                        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    timer.setRepeats(false); // Execute only once
                    timer.start();
                });
                // Show the restart button when the game is over
                restartButton.setVisible(true);
            }
        });

        doubleButton.addActionListener(e -> {
            doubleDown();
            try {
                createAndAddImagePanel();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            if (currentGame.calculatePlayerHand() > 21) {
                SwingUtilities.invokeLater(this::revealDealerCards);
                //Determine Winner
                currentGame.determineWinner();
                currentGame.updateWinLoss();
                SwingUtilities.invokeLater(() -> {

                            //bet window to open after a delay

                            Timer timer = new Timer(800, e1 -> {
                                try {
                                    gameEndDisplay();
                                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                            timer.setRepeats(false); // Execute only once
                            timer.start();
                        });
            }
            else timer.start();


        });

        restartButton.addActionListener(e -> {
            try {
                resetToDefault(); // Reset the page to the default state
            } catch (URISyntaxException | InterruptedException | IOException | LineUnavailableException |
                     UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        });

        standButton.addActionListener(e -> {

            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            doubleButton.setEnabled(false);

            // Start the timer to introduce delays
            timer.start();
        });
        musicButton.addActionListener(e -> {
            boolean musicStatus = MusicPlayer.toggleMusic();
            if(musicStatus) musicButton.setText("Stop Music");
            else musicButton.setText("Play Music");
        });
        leaderboardsButton.addActionListener(e -> SwingUtilities.invokeLater(LeaderBoard::new));




    }//end of createUI

    /**
     * Creates and adds an ImagePanel to represent a drawn card in the UI.
     *
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    private void createAndAddImagePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        currentGame.Hit();
        updatePlayerHandLabel();
        ImagePanel newImagePanel = new ImagePanel(currentGame.latestImage());
        newImagePanel.setBounds(100 + (imageCounter - 2) * 130, 230, 114, 158);
        imageCounter++;
        add(newImagePanel);
        dynamicImagePanels.add(newImagePanel);
        revalidate();
        repaint();
        MusicPlayer.cardSound();
    }

    /**
     * Initializes the UI with the initial deal of cards, including hidden dealer card.
     *
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    private void initialDealImages() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        String backOfCard = "https://www.deckofcardsapi.com/static/img/back.png";

        List<String> playersCurrentCards = currentGame.getPlayerImages();
        List<String> dealersCurrentCards = currentGame.getDealerImages();

        // Create ImagePanels for each image URL


        imagePanel3 = new ImagePanel(playersCurrentCards.get(0));
        dealImagePanels.add(imagePanel3);
        imagePanel4 = new ImagePanel(playersCurrentCards.get(1));
        dealImagePanels.add(imagePanel4);

        imagePanel1 = new ImagePanel(backOfCard);

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
        MusicPlayer.shuffleSound();

    }

    /**
     * Reveals the dealer's hidden cards in the UI.
     */
    public void revealDealerCards(){
        List<String> dealersCurrentCards = currentGame.getDealerImages();

        // Assuming dealersCurrentCards contains URLs for each dealer card
        if (dealersCurrentCards.size() >= 2) {
            // Assuming index 0 is the first dealer card and index 1 is the second dealer card
            imagePanel1.setImage(dealersCurrentCards.get(0)); // Set the image for the first dealer card
            imagePanel2.setImage(dealersCurrentCards.get(1)); // Set the image for the second dealer card
        }
        updateHandLabels();
    }

    /**
     * Resets the UI to its default state after a game is finished.
     *
     * @throws URISyntaxException          If a URI syntax exception occurs.
     * @throws IOException                If an IO operation fails.
     * @throws InterruptedException       If a thread is interrupted.
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     */
    private void resetToDefault() throws URISyntaxException, IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
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
        imageCounterDealer = 4;
        currentGame.reset();
        callBet();
        initialDealImages();
        restartButton.setVisible(false);
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        showDoubleDown();
        resetHandLabels();
        currentStep = 0;
        revalidate();
        repaint();
    }

    /**
     * Loads an image from a given URL.
     *
     * @param imageUrl The URL of the image.
     * @return The loaded image.
     */
    private Image loadImageFromURL(String imageUrl) {
        try {
            return new ImageIcon(new URL(imageUrl)).getImage();
        } catch (MalformedURLException e) {
            System.err.println("Failed to load image");
            return null;
        }
    }

    /**
     * Handles the event of a card being drawn and updates the UI accordingly.
     *
     * @param card The drawn card.
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    @Override
    public void onCardDrawn(Card card) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        updateHandLabels();
        ImagePanel newImagePanel = new ImagePanel(card.getUrl());
        newImagePanel.setBounds(100 + (imageCounterDealer - 2) * 130, 50, 114, 158);
        imageCounterDealer++;
        add(newImagePanel);
        dynamicImagePanelsDealer.add(newImagePanel);
        revalidate();
        repaint();
        MusicPlayer.cardSound();
    }

    /**
     * A JPanel subclass representing an image panel for displaying card images.
     */
    private static class ImagePanel extends JPanel {
        private ImageIcon imageIcon;

        /**
         * Constructs an ImagePanel with the given image URL.
         *
         * @param imageUrl The URL of the image.
         */
        public ImagePanel(String imageUrl) {
            setImage(imageUrl);
        }

        /**
         * Sets the image of the panel based on the given URL.
         *
         * @param imageUrl The URL of the image.
         */
        public void setImage(String imageUrl) {
            try {
                Image originalImage = new ImageIcon(new URL(imageUrl)).getImage();
                int newWidth = (int) (originalImage.getWidth(this) * 0.5);
                int newHeight = (int) (originalImage.getHeight(this) * 0.5);
                imageIcon = new ImageIcon(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
                repaint(); // Repaint the panel to update the displayed image
            } catch (MalformedURLException e) {
                System.err.println("Failed to set image");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imageIcon != null) {
                g.drawImage(imageIcon.getImage(), 0, 0, this);
            }
        }
    }

    /**
     * Resets the labels displaying the player's and dealer's hand information.
     */
    private void resetHandLabels() {
        playerHandArea.setText(currentGame.calculatePlayerHand() + " Player");
        dealerHandArea.setText(currentGame.calculateHiddenHand() + " Dealer");
        pointInfo.setText("Current Bet: " + BlackJackGame.getCurrentBet() + "     Points: " + (currentGame.getPoints() + BlackJackGame.getCurrentBet()));
    }

    /**
     * Updates the label displaying the player's hand information.
     */
    private void updatePlayerHandLabel() {
        playerHandArea.setText(currentGame.calculatePlayerHand() + " Player");
    }


    /**
     * Updates the labels displaying the player's and dealer's hand information.
     */
    private void updateHandLabels() {
        playerHandArea.setText(currentGame.calculatePlayerHand() + " Player");
        dealerHandArea.setText(currentGame.calculateDealerHand() + " Dealer");
    }

    /**
     * Calls the bet function to start a new bet in the game.
     *
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    private void callBet() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        BlackJackGame.startBet();
        MusicPlayer.betSound();


    }

    /**
     * Displays the end-of-game message with appropriate sound effects.
     *
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    private void gameEndDisplay() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String message = BlackJackGame.getGameEndMessage();
        if(currentGame.getResult()) MusicPlayer.winSound();
        if (!currentGame.getResult()) MusicPlayer.loseSound();

        String iconPath = "resources/chip.png";
        ImageIcon icon = new ImageIcon(iconPath);
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.PLAIN_MESSAGE, icon);
    }

    /**
     * Handles the timer tick event, performing actions based on the current step.
     *
     * @throws UnsupportedAudioFileException If an unsupported audio file is encountered.
     * @throws LineUnavailableException      If a line is unavailable for audio playback.
     * @throws IOException                   If an IO operation fails.
     */
    private void handleTimerTick() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Perform actions based on the current step
        switch (currentStep) {
            case 0:
                revealDealerCards();
                break;
            case 1:
                currentGame.dealerTurn();
                break;
            case 2:
                updateHandLabels();
                break;
            case 3:
                currentGame.determineWinner();
                currentGame.updateWinLoss();
                break;
            case 4:
                gameEndDisplay();
                break;
            case 5:
                // All steps completed, stop the timer
                timer.stop();
                restartButton.setVisible(true);
                break;
        }

        // Move to the next step
        currentStep++;
    }

    /**
     * Shows or hides the "Double Down" button based on the game state.
     */
    public void showDoubleDown() {
        if (currentGame.canDoubleDown()) {
            doubleButton.setVisible(true);
            doubleButton.setEnabled(true);
        }
        else {
            doubleButton.setVisible(false);
            doubleButton.setEnabled(false);
        }
        revalidate();
        repaint();
    }

    /**
     * Handles the "Double Down" action in the game.
     */
    public void doubleDown(){
        currentGame.doubleBet();
        pointInfo.setText("Current Bet: " + BlackJackGame.getCurrentBet() + "     Points: " + currentGame.getPoints());
        doubleButton.setVisible(false);

    }
}