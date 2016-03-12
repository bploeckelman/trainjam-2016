package lando.systems.trainjam2016.entities;

import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 3/11/2016.
 */
public class ItemMelon extends Item {

    private static final int[][] shape0 = new int[][] {
            { 1, 1 },
            { 1, 1 }
    };

    public ItemMelon() {
        super(Assets.melon);
        shape = shape0;

        offsetCellsX = offsetCellsY = 0;
        originX = originY = Const.cellSize;
    }
    @Override
    public void rotateCCW() {
        super.rotateCCW();
        if ((angle += 90) > 270) {
            angle = 0;
        }
    }

    @Override
    public void rotateCW() {
        super.rotateCW();
        if ((angle -= 90) < 0) {
            angle = 270;
        }
    }
}
