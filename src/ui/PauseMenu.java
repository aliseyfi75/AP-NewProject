package ui;

import engine.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class PauseMenu extends JFrame {
    private GamePanel panel;
    private GamePanel PanelGame;
    private myPanel playerMenuPanel;
    private JLabel welcomeLabel;

    private JButton resumeButton;
    private JButton exitButton;

    final private int width = 300;
    final private int height = 300;

    final private int horizontalMargin = 80;
    final private int verticalMargin = 20;
    final private int buttonHeight = 40;
    final private int smallButtonWidth = 80;
    final private int bigButtonWidth = 3 * smallButtonWidth / 2;

    PauseMenu(GamePanel lp, GameEngine gameEngine, myPanel myPanel) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((dim.width - width) / 2, (dim.height - height) / 2, width, height);

        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("resources/chicken-invaders-hd-wallpaper-1280*720.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new GamePanel(myImage);
        PanelGame = lp;
        setContentPane(panel);
        setResizable(false);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(gameEngine);
            }
        });
        playerMenuPanel = myPanel;
        initializeButtons(gameEngine);
    }

    private void initializeButtons(GameEngine gameEngine) {
        resumeButton = new JButton("ادامه");
        resumeButton.setBounds(horizontalMargin + 30, height - 2 * verticalMargin - 5*buttonHeight, smallButtonWidth, buttonHeight);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                PanelGame.setVisible(true);
                gameEngine.resumeGame();
            }
        });
        add(resumeButton);

        exitButton = new JButton(("خروج"));
        exitButton.setBounds(horizontalMargin + 30, height - 2 * verticalMargin - 3*buttonHeight, smallButtonWidth, buttonHeight);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                playerMenuPanel.setVisible(true);
                PanelGame.setVisible(false);
            }
        });
        add(exitButton);

        setVisible(true);

    }

    private void exitForm(GameEngine gameEngine) {
        gameEngine.resumeGame();
        setVisible(false);
        dispose();
        PanelGame.setVisible(true);
    }

}