package lando.systems.trainjam2016.entities;

import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 3/11/2016.
 */
public class ItemEggs extends Item {

    private static final int[][] shape0 = new int[][] {
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
    };
    private static final int[][] shape90 = new int[][] {
            { 0, 0, 0, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 }
    };
    private static final int[][] shape180 = new int[][] {
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
    };
    private static final int[][] shape270 = new int[][] {
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0 },
            { 0, 0, 0, 0, 0 }
    };

    public ItemEggs() {
        super(Assets.eggs);
        shape = shape0;

        offsetCellsX = 1;
        offsetCellsY = 2;
        originX = Const.cellSize;
        originY = Const.cellSize;
    }

    @Override
    public void rotateCCW() {
        if ((angle += 90) > 270) {
            angle = 0;
        }

        if      (angle == 0) {
            shape = shape0;
            offsetCellsX = 1;
            offsetCellsY = 2;
        }
        else if (angle == 90) {
            shape = shape90;
            offsetCellsX = 1;
            offsetCellsY = 1;
        }
        else if (angle == 180) {
            shape = shape180;
            offsetCellsX = 2;
            offsetCellsY = 1;
        }
        else if (angle == 270) {
            shape = shape270;
            offsetCellsX = 2;
            offsetCellsY = 2;
        }
    }

    @Override
    public void rotateCW() {
        if ((angle -= 90) < 0) {
            angle = 270;
        }

        if      (angle == 0) {
            shape = shape0;
            offsetCellsX = 1;
            offsetCellsY = 2;
        }
        else if (angle == 90) {
            shape = shape90;
            offsetCellsX = 1;
            offsetCellsY = 1;
        }
        else if (angle == 180) {
            shape = shape180;
            offsetCellsX = 2;
            offsetCellsY = 1;
        }
        else if (angle == 270) {
            shape = shape270;
            offsetCellsX = 2;
            offsetCellsY = 2;
        }
    }

}
