package engine.objects;

import engine.GameEngineParams;
import org.json.simple.JSONObject;

abstract public class EngineObject {

    protected GameEngineParams gameEngineParams;

    public EngineObject(GameEngineParams gameEngineParams) {
        this.gameEngineParams = gameEngineParams;
    }

    abstract public double getX(long time);

    abstract public double getY(long time);

    public boolean isDeleted(long time) {
        return false;
    }

    public JSONObject toJson(long time){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("X", getX(time));
        jsonObject.put("Y", getY(time));
        return jsonObject;
    }
}
