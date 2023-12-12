package org.JackJumpers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The MusicPlayer class manages the background music and various sound effects for the game.
 */
public class MusicPlayer {

    private static Clip music;

    /**
     * Toggles the background music between play and pause.
     *
     * @return True if music is now playing, false if paused.
     */
    static boolean toggleMusic() {
        // If music clip is running, stop it; otherwise, start playing
        if (music == null || !music.isRunning()) {
            playMusic();
            return true;
        } else {
            stopMusic();
            return false;
        }
    }

    /**
     * Plays the background music.
     */
    static void playMusic() {
        File musicPath = new File("resources/casinomusic.wav");
        try {
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                music = AudioSystem.getClip();
                music.open(audioInput);
                music.start();
            } else {
                System.out.println("Can't find the file");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops the background music.
     */
    private static void stopMusic() {
        if (music != null && music.isRunning()) {
            music.stop();
        }
    }

    /**
     * Plays a specified sound effect.
     *
     * @param sound The sound effect file to be played.
     * @throws UnsupportedAudioFileException If the audio file format is not supported.
     * @throws IOException                   If an I/O error occurs.
     * @throws LineUnavailableException      If a line matching the requested parameters cannot be opened.
     */
    private static void playSound(File sound) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (sound.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } else {
            System.out.println("Can't find the file");
        }
    }

    /**
     * Plays the sound effect for betting.
     */
    public static void betSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/poker-chip.wav");
        playSound(sound);
    }

    /**
     * Plays the sound effect for shuffling cards.
     */
    public static void shuffleSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/shuffle-cards.wav");
        playSound(sound);
    }

    /**
     * Plays the sound effect for winning.
     */
    public static void winSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/win.wav");
        playSound(sound);
    }

    /**
     * Plays the sound effect for losing.
     */
    public static void loseSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/lose.wav");
        playSound(sound);
    }

    /**
     * Plays the sound effect for drawing a card.
     */
    public static void cardSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/taking-card.wav");
        playSound(sound);
    }
}
