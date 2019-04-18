package engine.objects;

import engine.GameEngineParams;

public class EngineSpaceship extends EngineObject {
    private int x;
    private int y;
    private long lastShootAt = 0;
    private double lastTemperature = 0;
    private int numberOfBombs = 5;

    public EngineSpaceship(GameEngineParams gameEngineParams, int x, int y) {
        super(gameEngineParams);
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX(long time) {
        return this.x;
    }

    @Override
    public double getY(long time) {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getTemperature(long time) {
        return Math.max(lastTemperature - (Math.max(time - lastShootAt, 0)) * gameEngineParams.getDecreaseTemperatureConstant(), 0);
    }

    public synchronized EngineShot shoot(long time) {
        if (lastShootAt > time) {
            throw new RuntimeException();
        }

        double previousTemperature = getTemperature(time);
        if (lastTemperature >= gameEngineParams.getMaxTemperature() && previousTemperature > 0) {
            return null;
        }

        lastShootAt = time;
        lastTemperature = previousTemperature + gameEngineParams.getIncreaseTemperatureAmount();

        return new EngineShot(this.gameEngineParams, time, this.x, this.y + gameEngineParams.getSpaceShipShotBaias());
    }

    public synchronized EngineBomb bomb(long time) {
        if (numberOfBombs > 0) {
            numberOfBombs --;
            return new EngineBomb(this.gameEngineParams, time, this.x, this.y + gameEngineParams.getSpaceShipShotBaias());
        }
        else
            return null;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }
}
