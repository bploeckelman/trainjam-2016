package lando.systems.trainjam2016.entities;

import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import lando.systems.trainjam2016.utils.Assets;
import lando.systems.trainjam2016.utils.Const;
import lando.systems.trainjam2016.utils.accessors.ColorAccessor;

/**
 * Brian Ploeckelman created on 3/13/2016.
 */
public class InstructionsOverlay {

    public boolean show = true;

    BitmapFont  font;
    GlyphLayout layout;
    Rectangle   background;
    Color       pulseColor;

    Array<String> instructions;

    public InstructionsOverlay() {
        font = Assets.font;
        layout = Assets.glyphLayout;
        float width = Const.width * 0.75f;
        float height = Const.height * 0.75f;
        background = new Rectangle(
                Gdx.graphics.getWidth() / 2f - width / 2f,
                Gdx.graphics.getHeight() / 2f - height / 2f,
                width, height);

        pulseColor = new Color(0f, 1f, 0f, 1f);
        Tween.to(pulseColor, ColorAccessor.A, 0.75f)
             .target(0.2f)
             .repeatYoyo(-1, 0f)
             .start(Assets.tween);

        instructions = new Array<String>();
        instructions.add("[GREEN]CLICK AND DRAG[] items from the conveyor belt into the bag");
        instructions.add("[CYAN]CLICK[] the small bags on the right to [CYAN] SWITCH BAGS[]");
        instructions.add("[YELLOW]LEFT / RIGHT CLICK[] items on the conveyor belt to [YELLOW] ROTATE[] them");
        instructions.add("[ORANGE]FILL[] the bags to [ORANGE] MAXIMUM CAPACITY[]");
        instructions.add("You have [RED] 10 BAGS[] to fill");
    }

    public boolean update(float dt) {
        if (Gdx.input.justTouched()) {
            show = false;
            return true;
        }
        return false;
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
        font.setColor(Color.WHITE);
        layout.setText(font, "Instructions");
        float originX = background.x + background.width / 2f;
        float lineY = background.y + background.height - layout.height - margin;
        font.draw(batch, "Instructions", originX - layout.width / 2f, lineY);

        font.getData().setScale(2f);
        lineY -= background.height / 8f;

        for (int i = 0; i < instructions.size; ++i) {
            layout.setText(font, instructions.get(i));
            lineY -= layout.height + 2f * margin;
            font.draw(batch, instructions.get(i), originX - layout.width / 2f, lineY);
        }

        font.getData().setScale(2f);
        font.setColor(pulseColor);
        String startStr = "Click to begin...";
        layout.setText(font, startStr);
        lineY = background.y + margin + frameMargin + layout.height;
        font.draw(batch, startStr, originX - layout.width / 2f, lineY);
        font.setColor(Color.WHITE);
    }
}
