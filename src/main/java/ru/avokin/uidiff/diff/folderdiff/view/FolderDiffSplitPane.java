package ru.avokin.uidiff.diff.folderdiff.view;

import ru.avokin.uidiff.diff.common.view.AbstractDiffSplitPane;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * User: Andrey Vokin
 * Date: 24.08.2010
 */
public class FolderDiffSplitPane extends AbstractDiffSplitPane {

    protected FolderDiffSplitPane(FolderDiffSidePanel leftComponent, FolderDiffSidePanel rightComponent) {
        super(leftComponent, rightComponent);
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public AdjustmentListener createAdjustmentListener() {
        return new FolderDiffAdjustmentListenerImpl();
    }

    class FolderDiffAdjustmentListenerImpl implements AdjustmentListener {

    public FolderDiffAdjustmentListenerImpl() {
    }

    private boolean isAdjustmentByUser = true;

    private JScrollBar getOppositeScrollBar(JScrollBar source) {
        JScrollBar result;
        //noinspection IfStatementWithTooManyBranches
        if (source == leftComponent.getHorizontalScrollBar()) {
            result = rightComponent.getHorizontalScrollBar();
        } else if (source == leftComponent.getVerticalScrollBar()) {
            result = rightComponent.getVerticalScrollBar();
        } else if (source == rightComponent.getHorizontalScrollBar()) {
            result = leftComponent.getHorizontalScrollBar();
        } else if (source == rightComponent.getVerticalScrollBar()) {
            result = leftComponent.getVerticalScrollBar();
        } else {
            throw new IllegalArgumentException("Unexpected event source.");
        }

        return result;
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (!isAdjustmentByUser) {
            return;
        }

        JScrollBar source = (JScrollBar) e.getSource();
        JScrollBar oppositeSb = getOppositeScrollBar(source);

        if (JScrollBar.HORIZONTAL == source.getOrientation()) {
            isAdjustmentByUser = false;
            double relativeValue = 1.0 * source.getValue() /(source.getMaximum() - source.getVisibleAmount());
            int absoluteValue = (int) ((oppositeSb.getMaximum() - oppositeSb.getVisibleAmount()) * relativeValue);
            oppositeSb.setValue(oppositeSb.getMaximum() - oppositeSb.getVisibleAmount() - absoluteValue);

            isAdjustmentByUser = true;
        } else {
            isAdjustmentByUser = false;
            oppositeSb.setValue(source.getValue());
            isAdjustmentByUser = true;
        }
    }
}
}
