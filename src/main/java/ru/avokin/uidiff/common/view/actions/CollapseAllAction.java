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
public class CollapseAllAction extends BaseAction<ExpandCollapseActionListener> {

    public static final String NAME = "Collapse all folders";

    private static final URL ICON_COLLAPSE_ALL_URL = CollapseAllAction.class.getResource("/icons/16x16/collapseAll.png");

    private static final Icon ICON_COLLAPSE_ALL = new ImageIcon(ICON_COLLAPSE_ALL_URL);

    private static final String TITLE = "Collapse all folders (Alt + F3)";

    private static final int MNEMONIC = KeyEvent.VK_F3;

    public CollapseAllAction(ViewManager viewManager) {
        super(viewManager, NAME, ICON_COLLAPSE_ALL);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<ExpandCollapseActionListener> listeners = getListeners();
        for (ExpandCollapseActionListener collapseCollapseActionListener : listeners) {
            collapseCollapseActionListener.collapseAllPerformed();
        }
    }
}
