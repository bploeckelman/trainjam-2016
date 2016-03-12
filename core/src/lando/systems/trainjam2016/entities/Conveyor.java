package lando.systems.trainjam2016.entities;

import lando.systems.trainjam2016.utils.Assets;
import com.badlogic.gdx.math.Vector2;
import lando.systems.trainjam2016.utils.Const;

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
        currentTime = 0f;
    }

    public Vector2 getItemPosition(Item item) {
        float age = currentTime - item.conveyorTime;
        float dx = -(item.shape[0].length * Const.cellSize) / 2f;
        float dy = -(item.shape.length * Const.cellSize) / 2f;

        // moves the center of an item between these points over 10 seconds:
        // (60,0)
        // (60,660)
        // (820,660)
        // (820,560)

        float totalTime = 100f;
        float totalDistance = 660f + 760f + 100f;
        float leg1 = (660f / totalDistance) * totalTime;
        float leg2 = (760f / totalDistance) * totalTime;
        float leg3 = (100f / totalDistance) * totalTime;
        Vector2 ret;
        if (age < leg1) {
            ret = new Vector2(60f + dx, (age / leg1) * 660f + dy);
        } else if (age < leg1 + leg2) {
            ret = new Vector2(60f + ((age - leg1) / leg2) * 760f + dx, 660f + dy);
        } else if (age < leg1 + leg2 + leg3) {
            ret = new Vector2(820f + dx, 660f - ((age - leg1 - leg2) / leg3) * 100f + dy);
        } else {
            ret = null;
        }
        return ret;
    }

    public void update(float dt) {
        currentTime += dt;
    }

    @Override
    public void rotateCCW() {}

    @Override
    public void rotateCW() {}
}
