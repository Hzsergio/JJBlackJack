package org.JackJumpers;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * An interface for listening to card drawing events in a card game.
 */
public interface CardListener {
    /**
     * Called when a card is drawn during the game.
     *
     * @param card The card that has been drawn.
     * @throws UnsupportedAudioFileException If there is an issue with the audio file format.
     * @throws LineUnavailableException      If a line cannot be opened because it is unavailable.
     * @throws IOException                   If there is an I/O issue during the event.
     */
    void onCardDrawn(Card card) throws UnsupportedAudioFileException, LineUnavailableException, IOException;
}
