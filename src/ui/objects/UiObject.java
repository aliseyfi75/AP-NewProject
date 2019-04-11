package ui.objects;

import engine.objects.EngineBomb;
import engine.objects.EngineObject;
import engine.objects.EngineShot;
import engine.objects.EngineSpaceship;

import javax.swing.*;

abstract public class UiObject extends JLabel {
    private int reference;

    public UiObject(int reference) {
        this.reference = reference;
    }

    public static UiObject createFromEngineObject(EngineObject engineObject) {
        if (engineObject instanceof EngineShot) {
            return new Shot(engineObject.hashCode());
        } else if (engineObject instanceof EngineSpaceship) {
            return new Spaceship(engineObject.hashCode());
        } else if (engineObject instanceof EngineBomb) {
            return new Bomb(engineObject.hashCode());
        }

        return null;
    }

    public abstract void moveToPoint(int x, int y);

    public int getReference() {
        return this.reference;
    }
}
