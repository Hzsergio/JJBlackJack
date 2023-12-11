package org.JackJumpers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    private static Clip clip;

    static boolean toggleMusic() {
        // If clip is running, stop it
        if (clip == null || !clip.isRunning()) {
            // If clip is null or not running, start playing
            playMusic();
            return true;
        } else stopMusic();
        return false;
    }
     static void playMusic() {
        File musicPath = new File("resources/casinomusic.wav");
        try {
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find the file");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    private static void playSound(File sound) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (sound.exists()) {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } else {
            System.out.println("Can't find the file");
        }
    }

    public static void betSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/poker-chip.wav");
        playSound(sound);


    }
    public static void shuffleSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/shuffle-cards.wav");
        playSound(sound);
    }

    public static void winSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/win.wav");
        playSound(sound);
    }

    public static void loseSound() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        File sound = new File("resources/lose.wav");
        playSound(sound);
    }
}
