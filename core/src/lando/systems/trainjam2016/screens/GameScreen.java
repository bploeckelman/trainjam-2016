package lando.systems.trainjam2016.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import lando.systems.trainjam2016.TrainJam2016;
import lando.systems.trainjam2016.entities.Item;
import lando.systems.trainjam2016.entities.ItemApple;
import lando.systems.trainjam2016.entities.ItemSoup;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.Utils;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class GameScreen extends BaseScreen {

    Array<Item> items;
    Item        selectedItem;
    int         originalCellX, originalCellY;
    Vector3     firstTouch;
    Vector3     thisTouch;
    Vector2     dragDist;

    public GameScreen() {
        super();
        Utils.glClearColor(Const.bgColor);
        Gdx.input.setInputProcessor(this);

        items = new Array<Item>();
        items.add(new ItemApple());
        items.add(new ItemSoup());

        items.get(1).moveTo(80, 80);
        items.get(1).moveToCell();

        firstTouch = new Vector3();
        thisTouch = new Vector3();
        dragDist = new Vector2();
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            TrainJam2016.game.screen = new MenuScreen();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (int y = 0; y < camera.viewportHeight; y += Const.cellSize) {
            for (int x = 0; x < camera.viewportWidth; x += Const.cellSize) {
                batch.draw(Assets.grid, x, y, Const.cellSize, Const.cellSize);
            }
        }

        for (Item item : items) {
            item.render(batch);
        }
        batch.end();
    }

    // ------------------------------------------------------------------------
    // InputAdapter Overrides
    // ------------------------------------------------------------------------

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        firstTouch.set(screenX, screenY, 0);
        camera.unproject(firstTouch);

        for (Item item : items) {
            if (item.isPointInside(firstTouch.x, firstTouch.y)) {
                selectedItem = item;
                originalCellX = item.cellX;
                originalCellY = item.cellY;
                break;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (selectedItem != null) {
            thisTouch.set(screenX, screenY, 0);
            camera.unproject(thisTouch);

            // If the selected item didn't actually move, just rotate it, otherwise slide/clamp it to the nearest cell
            dragDist.set(firstTouch.x - thisTouch.x, firstTouch.y - thisTouch.y);
            if (Math.abs(dragDist.x) < Const.dragEpsilon && Math.abs(dragDist.y) < Const.dragEpsilon) {
                if      (button == 0) selectedItem.rotateCCW(); // left mouse
                else if (button == 1) selectedItem.rotateCW();  // right mouse
            } else {
                boolean overlaps = false;
                for (Item item : items) {
                    if (item != selectedItem && selectedItem.overlaps(item)) {
                        overlaps = true;
                        break;
                    }
                }
                if (overlaps) {
                    selectedItem.moveToCell(originalCellX, originalCellY);
                } else {
                    selectedItem.moveToCell();
                }
            }

            selectedItem = null;
            firstTouch.set(0, 0, 0);
            dragDist.set(0, 0);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        thisTouch.set(screenX, screenY, 0);
        camera.unproject(thisTouch);

        dragDist.set(firstTouch.x - thisTouch.x, firstTouch.y - thisTouch.y);

        if (selectedItem != null) {
            // Only move the selected item if we've dragged far enough
            if (Math.abs(dragDist.x) > Const.dragEpsilon || Math.abs(dragDist.y) > Const.dragEpsilon) {
                selectedItem.moveTo((int) thisTouch.x, (int) thisTouch.y);
            }
        }

        return false;
    }

}
