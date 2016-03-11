package lando.systems.trainjam2016.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lando.systems.trainjam2016.TrainJam2016;
import lando.systems.trainjam2016.entities.Item;
import lando.systems.trainjam2016.entities.ItemApple;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.Utils;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class GameScreen extends BaseScreen {

    Array<Item> items;

    public GameScreen() {
        super();
        Utils.glClearColor(Const.bgColor);
        Gdx.input.setInputProcessor(this);

        items = new Array<Item>();
        items.add(new ItemApple());
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            TrainJam2016.game.screen = new MenuScreen();
        }

        if (Gdx.input.justTouched()) {
            touchCoords.set(Gdx.input.getX(), Gdx.input.getY(), 0f);
            camera.unproject(touchCoords);
            for (Item item : items) {
                if (item.isPointInside(touchCoords.x, touchCoords.y)) {
                    item.rotateCCW();
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.testTexture,
                   camera.viewportWidth  / 2f - Assets.testTexture.getWidth()  / 2f,
                   camera.viewportHeight / 2f - Assets.testTexture.getHeight() / 2f);
        for (Item item : items) {
            item.render(batch);
        }
        batch.end();
    }

}
