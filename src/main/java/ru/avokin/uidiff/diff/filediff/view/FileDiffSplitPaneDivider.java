package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.filediff.Difference;
import ru.avokin.settings.ColorManager;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

/**
 * Created by Andrey Vokin.
 * Date: 24.07.2010
 */
public class FileDiffSplitPaneDivider extends BasicSplitPaneDivider {

    private static final int V_INSET = 2;

    private int charHeight;

    private int leftMove;

    private int rightMove;

    private FileDiffModel model;

    public FileDiffSplitPaneDivider(BasicSplitPaneUI ui) {
        super(ui);
        setBorder(BorderFactory.createEmptyBorder());
    }

    protected int getVerticalInset() {
        return V_INSET + charHeight;
    }

    public void setLeftMove(int leftMove) {
        this.leftMove = leftMove;
    }

    public void setRightMove(int rightMove) {
        this.rightMove = rightMove;
    }

    @Override
    public void paint(Graphics g) {
        if (model == null) {
            return;
        }
        int verticalInset = getVerticalInset();

        g.setClip(0, verticalInset, getWidth(), getHeight() - verticalInset);
        for (Difference d : model.getFileDifference().getDifferenceList()) {
            Polygon p = new Polygon();
            int x;
            int y;

            if (d.getLeftCodeBlock().getEndLine() >= d.getLeftCodeBlock().getStartLine()) {
                x = 0;
                y = (d.getLeftCodeBlock().getEndLine() + 1) * charHeight - leftMove + verticalInset - 1;
                p.addPoint(x, y);
            }

            x = 0;
            y = d.getLeftCodeBlock().getStartLine() * charHeight - leftMove + verticalInset;
            p.addPoint(x, y);

            x = getWidth() - 1;
            y = d.getRightCodeBlock().getStartLine() * charHeight - rightMove + verticalInset;
            p.addPoint(x, y);

            if (d.getRightCodeBlock().getEndLine() >= d.getRightCodeBlock().getStartLine()) {
                x = getWidth() - 1;
                y = (d.getRightCodeBlock().getEndLine() + 1) * charHeight - rightMove + verticalInset - 1;
                p.addPoint(x, y);
            }

            Color c = DiffColorManager.getColor(d.getLeftCodeBlock());
            g.setColor(c);
            g.fillPolygon(p);

            g.setColor(ColorManager.highlightBorder);
            g.drawPolygon(p);
        }
    }

    public void setModel(FileDiffModel model) {
        this.model = model;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public int getCharHeight() {
        return charHeight;
    }

    public void setCharHeight(int charHeight) {
        this.charHeight = charHeight;
    }
}
