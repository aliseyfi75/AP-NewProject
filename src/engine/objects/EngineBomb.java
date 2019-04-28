package engine.objects;

import engine.GameEngineParams;
import org.json.simple.JSONObject;

public class EngineBomb extends EngineObject {
    private long initiationTime;
    private long x;
    private long y;
    private double theta;
    private long explosionTime;


    public EngineBomb(GameEngineParams gameEngineParams, long initiationTime, int x, int y) {
        super(gameEngineParams);
        this.initiationTime = initiationTime;
        this.x = x;
        this.y = y;
        this.theta = Math.atan((this.y - gameEngineParams.getScreenHeight() / 2.0) / (this.x - gameEngineParams.getScreenWidth() / 2.0));
        this.explosionTime = (long) (initiationTime + (Math.sqrt((Math.pow(this.x - gameEngineParams.getScreenWidth() / 2.0, 2)) + (Math.pow(this.y - gameEngineParams.getScreenHeight() / 2.0, 2)))) / gameEngineParams.getBombSpeed());
    }

    public double getX(long time) {
        if (this.x >= gameEngineParams.getScreenWidth()/2.0)
            return this.x - gameEngineParams.getBombSpeed() * Math.cos(this.theta) * (Math.min(time, explosionTime) - this.initiationTime);
        else
            return this.x + gameEngineParams.getBombSpeed() * Math.cos(this.theta) * (Math.min(time, explosionTime) - this.initiationTime);
    }

    public double getY(long time) {
        if (this.x >= gameEngineParams.getScreenWidth()/2.0)
            return this.y - gameEngineParams.getBombSpeed() * Math.sin(this.theta) * (Math.min(time, explosionTime) - this.initiationTime);
        else
            return this.y + gameEngineParams.getBombSpeed() * Math.sin(this.theta) * (Math.min(time, explosionTime) - this.initiationTime);

    }

    public long getInitiationTime() {
        return initiationTime;
    }

    public void setInitiationTime(long initiationTime) {
        this.initiationTime = initiationTime;
    }

    @Override
    public boolean isDeleted(long time) {
        return time > this.explosionTime;
    }

    @Override
    public JSONObject toJson(long time) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("InitiationTime", getInitiationTime());
        return jsonObject;
    }

    @Override
    public String toString() {
        return "EngineBomb{" +
                "initiationTime=" + initiationTime +
                ", x=" + x +
                ", y=" + y +
                ", theta=" + theta +
                ", explosionTime=" + explosionTime +
                '}';
    }
}