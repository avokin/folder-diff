package ru.avokin.uidiff.common.view.actions;

import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 24.09.2010
 */
public class CompareAction extends BaseAction<CompareActionListener> {

    public static final String NAME = "Compare";

    private static final URL ICON_COMPARE_URL = CompareAction.class.getResource("/icons/16x16/compare.png");

    private static final Icon ICON_COMPARE = new ImageIcon(ICON_COMPARE_URL);

    private static final String TITLE = "Compare selected nodes (Enter or Alt + F5)";

    private static final int MNEMONIC = KeyEvent.VK_F5;

    public CompareAction(ViewManager viewManager) {
       super(viewManager, NAME, ICON_COMPARE);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<CompareActionListener> listeners = getListeners();
        for (CompareActionListener l : listeners) {
            l.compare();
        }
    }
}
