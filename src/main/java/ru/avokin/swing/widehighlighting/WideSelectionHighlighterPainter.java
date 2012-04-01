package ru.avokin.swing.widehighlighting;

import javax.swing.text.*;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 16.07.2010
 */
public class WideSelectionHighlighterPainter extends DefaultHighlighter.DefaultHighlightPainter {

    private static final WideSelectionHighlighterPainter INSTANCE = new WideSelectionHighlighterPainter();

    /**
     * Constructs a new highlight painter. If <code>c</code> is null,
     * the JTextComponent will be queried for its selection color.
     */
    private WideSelectionHighlighterPainter() {
        super(null);
    }

    public static WideSelectionHighlighterPainter getInstance() {
        return INSTANCE;
    }

    public int getRight(JTextComponent c) {
        return c.getSelectionEnd();
    }

    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        Color color = getColor();

        if (color == null) {
            g.setColor(c.getSelectionColor());
        } else {
            g.setColor(color);
        }

        try {
            int rightInset = c.getInsets().right;

            Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
            @SuppressWarnings({"ConditionalExpression"})
            Rectangle r = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();

            boolean shortLine = isShortLine(c, offs1);
            int left;
            int width;

            // If selection starts from off0.
            left = r.x;

            //noinspection IfMayBeConditional
            if (shortLine) {
                width = r.width;
            } else {
                width = c.getWidth() - rightInset - r.x;
            }

            Rectangle rR = new Rectangle(left, r.y, width, r.height);
            g.fillRect(rR.x, rR.y, rR.width, rR.height);
            return rR;
        } catch (BadLocationException ignored) {
        }

        return null;
    }

     public boolean isShortLine(JTextComponent c, int lastSymbol) {
        return getRight(c) <= lastSymbol;
    }
}
