package engine.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import engine.GameEngineParams;

abstract public class EngineObject {

    Gson gson = new Gson();

    protected GameEngineParams gameEngineParams;

    public EngineObject(GameEngineParams gameEngineParams) {
        this.gameEngineParams = gameEngineParams;
    }

    abstract public double getX(long time);

    abstract public double getY(long time);

    public boolean isDeleted(long time) {
        return false;
    }

    public String saveObject(String json) {
        json = json + gson.toJson(this);
        return json;
    }

}
