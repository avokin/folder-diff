package ru.avokin.uidiff.diff.folderdiff.controller;

import ru.avokin.uidiff.diff.common.controller.DiffViewListenerImpl;
import ru.avokin.uidiff.diff.folderdiff.view.FolderDiffViewListener;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public class FolderDiffViewListenerImpl extends DiffViewListenerImpl implements FolderDiffViewListener {

    public FolderDiffViewListenerImpl(FolderDiffController controller) {
        super(controller);
    }

    protected FolderDiffController getController() {
        return (FolderDiffController) controller;
    }

    public void compare() {
        getController().compare();
    }

    public void cancel() {
        getController().cancel();
    }
}
