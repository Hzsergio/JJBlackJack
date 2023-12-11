package org.JackJumpers;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    public void testDrawCard() throws URISyntaxException, IOException, InterruptedException {
        Deck deck = new Deck();

        // Draw a card
        Card drawnCard = deck.drawCard();

        // Verify that a card is drawn and removed from the deck
        assertNotNull(drawnCard);
        assertFalse(deck.getCards().contains(drawnCard));
    }

    @Test
    public void testDeckShuffling() throws URISyntaxException, IOException, InterruptedException {
        Deck deck = new Deck();
        Deck originalDeck = new Deck();

        // Shuffle the deck
        deck.shuffle();

        // Verify that the deck has the same cards but in a different order
        assertNotEquals(originalDeck.getCards(), deck.getCards());
    }
}