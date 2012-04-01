package ru.avokin.swing.widehighlighting;

import javax.swing.text.DefaultHighlighter;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 16.08.2010
 */
public class WidePainter extends DefaultHighlighter.DefaultHighlightPainter {

    private Color borderColor;

    public WidePainter(Color c, Color borderColor) {
        super(c);
        this.borderColor = borderColor;
    }

    protected Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
