package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Settings extends JFrame {
    private myPanel panel;
    private myPanel lastPanel;
    private JLabel welcomeLabel;

    private JButton back;

    final private int width = 1280;
    final private int height = 720;

    final private int horizontalMargin = 80;
    final private int verticalMargin = 20;
    final private int buttonHeight = 40;
    final private int smallButtonWidth = 80;
    final private int bigButtonWidth = 3 * smallButtonWidth / 2;

    Settings(myPanel lp) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((dim.width - width) / 2, (dim.height - height) / 2, width, height);

        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("resources/chicken-invaders-hd-wallpaper-1280*720.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new myPanel(myImage);
        lastPanel = lp;
        setContentPane(panel);
        setResizable(false);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm();
            }
        });
        initializeButtons();
    }

    private void initializeButtons() {
        back = new JButton("بازگشت");
        back.setBounds(horizontalMargin, height - 2 * verticalMargin - buttonHeight, smallButtonWidth, buttonHeight);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                lastPanel.setVisible(true);
            }
        });
        add(back);
        setVisible(true);

    }

    private void exitForm() {

    }
}
