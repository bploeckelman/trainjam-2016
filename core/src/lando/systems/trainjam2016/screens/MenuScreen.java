package lando.systems.trainjam2016.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.primitives.MutableFloat;
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

    MutableFloat delay;
    Color        pulseColor;

    public MenuScreen() {
        super();
        Utils.glClearColor(Color.FOREST);
        Gdx.input.setInputProcessor(this);

        delay = new MutableFloat(1f);
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
            Assets.clickPlay.play(Const.volume * 2f);
            Tween.to(delay, -1, 0.5f)
                    .target(0f)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int i, BaseTween<?> baseTween) {
                            TrainJam2016.game.screen = new GameScreen();
                        }
                    })
                    .start(Assets.tween);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.titleTexture, 0, 0, 1200, 800);
        batch.end();
    }

}
