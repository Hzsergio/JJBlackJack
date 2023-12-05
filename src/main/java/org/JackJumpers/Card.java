package org.JackJumpers;


public class Card {
    private final Suit suit;
    private final Rank rank;
    private final String url;

    public Card(Suit suit, Rank rank, String cardUrl) {
        this.suit = suit;
        this.rank = rank;
        this.url = cardUrl;
    }

    public String getUrl() {
        return url;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

}
