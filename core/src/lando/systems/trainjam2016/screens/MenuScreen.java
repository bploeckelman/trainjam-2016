package lando.systems.trainjam2016.screens;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.trainjam2016.TrainJam2016;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.Utils;
import lando.systems.trainjam2016.utils.accessors.ColorAccessor;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class MenuScreen extends BaseScreen {

    Color pulseColor;

    public MenuScreen() {
        Utils.glClearColor(Color.FOREST);
        Gdx.input.setInputProcessor(this);

        pulseColor = new Color(1f, 1f, 1f, 1f);
        Tween.to(pulseColor, ColorAccessor.A, 0.333f)
                .target(0.1f)
                .repeatYoyo(-1, 0f)
                .start(Assets.tween);
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.justTouched()) {
            TrainJam2016.game.screen = new GameScreen();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Assets.glyphLayout.setText(Assets.font, Const.title);
        float titleTextWidth  = Assets.glyphLayout.width;
        float titleTextHeight = Assets.glyphLayout.height;

        String clickText = "Click to play!";
        Assets.glyphLayout.setText(Assets.font, clickText);
        float clickTextWidth  = Assets.glyphLayout.width;
        float clickTextHeight = Assets.glyphLayout.height;

        batch.begin();
        Assets.font.draw(batch, Const.title,
                         camera.viewportWidth  / 2f - titleTextWidth  / 2f,
                         camera.viewportHeight / 2f + 2f * titleTextHeight);
        Assets.font.setColor(pulseColor);
        Assets.font.draw(batch, clickText,
                         camera.viewportWidth  / 2f - clickTextWidth  / 2f,
                         camera.viewportHeight / 2f - clickTextHeight);
        Assets.font.setColor(Color.WHITE);
        batch.end();
    }

}
