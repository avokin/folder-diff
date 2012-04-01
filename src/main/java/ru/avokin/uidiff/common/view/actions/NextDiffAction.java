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
public class NextDiffAction extends BaseAction<CurrentDifferenceMovedListener> {

    public static final String NAME = "Next difference";

    private static final URL ICON_BOTTOM_ARROW_URL = NextDiffAction.class.getResource("/icons/16x16/nextDiff.png");

    private static final Icon ICON_BOTTOM_ARROW = new ImageIcon(ICON_BOTTOM_ARROW_URL);

    private static final String TITLE = "Select next difference (Alt + F7)";

    private static final int MNEMONIC = KeyEvent.VK_F7;

    public NextDiffAction(ViewManager viewManager) {
        super(viewManager, NAME, ICON_BOTTOM_ARROW);
        putValue(SHORT_DESCRIPTION, TITLE);
        putValue(MNEMONIC_KEY, MNEMONIC);
    }

    public void actionPerformed(ActionEvent e) {
        java.util.List<CurrentDifferenceMovedListener> listeners = getListeners();
        for (CurrentDifferenceMovedListener l : listeners) {
            l.CurrentDifferenceIncremented();
        }
    }
}
