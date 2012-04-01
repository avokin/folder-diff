package ru.avokin.uidiff.diff.folderdiff.model;

import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;

import java.util.ArrayList;
import java.util.List;

public class FolderDiffModel extends AbstractDiffModel {

    private final FolderDiffSideModel leftSideModel;

    private final FolderDiffSideModel rightSideModel;

    private String statusMessage;

    private boolean compareActionEnabled;

    private boolean cancelActionEnabled;


    private final List<FolderDiffModelChangeListener> folderDiffModelChangeListenerList;

    public FolderDiffModel(FolderDiffSideModel leftSideModel,  FolderDiffSideModel rightSideModel) {
        super(leftSideModel, rightSideModel);

        this.leftSideModel = leftSideModel;
        this.rightSideModel = rightSideModel;

        cancelActionEnabled = true;
        folderDiffModelChangeListenerList = new ArrayList<FolderDiffModelChangeListener>();
    }

    public void addItem(DiffFileTreeNode node1, DiffFileTreeNode parent1, DiffFileTreeNode node2, DiffFileTreeNode parent2) {
        leftSideModel.getTreeModel().insertNodeInto(node1, parent1, parent1.getChildCount());
        rightSideModel.getTreeModel().insertNodeInto(node2, parent2, parent2.getChildCount());

         for (FolderDiffModelChangeListener l : folderDiffModelChangeListenerList) {
            l.nodeInserted(node1);
        }
    }

    public void addFolderDiffModelChangeListener(FolderDiffModelChangeListener l) {
        folderDiffModelChangeListenerList.add(l);
    }

    public FolderDiffSideModel getLeftSideModel() {
        return leftSideModel;
    }

    public FolderDiffSideModel getRightSideModel() {
        return rightSideModel;
    }

    @Override
    public int getDifferenceCount() {
        return leftSideModel.getTreeModel().getDifferenceCount();
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;

        for (FolderDiffModelChangeListener l : folderDiffModelChangeListenerList) {
            l.statusChanged();
        }
    }

    public void setSelectedNodes(DiffFileTreeNode leftSelectedNode, DiffFileTreeNode rightSelectedNode) {
        leftSideModel.setSelectedNode(leftSelectedNode);
        rightSideModel.setSelectedNode(rightSelectedNode);

        for (FolderDiffModelChangeListener l : folderDiffModelChangeListenerList) {
            l.selectedNodesChanged(leftSelectedNode, rightSelectedNode);
        }
    }


    public boolean isCompareActionEnabled() {
        return compareActionEnabled;
    }

    public void setCompareActionEnabled(boolean compareActionEnabled) {
        this.compareActionEnabled = compareActionEnabled;

        for (FolderDiffModelChangeListener l : folderDiffModelChangeListenerList) {
            l.compareActionEnablingChanged();
        }
    }

     public boolean isCancelActionEnabled() {
        return cancelActionEnabled;
    }

    public void setCancelActionEnabled(boolean cancelActionEnabled) {
        this.cancelActionEnabled = cancelActionEnabled;

        for (FolderDiffModelChangeListener l : folderDiffModelChangeListenerList) {
            l.cancelActionEnablingChanged();
        }
    }
}