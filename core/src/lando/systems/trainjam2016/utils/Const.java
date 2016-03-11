package lando.systems.trainjam2016.utils;

import com.badlogic.gdx.graphics.Color;

/**
 * Brian Ploeckelman created on 3/10/2016.
 */
public class Const {
    public static final String  title     = "Grocery Bagging Simulator 2016";
    public static final int     width     = 1200;
    public static final int     height    = 800;
    public static final boolean resizable = false;
    public static final Color   bgColor   = new Color(0f, 0f, 0.6f, 1f);
    public static final float   cellSize  = 40f;
    public static final int     cellsWide = width  / (int) cellSize;
    public static final int     cellsHigh = height / (int) cellSize;
}
