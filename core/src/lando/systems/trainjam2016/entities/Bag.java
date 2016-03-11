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
    int minCellX, minCellY;
    int maxCellX, maxCellY;

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
        // TODO: progress dropped items until they are 'placed'
    }

    public void dropItem(Item item) {
        // TODO: place on dropped item list
    }

    public boolean placeItem(Item item) {
        if (!isInBag(item)) return false;

        for (Item placedItem : placedItems) {
            if (item.overlaps(placedItem)) {
                return false;
            }
        }

        placedItems.add(item);
        return true;
    }

    public boolean isInBag(Item item) {
        return item.isInsideCellRegion(minCellX, minCellY, maxCellX, maxCellY);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y);
        for (Item item : placedItems) {
            item.render(batch);
        }
        for (Item item : droppingItems) {
            item.render(batch);
        }
    }

    @Override
    public void rotateCCW() {}

    @Override
    public void rotateCW() {}

}
