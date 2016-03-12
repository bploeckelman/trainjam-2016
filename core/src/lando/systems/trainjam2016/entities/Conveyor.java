package lando.systems.trainjam2016.entities;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.primitives.MutableFloat;
import lando.systems.trainjam2016.utils.Assets;
import com.badlogic.gdx.math.Vector2;
import lando.systems.trainjam2016.utils.Const;

/**
 * Created by mtolly on 3/11/16.
 */
public class Conveyor extends Item {
    static final float startTravelTime = 20f;
    float        currentTime;
    MutableFloat travelTime;

    public Conveyor() {
        super(Assets.conveyor);
        cellX = 0;
        cellY = 0;
        pos.set(0, 0);
        shape = new int[][] {};
        currentTime = 0f;

        // Time required for an item to traverse the entire conveyor
        travelTime = new MutableFloat(startTravelTime);
        Assets.conveyorLoop.loop(0.5f);
    }

    public Vector2 getItemPosition(Item item) {
        float age = currentTime - item.conveyorTime;
        float dx = -(item.shape[0].length * Const.cellSize) / 2f;
        float dy = -(item.shape.length * Const.cellSize) / 2f;

        // moves the center of an item between these points:
        // (0,195)
        // (395,195)
        // (395,720)
        // (825,720)
        // (825,550)

        float totalDistance = 395f + 525f + 430f + 170f;
        float leg1 = (395f / totalDistance) * travelTime.floatValue();
        float leg2 = (525f / totalDistance) * travelTime.floatValue();
        float leg3 = (430f / totalDistance) * travelTime.floatValue();
        float leg4 = (170f / totalDistance) * travelTime.floatValue();
        Vector2 ret;
        if (age < leg1) {
            ret = new Vector2((age / leg1) * 395f + dx, 195f + dy);
        } else if (age < leg1 + leg2) {
            ret = new Vector2(395f + dx, 195f + ((age - leg1) / leg2) * 525f + dy);
        } else if (age < leg1 + leg2 + leg3) {
            ret = new Vector2(395f + ((age - leg1 - leg2) / leg3) * 430f + dx, 720f + dy);
        } else if (age < leg1 + leg2 + leg3 + leg4) {
            ret = new Vector2(825f + dx, 720f - ((age - leg1 - leg2 - leg3) / leg4) * 170f + dy);
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

    public void speedUp() {
        final float numBags = 10f;
        final float minTime = 2f;
        final float speedUpAmount =  (startTravelTime - minTime) / numBags;
        final float currentTravelTime = travelTime.floatValue();
        float newTravelTime = currentTravelTime - speedUpAmount;
        if (newTravelTime < minTime) {
            newTravelTime = minTime;
        }

        Tween.to(travelTime, -1, 1f)
                .target(newTravelTime)
                .start(Assets.tween);
    }
}
