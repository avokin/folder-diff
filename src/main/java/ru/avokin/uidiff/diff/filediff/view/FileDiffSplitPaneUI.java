package ru.avokin.uidiff.diff.filediff.view;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Created by Andrey Vokin.
 * Date: 24.07.2010
 */
public class FileDiffSplitPaneUI extends BasicSplitPaneUI {

    @SuppressWarnings({"UnusedDeclaration"})
    public static ComponentUI createUI(JComponent x) {
        return new FileDiffSplitPaneUI();
    }

    public BasicSplitPaneDivider createDefaultDivider() {
        return new FileDiffSplitPaneDivider(this);
    }

    public FileDiffSplitPaneDivider getDivider() {
        return (FileDiffSplitPaneDivider) divider;
    }
}
