package ru.avokin.uidiff.common.view.actions;

import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * Created by Andrey Vokin.
 * Date: 21.08.2010
 */
public class PrevDiffAction extends BaseAction<CurrentDifferenceMovedListener> {

    public static final String NAME = "Previous diff";

    private static final URL ICON_TOP_ARROW_URL = PrevDiffAction.class.getResource("/icons/16x16/prevDiff.png");

    private static final Icon ICON_TOP_ARROW = new ImageIcon(ICON_TOP_ARROW_URL);

    private static final String TITLE = "Select previous difference (Alt + F8)";

    private static final int MNEMONIC = KeyEvent.VK_F8;

    public PrevDiffAction(ViewManager viewManager) {
        super(viewManager, NAME, ICON_TOP_ARROW);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<CurrentDifferenceMovedListener> listeners = getListeners();
        for (CurrentDifferenceMovedListener currentDifferenceMovedListener : listeners) {
            currentDifferenceMovedListener.CurrentDifferenceDecremented();
        }
    }
}
