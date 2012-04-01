package ru.avokin.uidiff.diff.folderdiff.model;

/**
 * User: Andrey Vokin
 * Date: 28.09.2010
 */
public interface FolderDiffModelChangeListener {
    
    void statusChanged();

    void nodeInserted(DiffFileTreeNode inserted);

    void selectedNodesChanged(DiffFileTreeNode leftNode, DiffFileTreeNode rightNode);

    void compareActionEnablingChanged();

    void cancelActionEnablingChanged();
}
