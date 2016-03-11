package lando.systems.trainjam2016.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lando.systems.trainjam2016.TrainJam2016;
import lando.systems.trainjam2016.utils.Const;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Const.width;
		config.height = Const.height;
		config.resizable = Const.resizable;
		new LwjglApplication(new TrainJam2016(), config);
	}
}
