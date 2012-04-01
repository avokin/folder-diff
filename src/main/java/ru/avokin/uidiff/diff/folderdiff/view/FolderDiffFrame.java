package ru.avokin.uidiff.diff.folderdiff.view;

import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;
import ru.avokin.uidiff.diff.common.view.AbstractDiffSplitPane;
import ru.avokin.uidiff.diff.folderdiff.model.DiffFileTreeNode;
import ru.avokin.uidiff.diff.folderdiff.model.DiffStatusEnum;
import ru.avokin.uidiff.diff.folderdiff.model.FolderDiffModel;
import ru.avokin.uidiff.diff.folderdiff.model.FolderDiffModelChangeListener;
import ru.avokin.uidiff.fs.common.view.DoubleTreeListener;
import ru.avokin.uidiff.common.view.actions.*;
import ru.avokin.uidiff.common.view.AbstractViewListener;
import ru.avokin.uidiff.diff.common.view.AbstractDiffFrame;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

/**
 * User: Andrey Vokin
 * Date: 31.07.2010
 */
public class FolderDiffFrame extends AbstractDiffFrame {

    private JTree leftTree;

    private JTree rightTree;

    private final CompareAction compareAction;

    private final ExpandAllAction expandAllAction;

    private final CollapseAllAction collapseAllAction;

    private final ExpandDiffAction expandDiffAction;

    private final CancelAction cancelAction;

    public FolderDiffFrame(FolderDiffModel model, ActionManager actionManager) {
        super(model, actionManager);

        compareAction = (CompareAction) actionManager.getAction(CompareAction.NAME);
        CompareActionListener compareListener = new FolderDiffCompareListenerImpl();
        compareAction.addListener(this, compareListener);

        cancelAction = (CancelAction) actionManager.getAction(CancelAction.NAME);
        CancelActionListener cancelListener = new FolderDiffCancelListenerImpl();
        cancelAction.addListener(this, cancelListener);


        expandAllAction = (ExpandAllAction) actionManager.getAction(ExpandAllAction.NAME);
        expandDiffAction = (ExpandDiffAction) actionManager.getAction(ExpandDiffAction.NAME);
        collapseAllAction = (CollapseAllAction) actionManager.getAction(CollapseAllAction.NAME);

        ExpandCollapseActionListener expandCollapseActionListener = new ExpandCollapseActionListenerImpl();
        expandAllAction.addListener(this, expandCollapseActionListener);
        expandDiffAction.addListener(this, expandCollapseActionListener);
        collapseAllAction.addListener(this, expandCollapseActionListener);


        FolderDiffModelChangeListener l = new FolderDiffModelChangeListenerImpl();
        model.addFolderDiffModelChangeListener(l);
    }

    @Override
    public AbstractDiffSplitPane createSplitPane(AbstractDiffModel model) {
        final FolderDiffModel folderDiffModel = (FolderDiffModel) model;

        FolderDiffSidePanel leftPanel = new FolderDiffSidePanel(folderDiffModel.getLeftSideModel(), true);
        FolderDiffSidePanel rightPanel = new FolderDiffSidePanel(folderDiffModel.getRightSideModel(), false);

        leftTree = leftPanel.getTree();
        rightTree = rightPanel.getTree();

        TreeExpansionListener expansionListener = new DiffTreeExpansionListener(leftTree, rightTree);
        leftPanel.getTree().addTreeExpansionListener(expansionListener);
        rightPanel.getTree().addTreeExpansionListener(expansionListener);

        TreeSelectionListener selectionListener = new DiffTreeSelectionListener(leftTree, rightTree);
        leftPanel.getTree().addTreeSelectionListener(selectionListener);
        rightPanel.getTree().addTreeSelectionListener(selectionListener);

        MouseListener ml = new DiffTreeClickListener();
        leftPanel.getTree().addMouseListener(ml);
        rightPanel.getTree().addMouseListener(ml);

        DiffTreeKeyListener kl = new DiffTreeKeyListener();
        leftPanel.getTree().addKeyListener(kl);
        rightPanel.getTree().addKeyListener(kl);

        return new FolderDiffSplitPane(leftPanel, rightPanel);
    }

    private boolean dfs(DiffFileTreeNode tn, final boolean expand, boolean expandOnlyChangedNode) {
        boolean result = tn.getStatus() != DiffStatusEnum.equal;

        Enumeration en = tn.children();
        while (en.hasMoreElements()) {
            DiffFileTreeNode child = (DiffFileTreeNode) en.nextElement();

            boolean changed = dfs(child, expand, expandOnlyChangedNode);
            result = result || changed;

            final TreePath tp1 = new TreePath(child.getPath());
            if (!expandOnlyChangedNode || changed) {
                if (expand) {
                    leftTree.expandPath(tp1);
                } else {
                    leftTree.collapsePath(tp1);
                }
            }

            if (expandOnlyChangedNode && !changed) {
                if (expand) {
                    leftTree.collapsePath(tp1);
                }
            }
        }

        return result;
    }

    public void fireOpenFileDiff() {
        if (compareAction.isEnabled()) {
            compareAction.actionPerformed(null);
        }
    }

    protected FolderDiffModel getModel() {
        return (FolderDiffModel) model;
    }

    @Override
    public void initActions() {
        super.initActions();
        expandAllAction.setEnabled(true);
        expandDiffAction.setEnabled(true);
        collapseAllAction.setEnabled(true);
        cancelAction.setEnabled(getModel().isCancelActionEnabled());
        compareAction.setEnabled(getModel().isCompareActionEnabled());
    }

    private class DiffTreeKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                fireOpenFileDiff();
            }
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    private class DiffTreeSelectionListener extends DoubleTreeListener implements TreeSelectionListener {

        DiffTreeSelectionListener(JTree t1, JTree t2) {
            super(t1, t2);
        }

        public void valueChanged(TreeSelectionEvent e) {
            DiffFileTreeNode selectedNode = (DiffFileTreeNode) e.getPath().getLastPathComponent();
            TreePath tp = getOppositeTreePath(selectedNode);
            getOppositeTree(e.getSource()).setSelectionPath(tp);

            DiffFileTreeNode leftSelectedNode = null;
            if (leftTree.getSelectionPath() != null) {
                leftSelectedNode = (DiffFileTreeNode) leftTree.getSelectionPath().getLastPathComponent();
            }
            DiffFileTreeNode rightSelectedNode = null;
            if (rightTree.getSelectionPath() != null) {
                rightSelectedNode = (DiffFileTreeNode) rightTree.getSelectionPath().getLastPathComponent();
            }
            getModel().setSelectedNodes(leftSelectedNode, rightSelectedNode);
        }
    }

    private class DiffTreeExpansionListener extends DoubleTreeListener implements TreeExpansionListener {

        DiffTreeExpansionListener(JTree t1, JTree t2) {
            super(t1, t2);
        }

        public void treeExpanded(TreeExpansionEvent e) {
            TreePath tp = getOppositeTreePath((DiffFileTreeNode) e.getPath().getLastPathComponent());
            getOppositeTree(e.getSource()).expandPath(tp);
        }

        public void treeCollapsed(TreeExpansionEvent e) {
            TreePath tp = getOppositeTreePath((DiffFileTreeNode) e.getPath().getLastPathComponent());
            getOppositeTree(e.getSource()).collapsePath(tp);
        }
    }

    private class DiffTreeClickListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) {
                fireOpenFileDiff();
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    class FolderDiffModelChangeListenerImpl implements FolderDiffModelChangeListener {

        public void statusChanged() {
            setStatusBarMessage(getModel().getStatusMessage());
        }

        public void nodeInserted(DiffFileTreeNode inserted) {
            if (inserted.getStatus() != DiffStatusEnum.equal) {
                leftTree.makeVisible(new TreePath(inserted.getPath()));
            }
        }

        public void selectedNodesChanged(DiffFileTreeNode leftNode, DiffFileTreeNode rightNode) {
            // ignored
        }

        public void compareActionEnablingChanged() {
            compareAction.setEnabled(getModel().isCompareActionEnabled());
        }

        public void cancelActionEnablingChanged() {
            cancelAction.setEnabled(getModel().isCancelActionEnabled());
        }
    }

    class ExpandCollapseActionListenerImpl implements ExpandCollapseActionListener {

        public void expandAllPerformed() {
            dfs((DiffFileTreeNode) leftTree.getModel().getRoot(), true, false);
        }

        public void expandDiffPerformed() {
            dfs((DiffFileTreeNode) leftTree.getModel().getRoot(), true, true);
        }

        public void collapseAllPerformed() {
            dfs((DiffFileTreeNode) leftTree.getModel().getRoot(), false, false);
        }
    }

    class FolderDiffCompareListenerImpl implements CompareActionListener {

        public void compare() {
            for (AbstractViewListener viewListener : viewListenerList) {
                ((FolderDiffViewListener) viewListener).compare();
            }
        }
    }

    class FolderDiffCancelListenerImpl implements CancelActionListener {

        public void cancel() {
            for (AbstractViewListener viewListener : viewListenerList) {
                ((FolderDiffViewListener) viewListener).cancel();
            }
        }
    }

}
