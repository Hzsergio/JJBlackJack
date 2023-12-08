package org.JackJumpers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BlackJackGame {
    private final Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private boolean result;
    private static UserData player;
    private static int currentBet;

    public static int getCurrentBet() {
        return currentBet;
    }

    private static String gameEndMessage;
    private CardListener cardListener;


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

    public static String getGameEndMessage() {
        return gameEndMessage;
    }

    public void setCardListener(CardListener cardListener) {
        this.cardListener = cardListener;
    }

    public int calculatePlayerHand() {
        return playerHand.calculateHandValue();
    }

    public int calculateDealerHand() {
        return dealerHand.calculateHandValue();
    }
    public int calculateHiddenHand() {
        return dealerHand.calculateHiddenHandValue();
    }
    public void Hit() {
        playerHand.playerHit(deck);
    }

    public void dealerTurn() {
        while (dealerHand.calculateHandValue() < 17) {
            Card card = deck.drawCard();
            if (card != null) {
                dealerHand.addCard(card);
            }
            if (cardListener != null) {
                cardListener.onCardDrawn(card);
            }

            int handValue = dealerHand.calculateHandValue();
            System.out.println("TEST Dealer Score: " + calculateDealerHand());

            // If dealer has a soft 17 (Ace counted as 11), they must draw another card
            if (handValue >= 17 && handValue <= 21 && dealerHand.containsAce()) continue;
            else if (handValue >= 17) {
                // The dealer has a hard 17 or higher, they stand
                break;
            }
        }
    }

    public void determineWinner() {
        System.out.println("Player Score: " + calculatePlayerHand());
        System.out.println("Dealer Score: " + calculateDealerHand());
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

    public void reset() throws URISyntaxException, IOException, InterruptedException {
        resetDeck();
        deck.shuffle();
        playerHand = new Hand(new ArrayList<>());
        dealerHand = new Hand(new ArrayList<>());
        playerHand.deal(deck);
        dealerHand.deal(deck);
    }

    public String getUsername() {
        return player.getUser();
    }

    public void updateWinLoss() {
        if (result) {
            player.incrementWin();

        } else player.incrementLosses();
        player.updateStats();
    }

//    public static void startBet() {
//        boolean validBet = false;
//        while (!validBet) {
//            String betAmountString = JOptionPane.showInputDialog(null, "Current Points: " + player.getPoints() + "\nHow much do you want to bet?:", "Enter Bet", JOptionPane.QUESTION_MESSAGE);
//
//            int betAmount = Integer.parseInt(betAmountString);
//
//            if (UserData.canBet(betAmount)) {
//                currentBet = betAmount;
//                player.placeBet(betAmount);
//                validBet = true;
//            }
//
//
//        }
//    }
    public static void startBet() {
    boolean validBet = false;
    while (!validBet) {
//        String betAmountString = JOptionPane.showInputDialog(null, "Current Points: " + player.getPoints() + "\nHow much do you want to bet?:", "Enter Bet", JOptionPane.QUESTION_MESSAGE);
//
//        int betAmount = Integer.parseInt(betAmountString);

            // Call BetDialog to get the bet amount
        int betAmount = BetDialog.getBetAmount(null, player.getPoints()); // You can pass null or create a JFrame if needed

        System.out.println("Selected Bet Amount: " + betAmount);


        if (player.canBet(betAmount)) {
            currentBet = betAmount;
            player.placeBet(betAmount);
            validBet = true;
        }


    }
}
    public String latestImage() {
        return playerHand.getLatestCardImage();

    }

    public List<String> getPlayerImages() {
        return playerHand.getCardImages();
    }

    public List<String> getDealerImages() {
        return dealerHand.getCardImages();
    }

    public void resetDeck() {
        playerHand.returnToDeck(deck);
        dealerHand.returnToDeck(deck);
    }

    public void winBet() {
        player.winningBet(currentBet);
    }

    public void tieBet() {
        player.tieBet(currentBet);
    }

    public static int getPoints(){
        return player.getPoints();
    }
}


