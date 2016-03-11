package lando.systems.trainjam2016.entities;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.accessors.Vector2Accessor;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public abstract class Item {

    Vector2       pos;
    int           cellX, cellY;
    int           angle;
    int[][]       shape;
    TextureRegion texture;

    public Item(TextureRegion texture) {
        this.pos = new Vector2();
        this.cellX = 0;
        this.cellY = 0;
        this.angle = 0;
        this.texture = texture;
    }

    public abstract void rotateCCW();
    public abstract void rotateCW();

    public void moveToCell() {
        Tween.to(pos, Vector2Accessor.XY, 0.1f)
             .target(cellX * Const.cellSize, cellY * Const.cellSize)
             .start(Assets.tween);
    }

    public boolean isPointInside(float pointX, float pointY) {
        if (shape == null) return false;

        // If the specified point is inside at least one cell for this item, then the point is inside the item
        for (int iy = 0; iy < shape.length; ++iy) {
            for (int ix = 0; ix < shape[0].length; ++ix) {
                if (shape[iy][ix] == 0) continue;

                float minX = (cellX + ix) * Const.cellSize;
                float minY = (cellY + iy) * Const.cellSize;
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

    public void moveBy(float dx, float dy) {
        pos.set(pos.x + dx, pos.y + dy);
        cellX = MathUtils.round(pos.x / Const.cellSize);
        cellY = MathUtils.round(pos.y / Const.cellSize);
    }

    public void moveTo(int x, int y) {
        pos.set(x - texture.getRegionWidth() / 2f,
                y - texture.getRegionHeight() / 2f);
        cellX = MathUtils.round(pos.x / Const.cellSize);
        cellY = MathUtils.round(pos.y / Const.cellSize);
    }

    public void moveToX(int x) {
        pos.x = x - texture.getRegionWidth() / 2f;
        cellX = MathUtils.round(pos.x / Const.cellSize);
    }

    public void moveToY(int y) {
        pos.y = y - texture.getRegionHeight() / 2f;
        cellY = MathUtils.round(pos.y / Const.cellSize);
    }

    public void render(SpriteBatch batch) {
        float width  = texture.getRegionWidth();
        float height = texture.getRegionHeight();
        batch.draw(texture, pos.x, pos.y, width / 2f, height / 2f, width, height, 1f, 1f, angle);
        // TODO: draw grid shape as overlay
    }

}
