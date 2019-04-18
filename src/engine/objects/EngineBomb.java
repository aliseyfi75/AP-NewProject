package engine.objects;

import engine.GameEngineParams;

public class EngineBomb extends EngineObject {
    private long initiationTime;
    private long x;
    private long y;
    private double teta;
    private long explotionTime;


    public EngineBomb(GameEngineParams gameEngineParams, long initiationTime, int x, int y) {
        super(gameEngineParams);
        this.initiationTime = initiationTime;
        this.x = x;
        this.y = y;
        this.teta = Math.atan((this.y - gameEngineParams.getScreenHeight() / 2.0) / (this.x - gameEngineParams.getScreenWidth() / 2.0));
        this.explotionTime = (long) (initiationTime + (Math.sqrt((Math.pow(this.x - gameEngineParams.getScreenWidth() / 2.0, 2)) + (Math.pow(this.y - gameEngineParams.getScreenHeight() / 2.0, 2)))) / gameEngineParams.getBombSpeed());
    }

    public double getX(long time) {
        if (this.x >= gameEngineParams.getScreenWidth()/2.0)
            return this.x - gameEngineParams.getBombSpeed() * Math.cos(this.teta) * (Math.min(time, explotionTime) - this.initiationTime);
        else
            return this.x + gameEngineParams.getBombSpeed() * Math.cos(this.teta) * (Math.min(time, explotionTime) - this.initiationTime);
    }

    public double getY(long time) {
        if (this.x >= gameEngineParams.getScreenWidth()/2.0)
            return this.y - gameEngineParams.getBombSpeed() * Math.sin(this.teta) * (Math.min(time, explotionTime) - this.initiationTime);
        else
            return this.y + gameEngineParams.getBombSpeed() * Math.sin(this.teta) * (Math.min(time, explotionTime) - this.initiationTime);

    }

    @Override
    public boolean isDeleted(long time) {
        return time > this.explotionTime;
    }

}