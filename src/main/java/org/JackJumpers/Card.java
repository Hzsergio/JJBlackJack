package org.JackJumpers;

/**
 * Represents a playing card in a standard deck.
 */
public class Card {
    private final Suit suit;
    private final Rank rank;
    private final String url;

    /**
     * Constructs a new Card with the specified suit, rank, and card image URL.
     *
     * @param suit      The suit of the card (e.g., hearts, diamonds, clubs, spades).
     * @param rank      The rank of the card (e.g., Ace, 2, 3, ..., King).
     * @param cardUrl   The URL of the image representing the card.
     */
    public Card(Suit suit, Rank rank, String cardUrl) {
        this.suit = suit;
        this.rank = rank;
        this.url = cardUrl;
    }

    /**
     * Gets the URL of the image representing the card.
     *
     * @return The URL of the card image.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the suit of the card.
     *
     * @return The suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Gets the rank of the card.
     *
     * @return The rank of the card.
     */
    public Rank getRank() {
        return rank;
    }
}
