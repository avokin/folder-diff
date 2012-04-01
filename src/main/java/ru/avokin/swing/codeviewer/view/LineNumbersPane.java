package ru.avokin.swing.codeviewer.view;

import ru.avokin.swing.codeviewer.model.LineNumberPosition;
import ru.avokin.swing.utils.StrokeFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Andrey Vokin.
 * Date: 24.07.2010
 */
class LineNumbersPane extends JTextPane {

    private final LineNumberPosition lineNumberPosition;

    public LineNumbersPane(LineNumberPosition lineNumberPosition) {
        this.lineNumberPosition = lineNumberPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        Graphics2D g2 = (Graphics2D) g;
        
        Stroke stroke = StrokeFactory.createSimpleStroke();
        g2.setStroke(stroke);

        if (lineNumberPosition == LineNumberPosition.left) {
            g.drawLine(0, 0, 0, getHeight());
        }

        if (lineNumberPosition == LineNumberPosition.right) {
            g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        }
    }
}
