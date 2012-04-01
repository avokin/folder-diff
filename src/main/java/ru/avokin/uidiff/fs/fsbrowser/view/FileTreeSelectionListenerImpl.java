package ru.avokin.uidiff.fs.fsbrowser.view;

import ru.avokin.uidiff.fs.common.model.FileTreeNode;
import ru.avokin.uidiff.fs.common.view.DoubleTreeListener;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 * User: Andrey Vokin
 * Date: 27.09.2010
 */
class FileTreeSelectionListenerImpl extends DoubleTreeListener implements TreeSelectionListener {

    private final FsBrowserModel model;

    protected FileTreeSelectionListenerImpl(FsBrowserModel model, JTree t1, JTree t2) {
        super(t1, t2);
        this.model = model;
    }

    public void valueChanged(TreeSelectionEvent e) {
        JTree source = (JTree) e.getSource();
        JTree opposite = getOppositeTree(e.getSource());

        FileTreeNode firstNode = null;
        FileTreeNode secondNode = null;
        if (source.getSelectionPaths() != null && source.getSelectionPaths().length >= 2) {
            int sourceCount = source.getSelectionPaths().length;
            TreePath[] sourceTreePath = new TreePath[2];
            sourceTreePath[0] = source.getSelectionPaths()[sourceCount - 2];
            sourceTreePath[1] = source.getSelectionPaths()[sourceCount - 1];

            opposite.setSelectionPaths(new TreePath[0]);
            source.setSelectionPaths(sourceTreePath);

            firstNode = (FileTreeNode) sourceTreePath[0].getLastPathComponent();
            secondNode = (FileTreeNode) sourceTreePath[1].getLastPathComponent();
        } else if (source.getSelectionPaths() != null && source.getSelectionPaths().length == 1 &&
                opposite.getSelectionPaths() != null && opposite.getSelectionPaths().length > 1) {
            int oppositeCount = opposite.getSelectionPaths().length;
            TreePath[] oppositeTreePath = new TreePath[1];
            oppositeTreePath[0] = opposite.getSelectionPaths()[oppositeCount - 1];
            opposite.setSelectionPaths(oppositeTreePath);

            firstNode = (FileTreeNode) t1.getSelectionPath().getLastPathComponent();
            secondNode = (FileTreeNode) t2.getSelectionPath().getLastPathComponent();
        } else {
            if (t1.getSelectionPath() != null) {
                firstNode = (FileTreeNode) t1.getSelectionPath().getLastPathComponent();
            }
            if (t2.getSelectionPath() != null) {
                secondNode= (FileTreeNode) t2.getSelectionPath().getLastPathComponent();
            }
        }

        model.setSelectedItems(firstNode, secondNode);
    }
}