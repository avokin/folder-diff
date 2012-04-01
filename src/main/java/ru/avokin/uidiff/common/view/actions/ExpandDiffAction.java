package ru.avokin.uidiff.common.view.actions;

import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 16.09.2010
 */
public class ExpandDiffAction extends BaseAction<ExpandCollapseActionListener> {

    public static final String NAME = "Expand Diff";

    private static final URL ICON_EXPAND_ALL_URL = ExpandAllAction.class.getResource("/icons/16x16/expandDiff.png");

    private static final Icon ICON_EXPAND_DIFF = new ImageIcon(ICON_EXPAND_ALL_URL);

    private static final String TITLE = "Expand all differed folders (Alt + F1)";

    private static final int MNEMONIC = KeyEvent.VK_F1;

    public ExpandDiffAction(ViewManager viewManager) {
        super(viewManager, NAME, ICON_EXPAND_DIFF);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<ExpandCollapseActionListener> listeners = getListeners();
        for (ExpandCollapseActionListener expandCollapseActionListener : listeners) {
            expandCollapseActionListener.expandDiffPerformed();
        }
    }
}
