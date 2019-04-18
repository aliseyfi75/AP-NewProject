package ui.objects;

import javax.swing.*;

public class Spaceship extends UiObject {
    private long temperature;

    public Spaceship(int reference) {
        super(reference);
        setIcon(new ImageIcon("resources/spaceship-resized.png"));
        temperature = 0;
        setSize(getIcon().getIconWidth(), getIcon().getIconHeight());

    }

    public void moveToPoint(int x, int y) {
        setLocation(x - getWidth() / 2, y - getHeight() / 2);
    }
}
