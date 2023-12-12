package org.JackJumpers;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a player's or dealer's hand in a blackjack game.
 */
public class Hand {

    private final List<Card> cards;
    /**
     * Constructs a hand with the given list of cards.
     *
     * @param cards The initial list of cards in the hand.
     */
    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Adds a card to the hand.
     *
     * @param card The card to be added to the hand.
     */
    public void addCard(Card card) {
        cards.add(card);
    }
    /**
     * Calculates the total value of the hand.
     *
     * @return The total value of the hand.
     */
    public int calculateHandValue() {
        int handValue = 0;
        int numAces = 0;

        for (Card card : cards) {
            Rank rank = card.getRank();
            switch (rank) {
                case ACE:
                    numAces++;
                    handValue += 11;
                    break;
                case TWO:
                    handValue += 2;
                    break;
                case THREE:
                    handValue += 3;
                    break;
                case FOUR:
                    handValue += 4;
                    break;
                case FIVE:
                    handValue += 5;
                    break;
                case SIX:
                    handValue += 6;
                    break;
                case SEVEN:
                    handValue += 7;
                    break;
                case EIGHT:
                    handValue += 8;
                    break;
                case NINE:
                    handValue += 9;
                    break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    handValue += 10;
                    break;
            }
        }

        while (numAces > 0 && handValue > 21) {
            handValue -= 10;
            numAces--;
        }

        return handValue;
    }

    /**
     * Calculates the hidden value of the dealer's hand.
     *
     * @return The hidden value of the dealer's hand.
     */
    public int calculateHiddenHandValue() {
        if (cards.size() < 2) {
            return 0;
        }

        int handValue = 0;
        // Consider only the second card (index 1) in the dealer's hand
        Card secondCard = cards.get(1);
        Rank secondCardRank = secondCard.getRank();

        switch (secondCardRank) {
            case ACE:
                handValue += 1;
                break;
            case TWO:
                handValue += 2;
                break;
            case THREE:
                handValue += 3;
                break;
            case FOUR:
                handValue += 4;
                break;
            case FIVE:
                handValue += 5;
                break;
            case SIX:
                handValue += 6;
                break;
            case SEVEN:
                handValue += 7;
                break;
            case EIGHT:
                handValue += 8;
                break;
            case NINE:
                handValue += 9;
                break;
            case TEN:
            case JACK:
            case QUEEN:
            case KING:
                handValue += 10;
                break;
        }

        return handValue;
    }

    /**
     * Deals two cards from the deck to the hand.
     *
     * @param deck The deck from which cards are drawn.
     */
    public void deal(Deck deck) {
        for (int i = 0; i < 2; i++) {
            Card card = deck.drawCard();
            if (card != null) {
                addCard(card);
            } else {
                System.out.println("No more cards in the deck.");
                break;
            }
        }
    }
    /**
     * Allows the player to hit, adding a card to the hand.
     *
     * @param deck The deck from which a card is drawn.
     */
    public void playerHit(Deck deck) {
        Card card = deck.drawCard();
//        System.out.println(card.getUrl());
        addCard(card);
    }
    /**
     * Checks if the hand contains an Ace.
     *
     * @return true if the hand contains an Ace, false otherwise.
     */
    public boolean containsAce() {
        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                return true;
            }
        }
        return false;
    }
    /**
     * Gets the URLs of the card images in the hand.
     *
     * @return The list of card images URLs.
     */
    public List<String> getCardImages() {
        List<String> cardImages = new ArrayList<>();
        for (Card card : cards) {
            cardImages.add(card.getUrl());
        }
        return cardImages;
    }

    /**
     * Returns the cards in the hand to the deck.
     *
     * @param deck The deck to which cards are returned.
     */
    public void returnToDeck(Deck deck) {
        for (Card card : cards) {
            deck.addCardToDeck(card);

        }
    }
    /**
     * Gets the URL of the latest card image in the hand.
     *
     * @return The URL of the latest card image, or null if the hand is empty.
     */
    public String getLatestCardImage() {
        if (!cards.isEmpty()) {
            return cards.get(cards.size() - 1).getUrl();
        } else {
            return null;  // Handle the case when the hand is empty
        }
    }
    /**
     * Converts the hand to a string representation.
     *
     * @return The string representation of the hand.
     */
    @Override
    public String toString() {
        StringBuilder handString = new StringBuilder();
        for (Card card : cards) {
            handString.append(card.toString()).append("\n");
        }
        return handString.toString();
    }


}