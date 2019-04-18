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

public class PlayerMenu extends JFrame {
    private myPanel panel;
    private JLabel welcomeLabel;
    private JButton resumeButton;
    private JButton newGameButton;
    private JButton rankingButton;
    private JButton exitButton;
    private JButton settingButton;
    private JButton aboutUsButton;

    final private int width = 1280;
    final private int height = 720;

    final private int horizontalMargin = 80;
    final private int verticalMargin = 20;
    final private int buttonHeight = 40;
    final private int smallButtonWidth = 80;
    final private int bigButtonWidth = 3 * smallButtonWidth / 2;

    public PlayerMenu() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((dim.width - width) / 2, (dim.height - height) / 2, width, height);
        setTitle("Chicken Invaders");

        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("resources/chicken-invaders-hd-wallpaper-1280*720.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new myPanel(myImage);
        setContentPane(panel);
        setResizable(false);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
        });
        initializeButtons();
    }

    PlayerMenu(String selectedValue) {

    }

    private void initializeButtons() {
        exitButton = new JButton("خروج");
        exitButton.setBounds(horizontalMargin, height - 2 * verticalMargin - buttonHeight, smallButtonWidth, buttonHeight);
        exitButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        add(exitButton);
        settingButton = new JButton("تنظیمات");
        settingButton.setBounds((width - smallButtonWidth) / 2, exitButton.getY(), smallButtonWidth, buttonHeight);
        settingButton.addActionListener(e -> {
            Settings settings = new Settings(panel);
            settings.setVisible(true);
        });
        add(settingButton);
        aboutUsButton = new JButton("درباره ما");
        aboutUsButton.setBounds(width - horizontalMargin - smallButtonWidth, exitButton.getY(), smallButtonWidth, buttonHeight);
        aboutUsButton.addActionListener(e -> {
            AboutUs aboutUs = new AboutUs(panel);
            aboutUs.setVisible(true);
        });
        add(aboutUsButton);
        rankingButton = new JButton("رتبه بندی");
        rankingButton.setBounds((width - bigButtonWidth) / 2, settingButton.getY() - verticalMargin - buttonHeight, bigButtonWidth, buttonHeight);
        add(rankingButton);
        newGameButton = new JButton("شروع بازی جدید");
        newGameButton.setBounds((width - bigButtonWidth) / 2, rankingButton.getY() - verticalMargin - buttonHeight, bigButtonWidth, buttonHeight);
        newGameButton.addActionListener(e -> new GamePlay(panel).start());
        add(newGameButton);
        resumeButton = new JButton("ادامه بازی");
        resumeButton.setBounds((width - bigButtonWidth) / 2, newGameButton.getY() - verticalMargin - buttonHeight, bigButtonWidth, buttonHeight);
        add(resumeButton);
        welcomeLabel = new JLabel("Hello!");
        welcomeLabel.setBounds((width - bigButtonWidth) / 2, resumeButton.getY() - verticalMargin - buttonHeight, bigButtonWidth, buttonHeight);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel);
        setVisible(true);
    }


    private void exitForm(WindowEvent e) {
        //TODO
    }


}
class myPanel extends JPanel {
    private Image image;

    myPanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}