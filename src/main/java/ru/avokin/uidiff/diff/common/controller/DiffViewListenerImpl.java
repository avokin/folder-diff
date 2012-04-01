package ru.avokin.uidiff.diff.common.controller;

import ru.avokin.uidiff.common.controller.AbstractViewListenerImpl;
import ru.avokin.uidiff.diff.common.view.DiffViewListener;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public class DiffViewListenerImpl extends AbstractViewListenerImpl implements DiffViewListener {

    public DiffViewListenerImpl(AbstractDiffController controller) {
        super(controller);
    }

    protected AbstractDiffController getController() {
        return (AbstractDiffController) controller;
    }

    public void nextDifference() {
        getController().nextDifference();
    }

    public void previousDifference() {
        getController().previousDifference();
    }
}