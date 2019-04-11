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

//    void fireShot(ui.GamePanel panel, JLabel temperatureLabel) {
//        ui.objects.Shot s = new ui.objects.Shot();
//        s.setBounds(x + 40, y, 10, 10);
//        panel.add(s);
//        temperature += 5;
//        temperatureLabel.setText("temperature = " + temperature);
//    }
//
//    void fireBomb(ui.GamePanel panel) {
//        ui.objects.Bomb b = new ui.objects.Bomb();
//        b.setBounds(x + 40, y, 40, 40);
//        panel.add(b);
//    }
//
//    long getTemperature() {
//        return temperature;
//    }
//
//    void setTemperature(long temperature) {
//        this.temperature = temperature;
//    }
}
