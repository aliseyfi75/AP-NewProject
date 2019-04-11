package engine;

import engine.objects.EngineBomb;
import engine.objects.EngineShot;
import engine.objects.EngineSpaceship;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameEngine extends Thread implements GameDataProvider {
    private boolean running = true;
    private boolean mouseDown = false;
    private long mouseDownFrom = 0;
    private long mouseDownTo = 0;
    private long pauseTime = 0;
    private boolean isPaused = false;

    private int mouseX = 0, mouseY = 0;

    private long currentCycleTime;
    private long previousCycleTime;

    private GameInterface gamePlay;
    private EngineSpaceship spaceship;

    private GameEngineParams gameEngineParams;

    private ConcurrentHashMap<Integer, EngineShot> engineShots = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, EngineSpaceship> engineSpaceships = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, EngineBomb> engineBombs = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, EngineBomb> getEngineBombs() {
        return engineBombs;
    }

    public GameEngine(GameInterface gamePlay, GameEngineParams gameEngineParams) {
        this.gameEngineParams = gameEngineParams;
        this.gamePlay = gamePlay;

        init();
    }

    private void init() {
        this.spaceship = new EngineSpaceship(this.gameEngineParams, 0, 0);
        this.engineSpaceships.put(this.spaceship.hashCode(), this.spaceship);
    }

    @Override
    public void run() {
        try {
            previousCycleTime = gameEngineParams.getEngineTime(System.currentTimeMillis());
            boolean lastCyclePaused = false;
            while (this.running) {
                try {
                    if (isPaused) {
                        lastCyclePaused = true;
                        Thread.sleep(500);
                        continue;
                    } else if (lastCyclePaused) {
                        long pauseDiff = System.currentTimeMillis() - this.pauseTime;
                        this.gameEngineParams.setSystemTimeBaias(this.gameEngineParams.getSystemTimeBaias() - pauseDiff);
                        lastCyclePaused = false;
                    }
                    currentCycleTime = gameEngineParams.getEngineTime(System.currentTimeMillis());

                    this.updateSpaceships();
                    this.updateShots();
                    this.gamePlay.updateInterface(this);

                    previousCycleTime = currentCycleTime;
                    sleep(10);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        } catch (Exception e) {
//            throw e;
        }

    }

    private void updateSpaceships() {

    }

    private void updateShots() {
        this.engineShots.values().removeIf(shot -> shot.isDeleted(currentCycleTime));

        boolean mouseDown;
        long mouseDownFrom;
        long mouseDownTo;
        synchronized (this) {
            mouseDown = this.mouseDown;
            mouseDownFrom = this.mouseDownFrom;
            mouseDownTo = this.mouseDownTo;
        }

        int autoShotDelay = this.gameEngineParams.getAutoShotDelay();
        if (mouseDown || mouseDownTo > previousCycleTime) {
            int totalToCreate = (int) (((mouseDown ? currentCycleTime : mouseDownTo) - mouseDownFrom) / autoShotDelay);
            int previousCreated = (int) Math.max(((previousCycleTime - mouseDownFrom) / autoShotDelay), 0);

            for (int i = previousCreated; i < totalToCreate; i++) {
                this.createNewShot(mouseDownFrom + autoShotDelay * (i + 1));
            }
        }
    }

    private void createNewShot(long time) {
        EngineShot engineShot = this.spaceship.shoot(time);
        if (engineShot != null) {
            this.engineShots.put(engineShot.hashCode(), engineShot);
        }
    }

    private void createNewBomb(long time) {
        EngineBomb engineBomb = this.spaceship.bomb(time);
        if (engineBomb != null) {
            this.engineBombs.put(engineBomb.hashCode(), engineBomb);
        }
    }

    public void setMouseDown(boolean mouseDown) {
        if (!this.mouseDown && mouseDown) {
            this.mouseDownFrom = this.gameEngineParams.getEngineTime(System.currentTimeMillis());
        } else if (this.mouseDown && !mouseDown) {
            this.mouseDownTo = this.gameEngineParams.getEngineTime(System.currentTimeMillis());
        }
        this.mouseDown = mouseDown;
    }

    public synchronized void mouseClicked(int x, int y) {
        this.mouseDown = false;
        this.setMouseLocation(x, y);
        this.createNewShot(this.gameEngineParams.getEngineTime(System.currentTimeMillis()));
    }

    public synchronized void mouseRightClicked(int x, int y) {
        this.mouseDown = false;
        this.setMouseLocation(x, y);
        this.createNewBomb(this.gameEngineParams.getEngineTime(System.currentTimeMillis()));
    }

    public void setMouseLocation(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;

        this.spaceship.setX(this.mouseX);
        this.spaceship.setY(this.mouseY);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Map<Integer, EngineShot> getEngineShots() {
        return engineShots;
    }

    public Map<Integer, EngineSpaceship> getEngineSpaceships() {
        return engineSpaceships;
    }

    public EngineSpaceship getMySpaceship() {
        return this.spaceship;
    }

    public synchronized void pauseGame() {
        this.isPaused = true;
        this.pauseTime = System.currentTimeMillis();
    }

    public synchronized void resumeGame() {
        this.isPaused = false;
        this.interrupt();
    }
}
