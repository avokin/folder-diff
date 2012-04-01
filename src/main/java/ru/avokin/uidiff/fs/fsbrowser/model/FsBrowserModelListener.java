package ru.avokin.uidiff.fs.fsbrowser.model;

import ru.avokin.uidiff.fs.common.model.FileTreeNode;

/**
 * User: Andrey Vokin
 * Date: 04.10.2010
 */
public interface FsBrowserModelListener {
    
    void selectedNodesChanged(FileTreeNode firstNode, FileTreeNode secondNode);

    void compareActionEnablingChanged();
}
