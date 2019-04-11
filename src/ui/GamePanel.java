package ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Image[] images;
    private int currentBG;

    GamePanel(Image image) {
        images = new Image[1];
        images[0] = image;
        setIgnoreRepaint(false);
    }

    GamePanel(Image[] images) {
        this.images = images;
        currentBG = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBG(g);

    }

    private void drawBG(Graphics g) {
        g.drawImage(images[currentBG], 0, 0, this);
    }

    void nextBG() {
        currentBG = (currentBG + 1) % images.length;
    }
}