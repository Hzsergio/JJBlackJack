package org.JackJumpers;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface CardListener {
    void onCardDrawn(Card card) throws UnsupportedAudioFileException, LineUnavailableException, IOException;
}
