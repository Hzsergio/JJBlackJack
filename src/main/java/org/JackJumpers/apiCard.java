package org.JackJumpers;

import java.util.List;

public class apiCard {
    private List<CardInfo> cards;

    // Getters and setters...

    public List<CardInfo> getCards() {
        return cards;
    }

    public static class CardInfo {
        private String value;
        private String suit;
        private String image;

        // Getters and setters...

        public String getValue() {
            return value;
        }

        public String getSuit() {
            return suit;
        }

        public String getImage() {
            return image;
        }
    }
}
