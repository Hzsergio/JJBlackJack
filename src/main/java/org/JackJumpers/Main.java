package org.JackJumpers;

import javax.swing.*;

/**
 * The main class to launch the BlackJack game.
 */
public class Main {

    /**
     * The entry point for the BlackJack game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Invokes the Menu class on the event dispatch thread
        SwingUtilities.invokeLater(Menu::new);
    }
}
