package lando.systems.trainjam2016.screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 1/17/2016.
 */
public abstract class BaseScreen extends InputAdapter {

    public OrthographicCamera camera;

    public BaseScreen() {
        camera = new OrthographicCamera(Const.width, Const.height);
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);

}
