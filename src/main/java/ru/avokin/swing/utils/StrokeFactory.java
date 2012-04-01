package ru.avokin.swing.utils;

import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 29.09.2010
 */
public class StrokeFactory {

    public static Stroke createSimpleStroke() {
        return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1 }, 0);
    }
}
