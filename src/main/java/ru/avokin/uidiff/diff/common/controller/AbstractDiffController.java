package ru.avokin.uidiff.diff.common.controller;

import ru.avokin.uidiff.common.controller.AbstractController;
import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;
import ru.avokin.uidiff.diff.common.view.DiffViewListener;
import ru.avokin.uidiff.diff.common.view.AbstractDiffFrame;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

/**
 * User: Andrey Vokin
 * Date: 23.08.2010
 */
public abstract class AbstractDiffController extends AbstractController {

    protected AbstractDiffModel model;

    protected AbstractDiffFrame view;

    protected AbstractDiffController(ViewManager viewFactory) {
        super(viewFactory);
    }

    protected void viewInitialized() {
        DiffViewListener l = new DiffViewListenerImpl(this);
        view.addViewListener(l);
    }

    protected void checkNextAndPreviousDiffActionEnabling() {
        boolean expectedNextDiffEnabled = model.getCurrentDifference() < model.getDifferenceCount() - 1;
        boolean expectedPreviousDiffEnabled = true;

        if (expectedNextDiffEnabled != model.isNextDiffActionEnabled()) {
            model.setNextDiffActionEnabled(expectedNextDiffEnabled);
        }
        if (expectedPreviousDiffEnabled != model.isPreviousDiffActionEnabled()) {
            model.setPreviousDiffActionEnabled(expectedPreviousDiffEnabled);
        }
    }

    public void nextDifference() {
        if (model.getCurrentDifference() < model.getDifferenceCount() - 1) {
            model.setCurrentDifference(model.getCurrentDifference() + 1);
        }
        checkNextAndPreviousDiffActionEnabling();
    }

    public void previousDifference() {
        if (model.getCurrentDifference() > 0) {
            model.setCurrentDifference(model.getCurrentDifference() - 1);
        }
        checkNextAndPreviousDiffActionEnabling();
    }
}
