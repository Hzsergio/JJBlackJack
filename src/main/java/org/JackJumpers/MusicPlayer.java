package org.JackJumpers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
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

}
