package ui.objects;

import javax.swing.*;

public class Bomb extends UiObject {
    public Bomb(int reference) {
        super(reference);
        setIcon(new ImageIcon("resources/bomb.png"));
        setSize(40, 40);
    }

    public void moveToPoint(int x, int y) {
        setLocation(x - getWidth() / 2, y - getHeight() / 2);
    }
}
