package org.JackJumpers;

import javax.swing.*;

public class CustomIcon extends JFrame {

    public CustomIcon() {
        String iconPath = "resources/chip.png";
        ImageIcon customIcon = new ImageIcon(iconPath);
        setIconImage(customIcon.getImage());
    }

    public void setCustomIcon(JFrame frame){
        String iconPath = "resources/chip.png";
        ImageIcon customIcon = new ImageIcon(iconPath);
        frame.setIconImage(customIcon.getImage());
    }
}