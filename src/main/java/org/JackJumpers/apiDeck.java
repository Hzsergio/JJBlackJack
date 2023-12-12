package org.JackJumpers;
/**
 * Class to collect data from a deck created by the DeckOfCards API.
 */
public class apiDeck {
    private String deck_id;

    public apiDeck() {
    }

    /**
     * Gets the value of the deck id needed for API calls
     *
     * @return The value of deck_id.
     */
    public String getDeck_id() {
        return deck_id;
    }

}
