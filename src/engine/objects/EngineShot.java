package engine.objects;

import engine.GameEngineParams;

public class EngineShot extends EngineObject {
    private long initiationTime;
    private long x;
    private long y;


    public EngineShot(GameEngineParams gameEngineParams, long initiationTime, int x, int y) {
        super(gameEngineParams);
        this.initiationTime = initiationTime;
        this.x = x;
        this.y = y;
    }

    public double getX(long time) {
        return this.x;
    }

    public double getY(long time) {
        return y - this.gameEngineParams.getShotSpeed() * (time - this.initiationTime);
    }

    @Override
    public boolean isDeleted(long time) {
        return getY(time) < -10;
    }
}