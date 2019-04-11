package engine;

public class GameEngineParams {
    private int screenWidth, screenHeight;
    private int spaceshipWidth = 90, spaceshipHeight = 145;
    private int autoShotDelay = 200;
    private double shotSpeed = 0.3; // Pixel per millis
    private double increaseTemperatureAmount = 20;
    private double decreaseTemperatureConstant = 0.04; // temp per millis
    private double maxTemperature = 100;
    private double bombSpeed = 0.3;
    private long systemTimeBaias = 0;

    public GameEngineParams() {
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getSpaceshipWidth() {
        return spaceshipWidth;
    }

    public void setSpaceshipWidth(int spaceshipWidth) {
        this.spaceshipWidth = spaceshipWidth;
    }

    public int getSpaceshipHeight() {
        return spaceshipHeight;
    }

    public void setSpaceshipHeight(int spaceshipHeight) {
        this.spaceshipHeight = spaceshipHeight;
    }

    public int getAutoShotDelay() {
        return autoShotDelay;
    }

    public void setAutoShotDelay(int autoShotDelay) {
        this.autoShotDelay = autoShotDelay;
    }

    public int getSpaceShipShotBaias() {
        return (int) (-getSpaceshipHeight() / 2.3);
    }

    public double getShotSpeed() {
        return shotSpeed;
    }

    public double getIncreaseTemperatureAmount() {
        return increaseTemperatureAmount;
    }

    public double getDecreaseTemperatureConstant() {
        return decreaseTemperatureConstant;
    }

    public void setShotSpeed(double shotSpeed) {
        this.shotSpeed = shotSpeed;
    }

    public void setIncreaseTemperatureAmount(double increaseTemperatureAmount) {
        this.increaseTemperatureAmount = increaseTemperatureAmount;
    }

    public void setDecreaseTemperatureConstant(double decreaseTemperatureConstant) {
        this.decreaseTemperatureConstant = decreaseTemperatureConstant;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getBombSpeed() {
        return bombSpeed;
    }

    public void setBombSpeed(double bombSpeed) {
        this.bombSpeed = bombSpeed;
    }

    public long getSystemTimeBaias() {
        return systemTimeBaias;
    }

    public synchronized void setSystemTimeBaias(long systemTimeBaias) {
        this.systemTimeBaias = systemTimeBaias;
    }

    public long getEngineTime(long time){
        return time + getSystemTimeBaias();
    }
}
