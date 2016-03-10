package lando.systems.trainjam2016;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lando.systems.trainjam2016.utils.Assets;

public class TrainJam2016 extends ApplicationAdapter {
	SpriteBatch batch;

	@Override
	public void create () {
		Assets.load();
		batch = Assets.batch;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(Assets.testTexture, 0, 0);
		batch.end();
	}
}
