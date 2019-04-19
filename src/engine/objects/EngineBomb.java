package engine.objects;

import com.google.gson.Gson;
import engine.GameEngineParams;

import java.io.PrintStream;
import java.util.Scanner;

public class EngineBomb extends EngineObject {
    private long initiationTime;
    private long x;
    private long y;
    private double teta;
    private long explosionTime;


    public EngineBomb(GameEngineParams gameEngineParams, long initiationTime, int x, int y) {
        super(gameEngineParams);
        this.initiationTime = initiationTime;
        this.x = x;
        this.y = y;
        this.teta = Math.atan((this.y - gameEngineParams.getScreenHeight() / 2.0) / (this.x - gameEngineParams.getScreenWidth() / 2.0));
        this.explosionTime = (long) (initiationTime + (Math.sqrt((Math.pow(this.x - gameEngineParams.getScreenWidth() / 2.0, 2)) + (Math.pow(this.y - gameEngineParams.getScreenHeight() / 2.0, 2)))) / gameEngineParams.getBombSpeed());
    }

    public double getX(long time) {
        if (this.x >= gameEngineParams.getScreenWidth()/2.0)
            return this.x - gameEngineParams.getBombSpeed() * Math.cos(this.teta) * (Math.min(time, explosionTime) - this.initiationTime);
        else
            return this.x + gameEngineParams.getBombSpeed() * Math.cos(this.teta) * (Math.min(time, explosionTime) - this.initiationTime);
    }

    public double getY(long time) {
        if (this.x >= gameEngineParams.getScreenWidth()/2.0)
            return this.y - gameEngineParams.getBombSpeed() * Math.sin(this.teta) * (Math.min(time, explosionTime) - this.initiationTime);
        else
            return this.y + gameEngineParams.getBombSpeed() * Math.sin(this.teta) * (Math.min(time, explosionTime) - this.initiationTime);

    }

    @Override
    public boolean isDeleted(long time) {
        return time > this.explosionTime;
    }

    public void save(PrintStream p){
        Gson gson = new Gson();
        p.println(gson.toJson(this));
    }
    public static EngineBomb load(Scanner s){
        Gson gson = new Gson();
        return gson.fromJson(s.nextLine(), EngineBomb.class);
    }

    @Override
    public String toString() {
        return "EngineBomb{" +
                "initiationTime=" + initiationTime +
                ", x=" + x +
                ", y=" + y +
                ", teta=" + teta +
                ", explosionTime=" + explosionTime +
                '}';
    }
}