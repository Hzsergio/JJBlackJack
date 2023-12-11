package org.JackJumpers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    public void testCalculateHandValue() {
        // Create a hand with known cards
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE, "url1"),
                new Card(Suit.DIAMONDS, Rank.KING, "url2"),
                new Card(Suit.CLUBS, Rank.FIVE, "url3")
        );
        Hand hand = new Hand(cards);

        // Verify that the hand value is calculated correctly
        assertEquals(16, hand.calculateHandValue());
    }

    @Test
    public void testCalculateHiddenHandValue() {
        // Create a hand with known cards
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE, "url1"),
                new Card(Suit.DIAMONDS, Rank.KING, "url2")
        );
        Hand hand = new Hand(cards);

        // Verify that the hidden hand value is calculated correctly
        assertEquals(10, hand.calculateHiddenHandValue());
    }

}