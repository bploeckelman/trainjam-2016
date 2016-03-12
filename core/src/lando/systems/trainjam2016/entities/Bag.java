package lando.systems.trainjam2016.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class Bag extends Item {

    public static final int CELLS_WIDE = 7;
    public static final int CELLS_HIGH = 11;

    public Array<Item> placedItems;
    public Array<Item> droppingItems;

    // Extents for cells that can be filled
    public int minCellX, minCellY;
    public int maxCellX, maxCellY;

    public Bag() {
        super(Assets.bag);
        shape = new int[CELLS_HIGH][CELLS_WIDE];
        cellX = 16;
        cellY = 1;
        minCellX = cellX + 1;
        minCellY = cellY + 1;
        maxCellX = minCellX + CELLS_WIDE - 1;
        maxCellY = minCellY + CELLS_HIGH - 1;
        pos.set(cellX * Const.cellSize, cellY * Const.cellSize);

        placedItems = new Array<Item>();
        droppingItems = new Array<Item>();
    }

    @Override
    public void update(float dt) {
        Array<Item> place = new Array<Item>();
        for (int i = 0; i < droppingItems.size; i++) {
            Item item = droppingItems.get(i);
            if (item.canMoveDown(this)) {
                item.moveToCell(item.cellX, item.cellY - 1);
            } else {
                placedItems.add(item);
                place.add(item);
            }
        }
        droppingItems.removeAll(place, true);
        // TODO: progress dropped items until they are 'placed'
    }

    public boolean canDropItem(Item item) {
        if (!isInBag(item)) return false;

        for (Item placedItem : placedItems) {
            if (item.overlaps(placedItem)) {
                return false;
            }
        }
        for (Item placedItem : droppingItems) {
            if (item.overlaps(placedItem)) {
                return false;
            }
        }

        return true;
    }

    public boolean dropItem(Item item) {
        if (!canDropItem(item)) return false;
        droppingItems.add(item);
        // TODO: fill bag shape with item shape
        return true;
    }

    public boolean cramItem(Item item) {
        item.moveToCell();
        while (item.canMoveDown(this)) item.moveToCell(item.cellX, item.cellY - 1);
        return dropItem(item);
    }

    public boolean isInBag(Item item) {
        return item.isInsideCellRegion(minCellX, minCellY, maxCellX, maxCellY);
    }

    public float getCapacity() {
        int numFilled = 0;
        for (Item item : placedItems) {
            for (int y = 0; y < item.shape.length; ++y) {
                for (int x = 0; x < item.shape[0].length; ++x) {
                    if (item.shape[y][x] != 0) numFilled++;
                }
            }
        }
        return numFilled / (float) (CELLS_WIDE * CELLS_HIGH);
    }

    @Override
    public void render(SpriteBatch batch, boolean isOverBag, boolean canPlaceInBag) {
        batch.draw(Assets.bagGridTexture, pos.x + Const.cellSize, pos.y + Const.cellSize);
        batch.draw(texture, pos.x, pos.y);
        for (Item item : placedItems) {
            item.render(batch, true, true);
        }
        for (Item item : droppingItems) {
            item.render(batch, true, true);
        }
    }

    @Override
    public void rotateCCW() {}

    @Override
    public void rotateCW() {}

}
