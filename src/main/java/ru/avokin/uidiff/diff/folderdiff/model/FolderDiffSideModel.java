package ru.avokin.uidiff.diff.folderdiff.model;

import ru.avokin.uidiff.diff.common.model.DiffSideModel;

/**
 * User: Andrey Vokin
 * Date: 01.10.2010
 */
public class FolderDiffSideModel extends DiffSideModel {

    private final DiffTreeModel treeModel;

    private DiffFileTreeNode selectedNode;

    public FolderDiffSideModel(DiffTreeModel treeModel, String fileName, String filePath) {
        super(fileName, filePath);
        this.treeModel = treeModel;
    }

    public DiffTreeModel getTreeModel() {
        return treeModel;
    }

    public DiffFileTreeNode getSelectedNode() {
        return selectedNode;
    }

    void setSelectedNode(DiffFileTreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}
