package ru.avokin.uidiff.diff.common.view;

import javax.swing.*;
import java.awt.event.AdjustmentListener;

/**
 * User: Andrey Vokin
 * Date: 23.08.2010
 */
public abstract class AbstractDiffSplitPane extends JSplitPane {

    public static final String UI_CLASS_ID = "AbstractDiffSplitPane";

    protected final AbstractDiffSidePanel leftComponent;

    protected final AbstractDiffSidePanel rightComponent;

    protected AbstractDiffSplitPane(AbstractDiffSidePanel leftComponent, AbstractDiffSidePanel rightComponent) {
        super(JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent);
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }

    public void initAdjustmentListeners() {
        AdjustmentListener al = createAdjustmentListener();
        leftComponent.getVerticalScrollBar().addAdjustmentListener(al);
        leftComponent.getHorizontalScrollBar().addAdjustmentListener(al);
        rightComponent.getVerticalScrollBar().addAdjustmentListener(al);
        rightComponent.getHorizontalScrollBar().addAdjustmentListener(al);
        
        leftComponent.getHorizontalScrollBar().setValue(0);
        rightComponent.getHorizontalScrollBar().setValue(0);
    }

    public void selectDifference(int number) {
        leftComponent.selectDifference(number, true);
        rightComponent.selectDifference(number, false);
    }

    @Override
    public String getUIClassID() {
        return UI_CLASS_ID;
    }

    protected abstract AdjustmentListener createAdjustmentListener();
}
