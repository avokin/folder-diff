package ru.avokin.uidiff.diff.folderdiff.view;

import ru.avokin.uidiff.diff.common.model.DiffSideModel;
import ru.avokin.uidiff.diff.common.view.AbstractDiffSidePanel;
import ru.avokin.uidiff.diff.folderdiff.model.DiffFileTreeNode;
import ru.avokin.uidiff.diff.folderdiff.model.DiffTreeModel;
import ru.avokin.uidiff.diff.folderdiff.model.FolderDiffSideModel;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 01.10.2010
 */
public class FolderDiffSidePanel extends AbstractDiffSidePanel {

    private JTree tree;

    private JScrollPane scrollPane;

    public FolderDiffSidePanel(FolderDiffSideModel model, boolean left) {
        super(model, left);
    }

    @Override
    protected JComponent createDiffSideView(DiffSideModel model, boolean left) {
        FolderDiffSideModel folderDiffSideModel = (FolderDiffSideModel) model;

        tree = new JTree(folderDiffSideModel.getTreeModel());

        FolderDiffTreeUI ui = new FolderDiffTreeUI();
        tree.setUI(ui);
        tree.setCellRenderer(ui.getCellRenderer());

        scrollPane =
                new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        if (left) {
            scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        return scrollPane;
    }

    protected FolderDiffSideModel getModel() {
        return (FolderDiffSideModel) model;
    }

    @Override
    public void selectDifference(int number, boolean withScrolling) {
        DiffTreeModel treeModel = getModel().getTreeModel();
        DiffFileTreeNode node = treeModel.getDifference(number);
        if (node != null) {
            TreePath tp = new TreePath(node.getPath());
            tree.setSelectionPath(tp);
            tree.scrollPathToVisible(tp);
        }
    }

    @Override
    public JScrollBar getHorizontalScrollBar() {
        return scrollPane.getHorizontalScrollBar();
    }

    @Override
    public JScrollBar getVerticalScrollBar() {
        return scrollPane.getVerticalScrollBar();
    }

    public JTree getTree() {
        return tree;
    }
}
