package org.JackJumpers;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * The BlackJackGame class represents a game of Blackjack, managing the deck, hands, bets, and game logic.
 */
public class BlackJackGame {
    private final Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private boolean result;
    private static UserData player;
    private static int currentBet;
    private static String gameEndMessage;
    private CardListener cardListener;

    /**
     * Constructs a BlackJackGame object with the specified username.
     *
     * @param username The username of the player.
     * @throws URISyntaxException  If a URI syntax exception occurs.
     * @throws IOException        If an I/O exception occurs.
     * @throws InterruptedException If the current thread is interrupted.
     */
    public BlackJackGame(String username) throws URISyntaxException, IOException, InterruptedException {
        // Initialize the deck and hands
        deck = new Deck();
        deck.shuffle();
        playerHand = new Hand(new ArrayList<>());
        dealerHand = new Hand(new ArrayList<>());
        playerHand.deal(deck);
        dealerHand.deal(deck);
        result = false;
        player = UserData.getUserData(username);
    }

    /**
     * Sets the card listener for the game.
     *
     * @param cardListener The CardListener to set.
     */
    public void setCardListener(CardListener cardListener) {
        this.cardListener = cardListener;
    }

    /**
     * Calculates the value of the player's hand.
     *
     * @return The value of the player's hand.
     */
    public int calculatePlayerHand() {
        return playerHand.calculateHandValue();
    }

    /**
     * Calculates the value of the dealer's hand.
     *
     * @return The value of the dealer's hand.
     */
    public int calculateDealerHand() {
        return dealerHand.calculateHandValue();
    }

    /**
     * Calculates the value of the hidden part of the dealer's hand.
     *
     * @return The value of the hidden part of the dealer's hand.
     */
    public int calculateHiddenHand() {
        return dealerHand.calculateHiddenHandValue();
    }

    /**
     * Player action: Draw a card (Hit).
     */
    public void Hit() {
        playerHand.playerHit(deck);
    }

    /**
     * Dealer's turn: Draws cards until the hand value is 17 or higher.
     *
     * @throws UnsupportedAudioFileException If the audio file is not supported.
     * @throws LineUnavailableException      If a line is unavailable.
     * @throws IOException                   If an I/O exception occurs.
     */
    public void dealerTurn() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        while (dealerHand.calculateHandValue() < 17) {
            Card card = deck.drawCard();
            if (card != null) {
                dealerHand.addCard(card);
            }
            if (cardListener != null) {
                cardListener.onCardDrawn(card);
            }

            int handValue = dealerHand.calculateHandValue();

            // If dealer has a soft 17 (Ace counted as 11), they must draw another card
            if (handValue >= 17 && handValue <= 21 && dealerHand.containsAce())
                continue;
            else if (handValue >= 17) {
                // The dealer has a hard 17 or higher, they stand
                break;
            }
        }
    }

    /**
     * Determines the winner of the game.
     */
    public void determineWinner() {
        if (calculatePlayerHand() > 21) {
            gameEndMessage = "Dealer wins, you bust!";
        } else if (calculateDealerHand() > 21) {
            result = true;
            winBet();
            gameEndMessage = "You win, dealer busts!";
        } else if (calculatePlayerHand() > calculateDealerHand()) {
            result = true;
            winBet();
            gameEndMessage = "You win!";
        } else if (calculatePlayerHand() < calculateDealerHand()) {
            gameEndMessage = "Dealer Wins";
        } else if (calculatePlayerHand() == calculateDealerHand()) {
            tieBet();
            gameEndMessage = "It's a tie!";
        }
    }

    /**
     * Resets the game for a new round.
     */
    public void reset() {
        resetDeck();
        deck.shuffle();
        playerHand = new Hand(new ArrayList<>());
        dealerHand = new Hand(new ArrayList<>());
        playerHand.deal(deck);
        dealerHand.deal(deck);
        result = false;
    }

    /**
     * Gets the username of the player.
     *
     * @return The username of the player.
     */
    public String getUsername() {
        return player.getUser();
    }

    /**
     * Updates the player's win and loss statistics.
     */
    public void updateWinLoss() {
        if (result) {
            player.incrementWin();
        } else {
            player.incrementLosses();
        }
        player.updateStats();
    }

    /**
     * Gets the current bet amount.
     *
     * @return The current bet amount.
     */
    public static int getCurrentBet() {
        return currentBet;
    }

    /**
     * Starts the betting process.
     */
    public static void startBet() {
        boolean validBet = false;
        while (!validBet) {
            // Call BetDialog to get the bet amount
            int betAmount = BetDialog.getBetAmount(null, player.getPoints());

            if (player.canBet(betAmount)) {
                currentBet = betAmount;
                player.placeBet(betAmount);
                validBet = true;
            }
        }
    }

    /**
     * Gets the URL of the latest drawn card's image.
     *
     * @return The URL of the latest drawn card's image.
     */
    public String latestImage() {
        return playerHand.getLatestCardImage();
    }

    /**
     * Gets a list of URLs for the player's hand card images.
     *
     * @return A list of URLs for the player's hand card images.
     */
    public List<String> getPlayerImages() {
        return playerHand.getCardImages();
    }

    /**
     * Gets a list of URLs for the dealer's hand card images.
     *
     * @return A list of URLs for the dealer's hand card images.
     */
    public List<String> getDealerImages() {
        return dealerHand.getCardImages();
    }

    /**
     * Resets the cards in the hands to the deck.
     */
    public void resetDeck() {
        playerHand.returnToDeck(deck);
        dealerHand.returnToDeck(deck);
    }

    /**
     * Wins the bet for the player.
     */
    public void winBet() {
        player.winningBet(currentBet);
    }

    /**
     * Ties the bet for the player.
     */
    public void tieBet() {
        player.tieBet(currentBet);
    }

    /**
     * Gets the current points of the player.
     *
     * @return The current points of the player.
     */
    public int getPoints() {
        return player.getPoints();
    }

    /**
     * Checks if the player can double down.
     *
     * @return True if the player can double down; false otherwise.
     */
    public boolean canDoubleDown() {
        int doubleBet = currentBet * 2;
        return player.canBet(doubleBet);
    }

    /**
     * Doubles down the bet for the player.
     */
    public void doubleBet() {
        player.placeBet(currentBet);
        currentBet *= 2;
    }

    /**
     * Gets the game end message.
     *
     * @return The game end message.
     */
    public static String getGameEndMessage() {
        return gameEndMessage;
    }

    /**
     * Gets the result of the game.
     *
     * @return True if the player wins; false otherwise.
     */
    public boolean getResult() {
        return result;
    }
}
