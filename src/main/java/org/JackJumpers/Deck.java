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

public class Deck {
    private final List<Card> cards;

    public Deck() throws URISyntaxException, IOException, InterruptedException {
        cards = new ArrayList<>();
        apiCard apicard = new apiCard();
        Gson gson = new Gson();
        apiDeck apideck = new apiDeck();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://www.deckofcardsapi.com/api/deck/new/"))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        apideck = gson.fromJson(getResponse.body(), apiDeck.class);
        String deck_id = apideck.getDeck_id();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {

                HttpRequest getCard = HttpRequest.newBuilder()
                        .uri(new URI("https://www.deckofcardsapi.com/api/deck/" + apideck.getDeck_id() + "/draw/?count=1"))
                        .build();

                HttpResponse<String> cardResponse = httpClient.send(getCard, HttpResponse.BodyHandlers.ofString());
                apicard = gson.fromJson(cardResponse.body(), apiCard.class);
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

    public void shuffle() {
        int n = cards.size();
        for (int i = n - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public void addCardToDeck(Card card) {
        cards.add(card);
    }

    public Card drawCard() {
        // Remove and return a card from the deck
        if (cards.isEmpty()) {
            return null; // Handle an empty deck
        }
        int index = (int) (Math.random() * cards.size());
        return cards.remove(index);
    }


}
