package ru.avokin.uidiff.fs.fsbrowser.model;

import ru.avokin.uidiff.common.model.AbstractModel;
import ru.avokin.uidiff.fs.common.model.FileTreeNode;

import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 02.08.2010
 */
public class FsBrowserModel extends AbstractModel {

    private final DefaultTreeModel leftModel;

    private final DefaultTreeModel rightModel;

    private FileTreeNode firstSelectedItem;

    private FileTreeNode secondSelectedItem;

    private boolean compareActionEnabled;

    private final List<FsBrowserModelListener> fsBrowserModelListenerList;

    public FsBrowserModel(DefaultTreeModel rightModel, DefaultTreeModel leftModel) {
        this.rightModel = rightModel;
        this.leftModel = leftModel;

        fsBrowserModelListenerList = new ArrayList<FsBrowserModelListener>();
    }

    public void addFsBrowserModelListener(FsBrowserModelListener fsBrowserModelListener) {
        fsBrowserModelListenerList.add(fsBrowserModelListener);
    }

    public DefaultTreeModel getLeftModel() {
        return leftModel;
    }

    public DefaultTreeModel getRightModel() {
        return rightModel;
    }

    public FileTreeNode getFirstSelectedItem() {
        return firstSelectedItem;
    }

    public void setSelectedItems(FileTreeNode firstSelectedItem, FileTreeNode secondSelectedItem) {
        this.firstSelectedItem = firstSelectedItem;
        this.secondSelectedItem = secondSelectedItem;

        for (FsBrowserModelListener fsBrowserModelListener : fsBrowserModelListenerList) {
            fsBrowserModelListener.selectedNodesChanged(firstSelectedItem, secondSelectedItem);
        }
    }

    public FileTreeNode getSecondSelectedItem() {
        return secondSelectedItem;
    }

    public boolean isCompareActionEnabled() {
        return compareActionEnabled;
    }

    public void setCompareActionEnabled(boolean compareActionEnabled) {
        this.compareActionEnabled = compareActionEnabled;

        for (FsBrowserModelListener fsBrowserModelListener : fsBrowserModelListenerList) {
            fsBrowserModelListener.compareActionEnablingChanged();
        }
    }
}
