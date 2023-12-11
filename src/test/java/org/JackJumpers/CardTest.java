package org.JackJumpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void testCardConstruction() {
        Suit suit = Suit.HEARTS;
        Rank rank = Rank.ACE;
        String url = "url";

        Card card = new Card(suit, rank, url);

        assertEquals(suit, card.getSuit());
        assertEquals(rank, card.getRank());
        assertEquals(url, card.getUrl());
    }

    @Test
    public void testCardInequality() {
        Card card1 = new Card(Suit.DIAMONDS, Rank.KING, "url1");
        Card card2 = new Card(Suit.HEARTS, Rank.ACE, "url2");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testCardEquality() {
        Suit suit = Suit.SPADES;
        Rank rank = Rank.TWO;
        String url = "url";

        Card card1 = new Card(suit, rank, url);
        Card card2 = new Card(suit, rank, url);

        assertNotEquals(card1, card2);
    }
}