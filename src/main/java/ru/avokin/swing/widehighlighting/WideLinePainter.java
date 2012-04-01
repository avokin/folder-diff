package ru.avokin.swing.widehighlighting;

import javax.swing.text.*;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 28.07.2010
 */
public class WideLinePainter extends WidePainter {

    private final boolean downLine;

    public WideLinePainter(Color borderColor, boolean downLine) {
        super(null, borderColor);
        this.downLine = downLine;
    }

    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        int leftInset = c.getInsets().left;
        int rightInset =  c.getInsets().right;
        int maxWidth = c.getWidth() - rightInset - leftInset;

        try {
            Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
            @SuppressWarnings({"ConditionalExpression"})
            Rectangle r = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();
            Rectangle rH = new Rectangle(r.x, r.y, maxWidth, r.height);

            g.setColor(getBorderColor());
            int rightX = leftInset + maxWidth - 1;
            @SuppressWarnings({"ConditionalExpression"})
            int y = downLine ? r.y : r.y + r.height;
            g.drawLine(leftInset, y, rightX, y);

            return rH;
        } catch (BadLocationException ignored) {
        }
        return null;
    }
}
