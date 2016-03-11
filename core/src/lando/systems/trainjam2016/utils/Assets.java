package lando.systems.trainjam2016.utils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import lando.systems.trainjam2016.utils.accessors.*;

/**
 * Brian Ploeckelman created on 9/10/2015.
 */
public class Assets {

    public static TweenManager tween;
    public static GlyphLayout  glyphLayout;
    public static SpriteBatch  batch;
    public static BitmapFont   font;

    public static Texture whitePixelTexture;
    public static Texture whiteCircleTexture;
    public static Texture testTexture;
    public static Texture gridTexture;

    public static Texture bagTexture;
    public static Texture appleTexture;
    public static Texture soupTexture;

    public static TextureRegion apple;
    public static TextureRegion soup;
    public static TextureRegion grid;
    public static TextureRegion bag;


    public static void load() {
        if (tween == null) {
            tween = new TweenManager();
            Tween.setCombinedAttributesLimit(4);
            Tween.registerAccessor(Color.class, new ColorAccessor());
            Tween.registerAccessor(Rectangle.class, new RectangleAccessor());
            Tween.registerAccessor(Vector2.class, new Vector2Accessor());
            Tween.registerAccessor(Vector3.class, new Vector3Accessor());
            Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
        }

        glyphLayout = new GlyphLayout();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(2f);
        font.getData().markupEnabled = true;

        testTexture = new Texture("badlogic.jpg");
        whitePixelTexture = new Texture("white-pixel.png");
        whiteCircleTexture = new Texture("white-circle.png");
        gridTexture = new Texture("grid.png");
        gridTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        grid = new TextureRegion(gridTexture);

        bagTexture = new Texture("bag.png");
        appleTexture = new Texture("apple.png");
        soupTexture = new Texture("soup.png");
        bag = new TextureRegion(bagTexture);
        apple = new TextureRegion(appleTexture);
        soup = new TextureRegion(soupTexture);
    }

    public static void dispose() {
        batch.dispose();
        font.dispose();
        testTexture.dispose();
        whitePixelTexture.dispose();
        whiteCircleTexture.dispose();
        bagTexture.dispose();
        appleTexture.dispose();
        gridTexture.dispose();
        soupTexture.dispose();
    }

    private static ShaderProgram compileShaderProgram(FileHandle vertSource, FileHandle fragSource) {
        ShaderProgram.pedantic = false;
        final ShaderProgram shader = new ShaderProgram(vertSource, fragSource);
        if (!shader.isCompiled()) {
            throw new GdxRuntimeException("Failed to compile shader program:\n" + shader.getLog());
        }
        else if (shader.getLog().length() > 0) {
            Gdx.app.debug("SHADER", "ShaderProgram compilation log:\n" + shader.getLog());
        }
        return shader;
    }

}
