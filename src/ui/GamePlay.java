package ui;

import com.google.gson.Gson;
import engine.GameDataProvider;
import engine.GameEngine;
import engine.GameEngineParams;
import engine.GameInterface;
import engine.objects.EngineBomb;
import engine.objects.EngineObject;
import engine.objects.EngineShot;
import engine.objects.EngineSpaceship;
import ui.objects.Bomb;
import ui.objects.Shot;
import ui.objects.Spaceship;
import ui.objects.UiObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class GamePlay extends JFrame implements GameInterface {

    private myPanel myPanel;

    private GamePanel panel;

    private BufferedImage cursorImg;
    private Cursor blankCursor;
    private GameEngine gameEngine;

    private HashMap<Integer, Shot> shots = new HashMap<>();
    private HashMap<Integer, Spaceship> spaceships = new HashMap<>();
    private HashMap<Integer, Bomb> bombs = new HashMap<>();
    private GameEngineParams gameEngineParams = new GameEngineParams();

    private JProgressBar temperatureBar = new JProgressBar();
    private JLabel bombCounter = new JLabel();

    private Player player;

    String json = "";

    final private int width = 1280;
    final private int height = 720;

    GamePlay(Player player, ui.myPanel panel) {
        this.player = player;
        init();
        cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        myPanel = panel;
    }

    private void init() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((dim.width - width) / 2, (dim.height - height) / 2, width, height);
        setTitle("Playing Chicken Invaders");

        // add more background pics
        BufferedImage[] bgs = new BufferedImage[1];
        try {
            bgs[0] = ImageIO.read(new File("resources/chicken-invaders-hd-wallpaper-0.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new GamePanel(bgs);
        setContentPane(panel);
        setResizable(false);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitForm(e);
            }
        });
        temperatureBar.setBounds(10, 10, 300, 20);
        temperatureBar.setValue(0);
        temperatureBar.setUI(new MyProgressUI());
        temperatureBar.setForeground(Color.green);
        panel.add(temperatureBar);

        bombCounter.setText("remaining bombs : " + 3);
        bombCounter.setBounds(50,600,300,100);
        bombCounter.setForeground(Color.green);
        panel.add(bombCounter);

        setVisible(true);
    }

    @Override
    public void updateInterface(GameDataProvider gameEngine) {
        long time = gameEngineParams.getEngineTime(System.currentTimeMillis());

        Map<Integer, EngineShot> engineShots = gameEngine.getEngineShots();
        this.updateObjects(time, engineShots, this.shots);

        Map<Integer, EngineSpaceship> engineSpaceships = gameEngine.getEngineSpaceships();
        this.updateObjects(time, engineSpaceships, this.spaceships);

        Map<Integer, EngineBomb> engineBombs = gameEngine.getEngineBombs();
        this.updateObjects(time, engineBombs, this.bombs);

        temperatureBar.setValue((int) gameEngine.getMySpaceship().getTemperature(time));
        bombCounter.setText("remaining bombs : " + gameEngine.getMySpaceship().getNumberOfBombs());

        this.revalidate();
        this.repaint();
        panel.revalidate();
        panel.repaint();
    }

    private void updateObjects(long time, Map engineObjectMap, Map uiObjectMap) {
        HashSet<Integer> updatedObjects = new HashSet<>();
        for (Object obj : engineObjectMap.values()) {
            EngineObject engineObject = (EngineObject) obj;
            UiObject uiObject = (UiObject) uiObjectMap.get(engineObject.hashCode());
            if (uiObject == null) {
                uiObject = UiObject.createFromEngineObject(engineObject);
                uiObjectMap.put(engineObject.hashCode(), uiObject);
                panel.add(uiObject);
            }
            updatedObjects.add(uiObject.getReference());
            uiObject.moveToPoint((int) engineObject.getX(time), (int) engineObject.getY(time));
        }


        Iterator iterator = uiObjectMap.values().iterator();
        while (iterator.hasNext()) {
            UiObject uiObject = (UiObject) iterator.next();
            if (!updatedObjects.contains(uiObject.getReference())) {
                panel.remove(uiObject);
                iterator.remove();
            }
        }
    }

    // saving objects

//    private String saveObjects(long time, Map engineObjectMap, Map uiObjectMap) {
//    }

    private class PanelMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            GamePlay.this.gameEngine.setMouseLocation(e.getX(), e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            GamePlay.this.gameEngine.setMouseLocation(e.getX(), e.getY());
        }
    }

    private class PanelMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == 1)
                GamePlay.this.gameEngine.mouseClicked(e.getX(), e.getY());
            else if (e.getButton() == 3)
                GamePlay.this.gameEngine.mouseRightClicked(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            GamePlay.this.gameEngine.setMouseDown(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            GamePlay.this.gameEngine.setMouseDown(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class PanelComponentListener implements ComponentListener {
        @Override
        public void componentResized(ComponentEvent e) {
//            ui.GamePlay.this.gameEngine.setScreenSize(getWidth(), getHeight());
        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }

    private class PanelKeyboardListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                gameEngine.pauseGame();
                PauseMenu pauseMenu = new PauseMenu(panel, gameEngine, myPanel);
                pauseMenu.setVisible(true);
            }
        }
    }

    void start() {
        gameEngineParams.setScreenWidth(getWidth());
        gameEngineParams.setScreenHeight(getHeight());


        gameEngine = new GameEngine(this, gameEngineParams);

        gameEngine.start();

        addComponentListener(new PanelComponentListener());
        addMouseMotionListener(new PanelMouseMotionListener());
        addMouseListener(new PanelMouseListener());
        addKeyListener(new PanelKeyboardListener());
        init();
        panel.setCursor(blankCursor);
    }

    private void exitForm(WindowEvent e) {
        gameEngine.setRunning(false);

        // saveObjects(..)
    }

    class MyProgressUI extends BasicProgressBarUI {
        Rectangle r = new Rectangle();
        @Override
        protected void paintIndeterminate(Graphics g, JComponent c) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            r = getBox(r);
            g.setColor(progressBar.getForeground());
            g.fillOval(r.x, r.y, r.width, r.height);
        }
    }
}
