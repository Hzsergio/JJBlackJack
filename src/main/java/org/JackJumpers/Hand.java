package org.JackJumpers;

import java.util.List;

public class Hand {

    private List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

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

    public void playerHit(Deck deck) {
        Card card = deck.drawCard();
        addCard(card);
    }

    public void displayHand() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    public boolean containsAce() {
        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder handString = new StringBuilder();
        for (Card card : cards) {
            handString.append(card.toString()).append("\n");
        }
        return handString.toString();
    }


}