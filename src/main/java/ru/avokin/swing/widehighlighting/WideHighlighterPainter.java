package ru.avokin.swing.widehighlighting;

import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 17.07.2010
 */
public class WideHighlighterPainter extends WidePainter {

    private final int startLeftSymbolPosition;

    private final int endLeftSymbolPosition;

    public WideHighlighterPainter(Color c, Color borderColor, int startLeftSymbolPosition, int endLeftSymbolPosition) {
        super(c, borderColor);
        this.startLeftSymbolPosition = startLeftSymbolPosition;
        this.endLeftSymbolPosition = endLeftSymbolPosition;
    }

    protected boolean isStartHighlightedLine(int pos) {
        return pos == startLeftSymbolPosition;
    }

    protected boolean isEndHighlightedLine(int pos) {
        return pos == endLeftSymbolPosition;
    }

    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        try {
            int leftSelection = c.getSelectionStart();
            int rightSelection = c.getSelectionEnd();

            int leftInset = c.getInsets().left;
            int rightInset =  c.getInsets().right;

            TextUI mapper = c.getUI();
            Rectangle p0 = mapper.modelToView(c, leftSelection);
            Rectangle p1 = mapper.modelToView(c, rightSelection);
            Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
            @SuppressWarnings({"ConditionalExpression"})
            Rectangle r = (shape instanceof Rectangle) ? (Rectangle) shape : shape.getBounds();

            int maxWidth = c.getWidth() - rightInset - leftInset;

            Rectangle rH = new Rectangle(r.x, r.y, maxWidth, r.height);

            g.setColor(getColor());

            if (leftSelection >= rightSelection || (rightSelection <= offs0) || (leftSelection >= offs1)) {
                // no selection
                g.fillRect(leftInset, rH.y, rH.width, rH.height);
            } else {
                // There is intersection with selection highlighting.
                if (offs0 < leftSelection && leftSelection <= offs1) {
                    // Left highlighting.
                    Rectangle rL = new Rectangle(leftInset, rH.y, p0.x - leftInset, rH.height);
                    g.fillRect(rL.x, rL.y, rL.width, rL.height);
                }
                if (offs0 < rightSelection && rightSelection <= offs1) {
                    // Right highlighting.
                    int width = c.getWidth() - rightInset - p1.x;
                    Rectangle rR = new Rectangle(p1.x, p1.y, width, rH.height);
                    g.fillRect(rR.x, rR.y, rR.width, rR.height);
                }
            }

            // Drawing border if line is top or bottom.
            Graphics2D g2 = (Graphics2D) g;
            Stroke stroke = new BasicStroke(1);
            g2.setStroke(stroke);
            g.setColor(getBorderColor());

            int rightX = leftInset + maxWidth - 1;
            if (isStartHighlightedLine(offs0)) {
                g.drawLine(leftInset, r.y, rightX, r.y);
            }
            if (isEndHighlightedLine(offs0)) {
                g.drawLine(leftInset, r.y + r.height - 1, rightX, r.y + r.height - 1);
            }

            return rH;
        } catch (BadLocationException ignored) {
        }

        return null;
    }
}
