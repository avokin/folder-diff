package ru.avokin.uidiff.common.view.actions;

import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 31.08.2010
 */
public class ExpandAllAction extends BaseAction<ExpandCollapseActionListener> {

    public static final String NAME = "Expand All";

    private static final URL ICON_EXPAND_ALL_URL = ExpandAllAction.class.getResource("/icons/16x16/expandAll.png");

    private static final Icon ICON_EXPAND_ALL = new ImageIcon(ICON_EXPAND_ALL_URL);

    private static final String TITLE = "Expand all folders (Alt + F2)";

    private static final int MNEMONIC = KeyEvent.VK_F2;

    public ExpandAllAction(ViewManager viewManager) {
        super(viewManager, NAME, ICON_EXPAND_ALL);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<ExpandCollapseActionListener> listeners = getListeners();
        for (ExpandCollapseActionListener expandCollapseActionListener : listeners) {
            expandCollapseActionListener.expandAllPerformed();
        }
    }
}
