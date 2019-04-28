package engine.objects;

import engine.GameEngineParams;
import org.json.simple.JSONObject;

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

    public long getInitiationTime() {
        return initiationTime;
    }

    public void setInitiationTime(long initiationTime) {
        this.initiationTime = initiationTime;
    }

    @Override
    public boolean isDeleted(long time) {
        return getY(time) < -10;
    }

    @Override
    public JSONObject toJson(long time) {
        JSONObject jsonObject = super.toJson(time);
        jsonObject.put("InitiationTime", getInitiationTime());
        return jsonObject;
    }

    @Override
    public String toString() {
        return "EngineShot{" +
                "initiationTime=" + initiationTime +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}