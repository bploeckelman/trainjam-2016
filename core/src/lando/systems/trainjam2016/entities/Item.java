package lando.systems.trainjam2016.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 3/10/2016.
 * TODO: probably want to separate grid x,y (int) from render x,y (float) to enable smooth movement between grid cells
 */
public abstract class Item {

    int           x, y;
    int           angle;
    int[][]       shape;
    TextureRegion texture;

    public Item(TextureRegion texture) {
        this.x       = 0;
        this.y       = 0;
        this.angle   = 0;
        this.texture = texture;
    }

    public abstract void rotateCCW();
    public abstract void rotateCW();

    public boolean isPointInside(float pointX, float pointY) {
        if (shape == null) return false;

        // If the specified point is inside at least one cell for this item, then the point is inside the item
        for (int iy = 0; iy < shape.length; ++iy) {
            for (int ix = 0; ix < shape[0].length; ++ix) {
                if (shape[iy][ix] == 0) continue;

                float minX = (x + ix) * Const.cellSize;
                float minY = (y + iy) * Const.cellSize;
                float maxX = minX + Const.cellSize;
                float maxY = minY + Const.cellSize;
                if (pointX >= minX && pointX <= maxX
                 && pointY >= minY && pointY <= maxY) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveToX(int x) {
        this.x = x;
    }

    public void moveToY(int y) {
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        float width = texture.getRegionWidth();
        float height = texture.getRegionHeight();
        batch.draw(texture, x, y, width / 2f, height / 2f, width, height, 1f, 1f, angle);
        // TODO: draw grid shape as overlay
    }

}
