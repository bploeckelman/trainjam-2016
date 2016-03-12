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

    public static enum Type {
        APPLE, SOUP, EGGS, BREAD, MELON;
        public static int NUM_TYPES() { return 5; }
    }

    public Vector2 pos;
    public int     cellX, cellY;
    int            angle;
    public int[][] shape;
    TextureRegion  texture;
    int            offsetCellsX;
    int            offsetCellsY;
    float          originX;
    float          originY;
    public float   conveyorTime;

    public Item(TextureRegion texture) {
        this.pos = new Vector2();
        this.cellX = 0;
        this.cellY = 0;
        this.angle = 0;
        this.texture = texture;
    }

    public void setConveyorTime(float t) {
        conveyorTime = t;
    }

    public abstract void rotateCCW();
    public abstract void rotateCW();

    public void update(float dt) {}

    public static Item createNewRandomItem() {
        Item item;
        switch (MathUtils.random(Type.NUM_TYPES() - 1)) {
            default:
            case 0: item = new ItemApple(); break;
            case 1: item = new ItemSoup();  break;
            case 2: item = new ItemEggs();  break;
            case 3: item = new ItemBread();  break;
            case 4: item = new ItemMelon();  break;
        }
        return item;
    }

    public void moveToCell() {
        Tween.to(pos, Vector2Accessor.XY, 0.1f)
             .target(cellX * Const.cellSize, cellY * Const.cellSize)
             .start(Assets.tween);
    }

    public void moveToCell(int newCellX, int newCellY) {
        cellX = newCellX;
        cellY = newCellY;
        moveToCell();
    }

    public boolean overlaps (Item that) {
        for (int iy = 0; iy < shape.length; ++iy) {
            for (int ix = 0; ix < shape[0].length; ++ix) {
                if (shape[iy][ix] != 0 && that.isCellInside(cellX + ix, cellY + iy)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveDown (Bag bag) {
        for (Item item : bag.placedItems) {
            if (item == this) continue;
            for (int iy = 0; iy < shape.length; ++iy) {
                for (int ix = 0; ix < shape[0].length; ++ix) {
                    if (shape[iy][ix] != 0 && item.isCellInside(cellX + ix, cellY + iy - 1)) {
                        return false;
                    }
                }
            }
        }
        for (Item item : bag.droppingItems) {
            if (item == this) continue;
            for (int iy = 0; iy < shape.length; ++iy) {
                for (int ix = 0; ix < shape[0].length; ++ix) {
                    if (shape[iy][ix] != 0 && item.isCellInside(cellX + ix, cellY + iy - 1)) {
                        return false;
                    }
                }
            }
        }
        for (int x = bag.minCellX; x <= bag.maxCellX; x++) {
            if (this.isCellInside(x, bag.minCellY)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCellInside(int aCellX, int aCellY) {
        int relX = aCellX - cellX;
        int relY = aCellY - cellY;
        return 0 <= relY && relY < shape.length
            && 0 <= relX && relX < shape[0].length
            && shape[relY][relX] != 0;
    }

    public boolean isInsideCellRegion(int cellMinX, int cellMinY, int cellMaxX, int cellMaxY) {
        for (int iy = 0; iy < shape.length; ++iy) {
            for (int ix = 0; ix < shape[0].length; ++ix) {
                if (shape[iy][ix] != 0
                 && (cellX + ix < cellMinX || cellX + ix > cellMaxX || cellY + iy < cellMinY || cellY + iy > cellMaxY)) {
                    return false;
                }
            }
        }
        return true;
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

    public void moveCornerTo(float x, float y) {
        pos.set(x, y);
        cellX = MathUtils.round(pos.x / Const.cellSize);
        cellY = MathUtils.round(pos.y / Const.cellSize);
    }

    public void moveTo(int x, int y) {
        pos.set(x - (shape.length * Const.cellSize) / 2f,
                y - (shape[0].length * Const.cellSize) / 2f);
        cellX = MathUtils.round(pos.x / Const.cellSize);
        cellY = MathUtils.round(pos.y / Const.cellSize);
    }

    public void moveToX(int x) {
        pos.x = x - (shape.length * Const.cellSize) / 2f;
        cellX = MathUtils.round(pos.x / Const.cellSize);
    }

    public void moveToY(int y) {
        pos.y = y - (shape[0].length * Const.cellSize) / 2f;
        cellY = MathUtils.round(pos.y / Const.cellSize);
    }

    public void render(SpriteBatch batch) {
        float width  = texture.getRegionWidth();
        float height = texture.getRegionHeight();

        batch.draw(texture, pos.x + offsetCellsX * Const.cellSize, pos.y + offsetCellsY * Const.cellSize, originX, originY, width, height, 1f, 1f, angle);

        for (int y = 0; y < shape.length; ++y) {
            for (int x = 0; x < shape[0].length; ++x) {
                if (shape[y][x] == 0) continue;
                float minX = (cellX + x) * Const.cellSize;
                float minY = (cellY + y) * Const.cellSize;
                batch.setColor(0f, 0.8f, 0f, 0.5f);
                batch.draw(Assets.whitePixelTexture, minX, minY, Const.cellSize, Const.cellSize);
            }
        }
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void renderGhost(SpriteBatch batch, Vector2 origPos) {
        float width  = texture.getRegionWidth();
        float height = texture.getRegionHeight();

        batch.setColor(1f, 1f, 1f, 0.5f);
        batch.draw(texture, origPos.x + offsetCellsX * Const.cellSize, origPos.y + offsetCellsY * Const.cellSize, originX, originY, width, height, 1f, 1f, angle);
        batch.setColor(1f, 1f, 1f, 1f);
    }

}
