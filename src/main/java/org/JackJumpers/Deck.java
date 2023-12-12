package org.JackJumpers;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a deck of cards obtained from the DeckOfCards API.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Constructs a deck by fetching cards from the DeckOfCards API.
     *
     * @throws URISyntaxException    If the URI syntax is incorrect.
     * @throws IOException           If an I/O error occurs.
     * @throws InterruptedException  If the thread is interrupted during an HTTP operation.
     */
    public Deck() throws URISyntaxException, IOException, InterruptedException {
        cards = new ArrayList<>();
        Gson gson = new Gson();

        // Fetch a new deck from the API
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://www.deckofcardsapi.com/api/deck/new/"))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        apiDeck apideck = gson.fromJson(getResponse.body(), apiDeck.class);
        String deck_id = apideck.getDeck_id();

        // Fetch cards from the new deck and add them to the local deck
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                HttpRequest getCard = HttpRequest.newBuilder()
                        .uri(new URI("https://www.deckofcardsapi.com/api/deck/" + deck_id + "/draw/?count=1"))
                        .build();

                HttpResponse<String> cardResponse = httpClient.send(getCard, HttpResponse.BodyHandlers.ofString());
                apiCard apicard = gson.fromJson(cardResponse.body(), apiCard.class);
                List<apiCard.CardInfo> cardInfoList = apicard.getCards();

                if (cardInfoList != null && !cardInfoList.isEmpty()) {
                    apiCard.CardInfo cardInfo = cardInfoList.get(0);
                    String cardUrl = cardInfo.getImage();
                    cards.add(new Card(suit, rank, cardUrl));
                } else {
                    System.out.println("No card information available.");
                }
            }
        }
    }

    /**
     * Shuffles the deck randomly.
     */
    public void shuffle() {
        int n = cards.size();
        for (int i = n - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    /**
     * Adds a card back to the deck.
     *
     * @param card The card to be added back to the deck.
     */
    public void addCardToDeck(Card card) {
        cards.add(card);
    }

    /**
     * Draws a card from the deck.
     *
     * @return The drawn card, or null if the deck is empty.
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        int index = (int) (Math.random() * cards.size());
        return cards.remove(index);
    }

    /**
     * Gets the list of cards in the deck.
     *
     * @return The list of cards in the deck.
     */
    public List<Card> getCards() {
        return cards;
    }
}
