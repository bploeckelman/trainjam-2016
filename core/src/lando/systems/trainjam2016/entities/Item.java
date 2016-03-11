package lando.systems.trainjam2016.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Brian Ploeckelman created on 3/10/2016.
 * TODO: probably want to separate grid x,y (int) from render x,y (float) to enable smooth movement between grid cells
 */
public abstract class Item {

    int           x, y;
    int           angle;
    int[]         shape;
    TextureRegion texture;

    public Item(TextureRegion texture) {
        this.x       = 0;
        this.y       = 0;
        this.angle   = 0;
        this.texture = texture;
    }

    public abstract void rotateCCW();
    public abstract void rotateCW();

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveToX(int x) {
        this.x = x;
    }

    public void moveToY(int y) {
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, 0, 0, texture.getRegionWidth(), texture.getRegionHeight(), 1f, 1f, angle);
        // TODO: draw grid shape as overlay
    }

}
