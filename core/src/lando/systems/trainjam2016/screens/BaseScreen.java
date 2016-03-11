package lando.systems.trainjam2016.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 1/17/2016.
 */
public abstract class BaseScreen {

    public OrthographicCamera camera;

    public BaseScreen() {
        float aspect = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera(Const.width, Const.width * aspect);
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);

}
