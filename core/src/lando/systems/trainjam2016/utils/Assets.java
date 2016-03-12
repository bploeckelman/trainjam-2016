package lando.systems.trainjam2016.utils;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    public static Texture bagSmallTexture;
    public static Texture bagGridTexture;
    public static Texture appleTexture;
    public static Texture soupTexture;
    public static Texture eggsTexture;
    public static Texture breadTexture;
    public static Texture melonTexture;
    public static Texture yamTexture;
    public static Texture cerealTexture;
    public static Texture milkTexture;
    public static Texture broccoliTexture;
    public static Texture grapesTexture;
    public static Texture bananasTexture;
    public static Texture bowlTexture;
    public static Texture conveyorTexture;

    public static TextureRegion grid;
    public static TextureRegion bag;
    public static TextureRegion apple;
    public static TextureRegion soup;
    public static TextureRegion conveyor;
    public static TextureRegion eggs;
    public static TextureRegion bread;
    public static TextureRegion melon;
    public static TextureRegion yam;
    public static TextureRegion cereal;
    public static TextureRegion milk;
    public static TextureRegion broccoli;
    public static TextureRegion grapes;
    public static TextureRegion bananas;
    public static TextureRegion bowl;

    public static Music music;

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
        bagSmallTexture = new Texture("bag-small.png");
        bagGridTexture = new Texture("bag-grid.png");
        appleTexture = new Texture("apple.png");
        soupTexture = new Texture("soup.png");
        eggsTexture = new Texture("eggs.png");
        breadTexture = new Texture("bread.png");
        melonTexture = new Texture("melon.png");
        yamTexture = new Texture("yam.png");
        cerealTexture = new Texture("cereal.png");
        milkTexture = new Texture("milk.png");
        broccoliTexture = new Texture("broccoli.png");
        grapesTexture = new Texture("grapes.png");
        bananasTexture = new Texture("bananas.png");
        bowlTexture = new Texture("bowl.png");
        conveyorTexture = new Texture("conveyor.png");
        conveyor = new TextureRegion(conveyorTexture);
        bag = new TextureRegion(bagTexture);
        apple = new TextureRegion(appleTexture);
        soup = new TextureRegion(soupTexture);
        bag = new TextureRegion(bagTexture);
        apple = new TextureRegion(appleTexture);
        soup = new TextureRegion(soupTexture);
        eggs = new TextureRegion(eggsTexture);
        bread = new TextureRegion(breadTexture);
        melon = new TextureRegion(melonTexture);
        yam = new TextureRegion(yamTexture);
        cereal = new TextureRegion(cerealTexture);
        milk = new TextureRegion(milkTexture);
        broccoli = new TextureRegion(broccoliTexture);
        grapes = new TextureRegion(grapesTexture);
        bananas = new TextureRegion(bananasTexture);
        bowl = new TextureRegion(bowlTexture);

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }

    public static void dispose() {
        batch.dispose();
        font.dispose();
        testTexture.dispose();
        whitePixelTexture.dispose();
        whiteCircleTexture.dispose();
        bagTexture.dispose();
        bagGridTexture.dispose();
        appleTexture.dispose();
        gridTexture.dispose();
        soupTexture.dispose();
        eggsTexture.dispose();
        breadTexture.dispose();
        melonTexture.dispose();
        yamTexture.dispose();
        cerealTexture.dispose();
        milkTexture.dispose();
        broccoliTexture.dispose();
        grapesTexture.dispose();
        bananasTexture.dispose();
        bowlTexture.dispose();
        conveyorTexture.dispose();
        music.stop();
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
