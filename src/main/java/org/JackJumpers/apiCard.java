package org.JackJumpers;

import java.util.List;

/**
 * Class to collect data from a card created by the DeckOfCards API.
 */
public class apiCard {

    // List to store information about the cards
    private List<CardInfo> cards;

    /**
     * Gets the list of card information.
     *
     * @return The list of card information.
     */
    public List<CardInfo> getCards() {
        return cards;
    }

    /**
     * Represents information about a single card.
     */
    public static class CardInfo {

        // Value of the card
        private String value;

        // Suit of the card
        private String suit;

        // URL of the card image
        private String image;

        /**
         * Gets the value of the card.
         *
         * @return The value of the card.
         */
        public String getValue() {
            return value;
        }

        /**
         * Gets the suit of the card.
         *
         * @return The suit of the card.
         */
        public String getSuit() {
            return suit;
        }

        /**
         * Gets the URL of the card image.
         *
         * @return The URL of the card image.
         */
        public String getImage() {
            return image;
        }
    }
}
