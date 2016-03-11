package lando.systems.trainjam2016.entities;

import com.badlogic.gdx.Gdx;
import lando.systems.trainjam2016.utils.Assets;

/**
 * Brian Ploeckelman created on 3/11/2016.
 */
public class ItemSoup extends Item {

//    private static final int[][] shape0 = new int[][] {
//            { 1 },
//            { 1 }
//    };
//    private static final int[][] shape90 = new int[][] {
//            { 1, 1 }
//    };
//    private static final int[][] shape180 = shape0;
//    private static final int[][] shape270 = shape90;
    private static final int[][] shape0 = new int[][] {
            { 0, 1, 0 },
            { 0, 1, 0 },
            { 0, 0, 0 }
    };
    private static final int[][] shape90 = new int[][] {
            { 0, 0, 0 },
            { 1, 1, 0 },
            { 0, 0, 0 }
    };
    private static final int[][] shape180 = new int[][] {
            { 0, 0, 0 },
            { 0, 1, 0 },
            { 0, 1, 0 }
    };
    private static final int[][] shape270 = new int[][] {
            { 0, 0, 0 },
            { 0, 1, 1 },
            { 0, 0, 0 }
    };


    public ItemSoup() {
        super(Assets.soup);
        shape = shape0;
    }

    @Override
    public void rotateCCW() {
        if ((angle += 90) > 270) {
            angle = 0;
        }

        if      (angle == 0)   shape = shape0;
        else if (angle == 90)  shape = shape90;
        else if (angle == 180) shape = shape180;
        else if (angle == 270) shape = shape270;
    }

    @Override
    public void rotateCW() {
        if ((angle -= 90) < 0) {
            angle = 270;
        }

        if      (angle == 0)   shape = shape0;
        else if (angle == 90)  shape = shape90;
        else if (angle == 180) shape = shape180;
        else if (angle == 270) shape = shape270;
    }

}
