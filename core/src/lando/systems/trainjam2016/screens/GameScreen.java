package lando.systems.trainjam2016.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import lando.systems.trainjam2016.TrainJam2016;
import lando.systems.trainjam2016.entities.*;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.Utils;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class GameScreen extends BaseScreen {

    private static final int NUM_BAGS = 5;

    Bag              activeBag;
    Array<Bag>       bags;
    Array<Bag>       doneBags;
    Array<Rectangle> bagTouchRegions;
    Array<Item>      items;
    Item             selectedItem;
    Conveyor         conveyor;
    int              originalCellX, originalCellY;
    Vector2          selectedItemOrigPos;
    Vector3          firstTouch;
    Vector3          thisTouch;
    Vector2          dragDist;
    Color            capacityColor;
    float            currentTime;

    public GameScreen() {
        super();
        Utils.glClearColor(Const.bgColor);
        Gdx.input.setInputProcessor(this);

        bags = new Array<Bag>(NUM_BAGS);
        bagTouchRegions = new Array<Rectangle>(NUM_BAGS);
        int bagTouchCellX = 27;
        int bagTouchCellY = 1;
        for (int i = 0; i < NUM_BAGS; ++i) {
            bags.add(new Bag());
            bagTouchRegions.add(new Rectangle(
                    bagTouchCellX * Const.cellSize,
                    bagTouchCellY * Const.cellSize,
                    2 * Const.cellSize,
                    3 * Const.cellSize
            ));
            bagTouchCellY += 4;
        }
        activeBag = bags.first();
        doneBags = new Array<Bag>();

        items = new Array<Item>();
        Item milk = new ItemMilk();
        milk.setConveyorTime(2);
        items.add(milk);

        conveyor = new Conveyor();

        selectedItemOrigPos = new Vector2();
        firstTouch = new Vector3();
        thisTouch = new Vector3();
        dragDist = new Vector2();
        capacityColor = new Color(1f, 0f, 0f, 1f);
    }

    private void replaceBag(Bag bag) {
        doneBags.add(bag);
        Bag newBag = new Bag();
        if (activeBag == bag) activeBag = newBag;
        for (int i = 0; i < NUM_BAGS; i++) {
            if (bags.get(i) == bag) {
                bags.set(i, newBag);
            }
        }
    }

    @Override
    public void update(float dt) {
        currentTime += dt;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            TrainJam2016.game.screen = new MenuScreen();
        }

        for (int i = 0; i < NUM_BAGS; i++) {
            Bag bag = bags.get(i);
            if (bag.getCapacity() >= 1f) replaceBag(bag);
        }

        for (Item item : items) {
            item.update(dt);
            if (item != selectedItem && !activeBag.isInBag(item)) {
                Vector2 conveyorPos = conveyor.getItemPosition(item);
                if (conveyorPos != null) {
                    item.moveCornerTo(conveyorPos.x, conveyorPos.y);
                } else {
                    while (!activeBag.cramItem(item)) replaceBag(activeBag);
                    items.removeValue(item, true);

                    Item newItem = Item.createNewRandomItem();
                    newItem.setConveyorTime(currentTime);
                    newItem.moveToCell(MathUtils.random(1, 10), MathUtils.random(1, 10));
                    items.add(newItem);
                }
            }
        }
        activeBag.update(dt);
        conveyor.update(dt);
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

        conveyor.render(batch, false);
        for (int i = 0; i < NUM_BAGS; ++i) {
            float capacity = bags.get(i).getCapacity();
            Rectangle rect = bagTouchRegions.get(i);
            if (bags.get(i) == activeBag) batch.setColor(1f, 1f, 1f, 0.5f);
            else                          batch.setColor(1f, 1f, 1f, 1f);
            batch.draw(Assets.bagSmallTexture, rect.x, rect.y, rect.width, rect.height);

            float meterX = rect.x + rect.width / 4f;
            float meterY = rect.y + rect.height / 4f;
            float meterW = rect.width / 2f;
            float meterH = rect.height / 2f;
            Utils.hsvToRgb(capacity * 120f / 365f, 1f, 1f, capacityColor);
            batch.setColor(capacityColor);
            batch.draw(Assets.whitePixelTexture, meterX, meterY, meterW, meterH * capacity + 0.2f);
            batch.setColor(1f, 1f, 1f, 1f);

            int capPercent = (int) (capacity * 100f);
            String capPercentStr = capPercent + "%";
            Assets.glyphLayout.setText(Assets.font, capPercentStr);
            float textW = Assets.glyphLayout.width;
            float textH = Assets.glyphLayout.height;
            Assets.font.draw(batch, capPercentStr, rect.x + rect.width / 2f - textW / 2f, rect.y + rect.height / 2f + textH / 2f);
        }
        activeBag.render(batch, false);

        for (Item item : items) {
            item.render(batch, activeBag.isInBag(item));
        }

        if (selectedItem != null) {
            Vector2 ghostPos = conveyor.getItemPosition(selectedItem);
            if (ghostPos != null) {
                selectedItem.renderGhost(batch, ghostPos);
            }
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
                selectedItemOrigPos.set(selectedItem.pos.x, selectedItem.pos.y);
                break;
            }
        }

        for (int i = 0; i < NUM_BAGS; ++i) {
            Rectangle rect = bagTouchRegions.get(i);
            if (rect.contains(firstTouch.x, firstTouch.y)) {
                activeBag = bags.get(i);
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
                    boolean placed = activeBag.dropItem(selectedItem);
                    if (placed) {
                        selectedItem.moveToCell();
                        items.removeValue(selectedItem, true);

                        Item newItem = Item.createNewRandomItem();
                        newItem.setConveyorTime(currentTime);
                        newItem.moveToCell(MathUtils.random(1, 10), MathUtils.random(1, 10));
                        items.add(newItem);
                    } else {
                        selectedItem.moveToCell(originalCellX, originalCellY);
                    }
                }
            }

            selectedItem = null;
            selectedItemOrigPos.set(0, 0);
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
