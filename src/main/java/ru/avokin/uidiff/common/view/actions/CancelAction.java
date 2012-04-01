package ru.avokin.uidiff.common.view.actions;

import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 28.09.2010
 */
public class CancelAction extends BaseAction<CancelActionListener> {

    public static final String NAME = "Cancel";

    private static final URL ICON_CANCEL_URL = CompareAction.class.getResource("/icons/16x16/cancel.png");

    private static final Icon ICON_CANCEL = new ImageIcon(ICON_CANCEL_URL);

    private static final String TITLE = "Cancel current operation (Escape)";

    private static final int MNEMONIC = KeyEvent.VK_ESCAPE;

    public CancelAction(ViewManager viewManager) {
       super(viewManager, NAME, ICON_CANCEL);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<CancelActionListener> listeners = getListeners();
        for (CancelActionListener l : listeners) {
            l.cancel();
        }
    }
}
