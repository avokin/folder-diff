package ru.avokin.uidiff.diff.common.view;

import ru.avokin.swing.utils.UiUtils;
import ru.avokin.uidiff.diff.common.model.DiffSideModel;

import javax.swing.*;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 01.10.2010
 */
public abstract class AbstractDiffSidePanel extends JPanel {

    protected final DiffSideModel model;

    protected AbstractDiffSidePanel(DiffSideModel model, boolean left) {
        super(new GridBagLayout());
        this.model = model;

        JComponent diffSideView = createDiffSideView(model, left);

        JLabel lblTitle = new JLabel(model.getFilePath());
        add(lblTitle, UiUtils.createGridBagConstraints(0, 0, UiUtils.DEFAULT_INSET, UiUtils.DEFAULT_INSET));
        add(diffSideView, UiUtils.createWideGridBagConstraints(0, 1, 1, UiUtils.DEFAULT_INSET, UiUtils.DEFAULT_INSET));
        setBorder(BorderFactory.createEmptyBorder());
    }

    protected abstract JComponent createDiffSideView(DiffSideModel model, boolean left);

    public abstract void selectDifference(int number, boolean withScrolling);

    public abstract JScrollBar getHorizontalScrollBar();

    public abstract JScrollBar getVerticalScrollBar();

    protected DiffSideModel getModel() {
        return model;
    }
}
