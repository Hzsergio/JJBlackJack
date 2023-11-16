package org.JackJumpers;

import java.util.Scanner;

public class Game {

    public static boolean determineWinner(int playerScore, int dealerScore, boolean gameResult) {
        if (playerScore > 21) {
            System.out.println("Dealer wins, you bust!");
            gameResult = false;
        } else if (dealerScore > 21) {
            System.out.println("You win, dealer busts!");
            gameResult = true;
        } else if (playerScore > dealerScore) {
            System.out.println("You win!");
            gameResult = true;
        } else if (playerScore < dealerScore) {
            System.out.println("Dealer wins!");
            gameResult = false;
        } else {
            System.out.println("It's a tie!");
        }
        return gameResult;
    }

    public static void playerTurn(Hand hand, Deck deck) {
        Scanner scanner = new Scanner(System.in);
        boolean drawAnother = true;
        while (drawAnother) {

            System.out.println("Do you want to Hit? (yes/no)");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                Card card = deck.drawCard();
                if (card != null) {
                    hand.addCard(card);
                    System.out.println("You drew: " + card);
                    hand.displayHand();
                    int handValue = hand.calculateHandValue();

                    if (handValue > 21) {
                        System.out.println("Your hand value is above 21. You bust!");
                        drawAnother = false;
                    }
                }

            } else if (response.equals("no")) {
                drawAnother = false;
            } else {
                System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            }
        }

        // Close the scanner
        scanner.close();
    }

    public static void dealerTurn(Hand dealerHand, Deck deck) {
        int soft17 = 17;
        System.out.println("Testing");
        while (dealerHand.calculateHandValue() < soft17) {
            Card card = deck.drawCard();
            if (card != null) {
                dealerHand.addCard(card);
            }

            int handValue = dealerHand.calculateHandValue();
            if (handValue >= 17 && handValue <= 21 && dealerHand.containsAce()) {
                // If dealer has a soft 17 (Ace counted as 11), they must draw another card
                continue;
            } else if (handValue >= 17) {
                // The dealer has a hard 17 or higher, they stand
                break;
            }
        }

    }
}
