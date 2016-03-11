package lando.systems.trainjam2016.entities;

import lando.systems.trainjam2016.utils.Assets;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mtolly on 3/11/16.
 */
public class Conveyor extends Item {
    float currentTime;

    public Conveyor() {
        super(Assets.conveyor);
        cellX = 0;
        cellY = 0;
        pos.set(0, 0);
        shape = new int[][] {};
        currentTime = 0;
    }

    public Vector2 getPosition(float dt) {
        float age = currentTime - dt;
        return new Vector2();
    }

    public void update(float dt) {
        currentTime = dt;
    }

    @Override
    public void rotateCCW() {}

    @Override
    public void rotateCW() {}
}
