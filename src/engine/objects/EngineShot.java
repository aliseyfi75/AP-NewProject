package engine.objects;

import com.google.gson.Gson;
import engine.GameEngineParams;

import java.io.PrintStream;
import java.util.Scanner;

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

    public void save(PrintStream p){
        Gson gson = new Gson();
        p.println(gson.toJson(this));
    }
    public static EngineShot load(Scanner s) {
        Gson gson = new Gson();
        return gson.fromJson(s.nextLine(), EngineShot.class);
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