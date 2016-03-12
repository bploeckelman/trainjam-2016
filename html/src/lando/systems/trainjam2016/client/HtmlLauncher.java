package lando.systems.trainjam2016.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import lando.systems.trainjam2016.TrainJam2016;
import lando.systems.trainjam2016.utils.Const;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(Const.width, Const.height);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new TrainJam2016();
        }
}