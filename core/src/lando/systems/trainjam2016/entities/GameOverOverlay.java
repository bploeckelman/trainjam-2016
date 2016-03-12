package lando.systems.trainjam2016.entities;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.accessors.ColorAccessor;

/**
 * Brian Ploeckelman created on 3/12/2016.
 */
public class GameOverOverlay {

    int   numBagsFilledTo100;
    int   averageCapacityPercent;
    float averageCapacity;
    float timeElapsed;
    float timeStarted;
    int   timeElapsedMins;
    Color pulseColor;

    BitmapFont  font;
    GlyphLayout layout;
    Rectangle   background;

    public GameOverOverlay() {
        numBagsFilledTo100 = 0;
        averageCapacity = 0;
        averageCapacityPercent = 0;
        timeElapsed = 0;
        timeStarted = TimeUtils.millis();

        font = Assets.font;
        layout = Assets.glyphLayout;
        float width = Const.width * 0.75f;
        float height = Const.height * 0.75f;
        background = new Rectangle(
                Gdx.graphics.getWidth() / 2f - width / 2f,
                Gdx.graphics.getHeight() / 2f - height / 2f,
                width, height);

        pulseColor = new Color(0f, 1f, 0f, 1f);
        Tween.to(pulseColor, ColorAccessor.A, 0.5f)
             .target(0.2f)
             .repeatYoyo(-1, 0f)
             .start(Assets.tween);
    }

    public void finish(Array<Bag> doneBags) {
        float now = TimeUtils.millis();
        timeElapsed = now - timeStarted;
        timeElapsed = ((timeElapsed / 1000f) / 60f);
        timeElapsedMins = (int) timeElapsed;

        float totalCapacity = 0f;
        for (Bag bag : doneBags) {
            float capacity = bag.getCapacity();
            if (capacity == 1f) {
                numBagsFilledTo100++;
            }
            totalCapacity += capacity;
        }
        averageCapacity = totalCapacity / doneBags.size;
        averageCapacityPercent = (int) (averageCapacity * 100);
    }

    public void render(SpriteBatch batch) {
        batch.setColor(0.1f, 0.1f, 0.1f, 0.85f);
        batch.draw(Assets.whitePixelTexture, background.x, background.y, background.width, background.height);
        batch.setColor(1f, 1f, 1f, 1f);

        final float margin = 20f;
        final float frameMargin = 6f;

        Assets.border.draw(batch,
                           background.x - frameMargin,
                           background.y - frameMargin,
                           background.width + 2f * frameMargin,
                           background.height + 2f * frameMargin);

        font.getData().setScale(5f);
        layout.setText(font, "Game Over!");
        float originX = background.x + background.width / 2f;
        float lineY = background.y + background.height - layout.height - margin;
        font.setColor(pulseColor);
        font.draw(batch, "Game Over!", originX - layout.width / 2f, lineY);
        font.setColor(Color.WHITE);
        font.getData().setScale(3f);
        lineY -= background.height / 4f;

        String timeElapsedStr = "Time Elapsed: " + timeElapsedMins + " minutes";
        layout.setText(font, timeElapsedStr);
        lineY -= layout.height + 2f * margin;
        font.draw(batch, timeElapsedStr, originX - layout.width / 2f, lineY);

        String numFilledStr = "Bags Completely Filled: " + numBagsFilledTo100;
        layout.setText(font, numFilledStr);
        lineY -= layout.height + 2f * margin;
        font.draw(batch, numFilledStr, originX - layout.width / 2f, lineY);

        String averageCapPercentStr = "Average Amount Filled: " + averageCapacityPercent + "%";
        layout.setText(font, averageCapPercentStr);
        lineY -= layout.height + 2f * margin;
        font.draw(batch, averageCapPercentStr, originX - layout.width / 2f, lineY);

        font.getData().setScale(2f);
    }

}
