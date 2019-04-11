package ui.objects;

import javax.swing.*;

public class Shot extends UiObject {
    public Shot(int reference) {
        super(reference);
        setIcon(new ImageIcon("resources/shot.png"));
        setSize(10, 10);
    }

    public void moveToPoint(int x, int y) {
        setLocation(x - getWidth() / 2, y - getHeight() / 2);
    }
}
