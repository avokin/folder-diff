package ru.avokin.uidiff.fs.fsbrowser.controller;

import ru.avokin.uidiff.common.controller.AbstractViewListenerImpl;
import ru.avokin.uidiff.fs.common.model.FileTreeNode;
import ru.avokin.uidiff.fs.fsbrowser.view.FsBrowserViewListener;

import javax.swing.tree.DefaultTreeModel;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public class FsBrowserViewListenerImpl extends AbstractViewListenerImpl implements FsBrowserViewListener {

    public FsBrowserViewListenerImpl(FsBrowserController controller) {
        super(controller);
    }

    protected FsBrowserController getController() {
        return (FsBrowserController) controller;
    }


    public void compare() {
        getController().diff();
    }

    public void expand(DefaultTreeModel treeModel, FileTreeNode node) {
        getController().expand(treeModel, node);
    }
}
