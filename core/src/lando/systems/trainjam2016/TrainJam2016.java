package lando.systems.trainjam2016;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import lando.systems.trainjam2016.screens.BaseScreen;
import lando.systems.trainjam2016.screens.GameScreen;
import lando.systems.trainjam2016.utils.Assets;

public class TrainJam2016 extends ApplicationAdapter {

	public static TrainJam2016 game;
	public        BaseScreen   screen;

	@Override
	public void create() {
		Assets.load();
		game = this;
		screen = new GameScreen();
	}

	@Override
	public void dispose() {
		Assets.dispose();
	}

	@Override
	public void render() {
		float dt = Math.min(Gdx.graphics.getDeltaTime(), 1f / 30f);
		Assets.tween.update(dt);
		screen.update(dt);
		screen.render(Assets.batch);
	}

}
