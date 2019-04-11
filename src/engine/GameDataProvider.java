package engine;

import engine.objects.EngineBomb;
import engine.objects.EngineShot;
import engine.objects.EngineSpaceship;

import java.util.Map;

public interface GameDataProvider {
    Map<Integer, EngineShot> getEngineShots();
    Map<Integer, EngineSpaceship> getEngineSpaceships();
    EngineSpaceship getMySpaceship();

    Map<Integer, EngineBomb> getEngineBombs();
}
