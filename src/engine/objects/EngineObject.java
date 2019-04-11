package engine.objects;

import engine.GameEngineParams;

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
}
