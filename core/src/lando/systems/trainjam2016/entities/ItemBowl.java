package lando.systems.trainjam2016.entities;

import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 3/12/2016.
 */
public class ItemBowl extends Item {

    private static final int[][] shape0 = new int[][] {
            { 0, 0, 0, 0 },
            { 1, 1, 1, 1 },
            { 1, 0, 0, 1 },
            { 0, 0, 0, 0 }
    };
    private static final int[][] shape90 = new int[][] {
            { 0, 1, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 0, 1, 0 },
            { 0, 1, 1, 0 }
    };
    private static final int[][] shape180 = new int[][] {
            { 0, 0, 0, 0 },
            { 1, 0, 0, 1 },
            { 1, 1, 1, 1 },
            { 0, 0, 0, 0 }
    };
    private static final int[][] shape270 = new int[][] {
            { 0, 1, 1, 0 },
            { 0, 1, 0, 0 },
            { 0, 1, 0, 0 },
            { 0, 1, 1, 0 }
    };

    public ItemBowl() {
        super(Assets.bowl);
        shape = shape0;
        offsetCellsX = 0;
        offsetCellsY = 1;
        originX = Const.cellSize * 2.0f;
        originY = Const.cellSize * 1.0f;
    }

    @Override
    public void rotateCCW() {
        super.rotateCCW();
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
        super.rotateCW();
        if ((angle -= 90) < 0) {
            angle = 270;
        }

        if      (angle == 0)   shape = shape0;
        else if (angle == 90)  shape = shape90;
        else if (angle == 180) shape = shape180;
        else if (angle == 270) shape = shape270;
    }
}
