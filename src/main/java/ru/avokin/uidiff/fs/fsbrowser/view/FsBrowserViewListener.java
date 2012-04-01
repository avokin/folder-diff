package ru.avokin.uidiff.fs.fsbrowser.view;

import ru.avokin.uidiff.fs.common.model.FileTreeNode;

import javax.swing.tree.DefaultTreeModel;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public interface FsBrowserViewListener {
    
    void compare();

    void expand(DefaultTreeModel treeModel, FileTreeNode node);
}
