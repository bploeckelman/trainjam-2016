package lando.systems.trainjam2016.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.trainjam2016.utils.Assets;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class ItemApple extends Item {

    private static final int[] shape0 = new int[] {
            1
    };

    public ItemApple() {
        super(Assets.apple);
        shape = shape0;
    }

    @Override
    public void rotateCCW() {
        angle += 90;
    }

    @Override
    public void rotateCW() {
        angle -= 90;
    }

}
