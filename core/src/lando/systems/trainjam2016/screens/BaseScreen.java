package lando.systems.trainjam2016.screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import lando.systems.trainjam2016.utils.Const;

/**
 * Brian Ploeckelman created on 1/17/2016.
 */
public abstract class BaseScreen extends InputAdapter {

    public OrthographicCamera camera;
    public Vector3            touchCoords;

    public BaseScreen() {
        camera = new OrthographicCamera(Const.width, Const.height);
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        touchCoords = new Vector3();
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);

}
