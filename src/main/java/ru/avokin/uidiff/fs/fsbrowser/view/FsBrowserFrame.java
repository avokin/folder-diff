package ru.avokin.uidiff.fs.fsbrowser.view;

import ru.avokin.swing.utils.UiUtils;
import ru.avokin.uidiff.common.view.AbstractViewListener;
import ru.avokin.uidiff.common.view.View;
import ru.avokin.uidiff.common.view.actions.ActionManager;
import ru.avokin.uidiff.common.view.actions.CompareAction;
import ru.avokin.uidiff.common.view.actions.CompareActionListener;
import ru.avokin.uidiff.fs.common.model.FileTreeNode;
import ru.avokin.uidiff.fs.common.view.FileSystemIconTreeCellRenderer;
import ru.avokin.uidiff.fs.common.view.FsTreeUI;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModel;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModelListener;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.*;

/**
 * User: Andrey Vokin
 * Date: 01.08.2010
 */
public class FsBrowserFrame extends View {

    private final JTree leftTree;

    private final JSplitPane splitPane;

    private final CompareAction compareAction;

    public FsBrowserFrame(FsBrowserModel model, ActionManager actionManager) {
        super(model);

        leftTree = new JTree(model.getLeftModel());
        leftTree.setUI(new FsTreeUI());

        JTree rightTree = new JTree(model.getRightModel());
        rightTree.setUI(new FsTreeUI());

        TreeCellRenderer cellRenderer = new FileSystemIconTreeCellRenderer();

        FsTreeExpansionListenerImpl expansionListener = new FsTreeExpansionListenerImpl();
        leftTree.addTreeExpansionListener(expansionListener);
        leftTree.setCellRenderer(cellRenderer);

        rightTree.addTreeExpansionListener(expansionListener);
        rightTree.setCellRenderer(cellRenderer);

        MouseListener ml = new FileTreeClickListener();
        leftTree.addMouseListener(ml);
        rightTree.addMouseListener(ml);

        KeyListener kl = new FileTreeKeyListener();
        leftTree.addKeyListener(kl);
        rightTree.addKeyListener(kl);

        TreeSelectionListener sl = new FileTreeSelectionListenerImpl(model, leftTree, rightTree);
        leftTree.addTreeSelectionListener(sl);
        rightTree.addTreeSelectionListener(sl);

        JScrollPane leftScrollPane = new JScrollPane(leftTree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftScrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JScrollPane rightScrollPane = new JScrollPane(rightTree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setLayout(new GridBagLayout());

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, rightScrollPane);
        addComponentListener(new FsBrowserComponentListener());
        add(splitPane, UiUtils.createWideGridBagConstraints(0, 0, 1, 0, 0));

        compareAction = (CompareAction) actionManager.getAction(CompareAction.NAME);
        CompareActionListener compareActionListener = new CompareActionListenerImpl();
        compareAction.addListener(this, compareActionListener);

        getModel().addFsBrowserModelListener(new FsBrowserModelListenerImpl());
    }

    protected FsBrowserModel getModel() {
        return (FsBrowserModel) model;
    }

    @Override
    public void initActions() {
        super.initActions();
        compareAction.setEnabled(getModel().isCompareActionEnabled());
    }

    class CompareActionListenerImpl implements CompareActionListener {

        public void compare() {
            for (AbstractViewListener viewListener : viewListenerList) {
                ((FsBrowserViewListener) viewListener).compare();
            }
        }
    }

    private class FileTreeClickListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) {
                compareAction.actionPerformed(null);
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

    private class FileTreeKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
                compareAction.actionPerformed(null);
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    private class FsBrowserComponentListener implements ComponentListener {

        public void componentResized(ComponentEvent e) {
            splitPane.setDividerLocation(0.5);
        }

        public void componentMoved(ComponentEvent e) {
        }

        public void componentShown(ComponentEvent e) {
        }

        public void componentHidden(ComponentEvent e) {
        }
    }

    private class FsTreeExpansionListenerImpl implements TreeExpansionListener {

        public void treeExpanded(TreeExpansionEvent e) {
            if (e.getPath().getLastPathComponent() instanceof DefaultMutableTreeNode) {
                DefaultTreeModel treeModel;
                if (e.getSource() == leftTree) {
                    treeModel = getModel().getLeftModel();
                } else {
                    treeModel = getModel().getRightModel();
                }
                for (AbstractViewListener viewListener : viewListenerList) {
                    ((FsBrowserViewListener) viewListener).expand(treeModel, (FileTreeNode) e.getPath().getLastPathComponent());
                }
            }
        }

        public void treeCollapsed(TreeExpansionEvent e) {
        }
    }

    private class FsBrowserModelListenerImpl implements FsBrowserModelListener {

        public void selectedNodesChanged(FileTreeNode firstNode, FileTreeNode secondNode) {
            // ignored.
        }

        public void compareActionEnablingChanged() {
            compareAction.setEnabled(getModel().isCompareActionEnabled());
        }
    }
}
