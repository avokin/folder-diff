package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.settings.FontManager;
import ru.avokin.uidiff.diff.common.view.AbstractDiffSplitPane;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by Andrey Vokin.
 * Date: 24.07.2010
 */
public class FileDiffSplitPane extends AbstractDiffSplitPane {

    public static final String UI_CLASS_ID = "FileDiffSplitPane";

    private static final int DIVIDER_WIDTH = 40;

    private final FileDiffSidePanel leftEditor;

    private final FileDiffSidePanel rightEditor;

    private final FileDiffSplitPaneUI ui;

    private final FileDiffModel model;

    private final FileDiffAdjuster diffAdjuster;

    public FileDiffSplitPane(FileDiffModel model, FileDiffSidePanel leftDiffSide, FileDiffSidePanel rightDiffSide) {
        super(leftDiffSide, rightDiffSide);

        this.leftEditor = leftDiffSide;
        this.rightEditor = rightDiffSide;
        this.model = model;

        FontMetrics fm = leftDiffSide.getFontMetrics(FontManager.getFont());
        ui = (FileDiffSplitPaneUI) getUI();
        int charHeight = fm.getHeight();
        ui.getDivider().setCharHeight(charHeight);
        ui.getDivider().setModel(model);

        diffAdjuster = new FileDiffAdjuster(charHeight);

        setDividerSize(DIVIDER_WIDTH);
        setBorder(BorderFactory.createEmptyBorder());

        setContinuousLayout(true);
    }

    @Override
    public String getUIClassID() {
        return UI_CLASS_ID;
    }

    @Override
    public AdjustmentListener createAdjustmentListener() {
        return new FileDiffAdjustmentListenerImpl();
    }

    class FileDiffAdjustmentListenerImpl implements AdjustmentListener {

        private JScrollBar getOppositeScrollBar(JScrollBar source) {
            JScrollBar result;
            //noinspection IfStatementWithTooManyBranches
            if (source == leftEditor.getHorizontalScrollBar()) {
                result = rightEditor.getHorizontalScrollBar();
            } else if (source == leftEditor.getVerticalScrollBar()) {
                result = rightEditor.getVerticalScrollBar();
            } else if (source == rightEditor.getHorizontalScrollBar()) {
                result = leftEditor.getHorizontalScrollBar();
            } else if (source == rightEditor.getVerticalScrollBar()) {
                result = leftEditor.getVerticalScrollBar();
            } else {
                throw new IllegalArgumentException("Unexpected event source.");
            }

            return result;
        }

        private boolean isHorizontalAdjustedByUser = true;
        private boolean isVerticalAdjustedByUser = true;

        public void adjustmentValueChanged(AdjustmentEvent e) {


            JScrollBar source = (JScrollBar) e.getSource();
            JScrollBar oppositeSb = getOppositeScrollBar(source);

            if (JScrollBar.HORIZONTAL == source.getOrientation()) {
                if (!isHorizontalAdjustedByUser) {
                    return;
                }
                isHorizontalAdjustedByUser = false;
                if (source.getMaximum() - source.getVisibleAmount() > 0) {
                    double relativeValue = 1.0 * source.getValue() / (source.getMaximum() - source.getVisibleAmount());
                    int absoluteValue = (int) ((oppositeSb.getMaximum() - oppositeSb.getVisibleAmount()) * relativeValue);
                    oppositeSb.setValue(oppositeSb.getMaximum() - oppositeSb.getVisibleAmount() - absoluteValue);
                }
                isHorizontalAdjustedByUser = true;
            } else {
                if (!isVerticalAdjustedByUser) {
                    return;
                }
                isVerticalAdjustedByUser = false;
                java.util.List<FileDiffCodeBlock> codeBlockList;
                java.util.List<FileDiffCodeBlock> oppositeCodeBlockList;
                if (e.getSource() == leftEditor.getVerticalScrollBar()) {
                    codeBlockList = model.getFileDifference().getLeftCodeBlockList();
                    oppositeCodeBlockList = model.getFileDifference().getRightCodeBlockList();
                } else {
                    oppositeCodeBlockList = model.getFileDifference().getLeftCodeBlockList();
                    codeBlockList = model.getFileDifference().getRightCodeBlockList();
                }

                int delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, e.getValue(), leftEditor.getHeight());

                int value = source.getValue() + delta;
                oppositeSb.setValue(value);
                isVerticalAdjustedByUser = true;

                ui.getDivider().setLeftMove(leftEditor.getVerticalScrollBar().getValue());
                ui.getDivider().setRightMove(rightEditor.getVerticalScrollBar().getValue());
                ui.getDivider().repaint();
            }
        }
    }
}
