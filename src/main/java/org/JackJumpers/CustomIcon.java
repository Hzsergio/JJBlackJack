package org.JackJumpers;

import javax.swing.*;

/**
 * A utility class for setting a custom icon for Swing JFrame instances.
 */
public class CustomIcon extends JFrame {

    /**
     * Constructs a new CustomIcon and sets a custom icon for the current JFrame.
     */
    public CustomIcon() {
        setCustomIcon(this);
    }

    /**
     * Sets a custom icon for the specified JFrame.
     *
     * @param frame The JFrame for which the custom icon will be set.
     */
    public void setCustomIcon(JFrame frame) {
        String iconPath = "resources/chip.png";
        ImageIcon customIcon = new ImageIcon(iconPath);
        frame.setIconImage(customIcon.getImage());
    }
}
