package org.JackJumpers;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BlackJackGame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private boolean result;
    private static UserData player;

    private static int currentBet;
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
    public void setCardListener(CardListener cardListener) {
        this.cardListener = cardListener;
    }
    public String getPlayerHand() {
        return playerHand.toString();
    }

    public String getDealerHand() {
        return dealerHand.toString();
    }

    public int calculatePlayerHand() {
        return playerHand.calculateHandValue();
    }

    public int calculateDealerHand() {
        return dealerHand.calculateHandValue();
    }

    public void Hit() {
        playerHand.playerHit(deck);
    }

    //    public void dealerTurn() {Game.dealerTurn(dealerHand, deck);}'
    public void dealerTurn() {
//    int soft17 = 17;

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
            if (handValue >= 17 && handValue <= 21 && dealerHand.containsAce()) continue;
            else if (handValue >= 17) {
                // The dealer has a hard 17 or higher, they stand
                break;
            }
        }

    }

    public void determineWinner() {
        if (calculatePlayerHand() > 21) {
            System.out.println("Dealer wins, you bust!");
            JOptionPane.showMessageDialog(null, "Dealer wins, you bust!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (calculateDealerHand() > 21) {
            result = true;
            winBet();
            System.out.println("You win, dealer busts!");
            JOptionPane.showMessageDialog(null, "You win, dealer busts!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (calculatePlayerHand() > calculateDealerHand()) {
            result = true;
            winBet();
            System.out.println("You win!");
            JOptionPane.showMessageDialog(null, "You win!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (calculatePlayerHand() < calculateDealerHand()) {
            System.out.println("Dealer Wins");
            JOptionPane.showMessageDialog(null, "Dealer wins!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (calculatePlayerHand() == calculateDealerHand()) {
            tieBet();
            System.out.println("It's a tie!");
            JOptionPane.showMessageDialog(null, "It's a tie!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public void reset() throws URISyntaxException, IOException, InterruptedException {
//        deck = new Deck();
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

    public static void startBet() {
        boolean validBet = false;
        while (!validBet) {
            String betAmountString = JOptionPane.showInputDialog(null,"Current Points: " + player.getPoints() + "\nHow much do you want to bet?:", "Enter Bet",JOptionPane.QUESTION_MESSAGE);

            int betAmount = Integer.parseInt(betAmountString);

            if (UserData.canBet(betAmount)) {
                currentBet = betAmount;
                player.placeBet(betAmount);
                validBet = true;
            }


        }
    }
    public String latestImage(){
        return playerHand.getLatestCardImage();

    }
    public List<String> getPlayerImages(){
        return playerHand.getCardImages();
    }

    public List<String> getDealerImages(){
        return dealerHand.getCardImages();
    }
    public void resetDeck(){
        playerHand.returnToDeck(deck);
        dealerHand.returnToDeck(deck);
    }
    public void winBet() {
        player.winningBet(currentBet);
    }

    public void tieBet() {
        player.tieBet(currentBet);
    }
}


