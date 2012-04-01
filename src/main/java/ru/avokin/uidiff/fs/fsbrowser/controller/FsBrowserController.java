package ru.avokin.uidiff.fs.fsbrowser.controller;

import org.apache.log4j.Logger;
import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.uidiff.common.controller.AbstractController;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;
import ru.avokin.uidiff.diff.filediff.controller.FileDiffController;
import ru.avokin.uidiff.diff.folderdiff.controller.FolderDiffController;
import ru.avokin.uidiff.fs.common.model.FileTreeNode;
import ru.avokin.uidiff.fs.common.model.FileTypeEnum;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModel;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModelListener;
import ru.avokin.uidiff.fs.fsbrowser.view.FsBrowserFrame;
import ru.avokin.utils.FileNameComparator;
import ru.avokin.utils.TreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 01.08.2010
 */
public class FsBrowserController extends AbstractController {

    private static final Logger logger = Logger.getLogger(FsBrowserController.class);

    private final CodeHighlighterManager codeHighlighterManager;

    public FsBrowserController(final ViewManager viewFactory, CodeHighlighterManager codeHighlighterManager) {
        super(viewFactory);
        this.codeHighlighterManager = codeHighlighterManager;
        DefaultMutableTreeNode leftRoot = new FileTreeNode(FileTreeNode.COMPUTER_FS_NAME, null, FileTypeEnum.computer);
        DefaultMutableTreeNode rightRoot = new FileTreeNode(FileTreeNode.COMPUTER_FS_NAME, null, FileTypeEnum.computer);
        File[] roots = File.listRoots();
        for (File root : roots) {
            String path =  root.getAbsolutePath();
            FileTreeNode fileTreeNodeLeft = new FileTreeNode(path, null, FileTypeEnum.disk);
            leftRoot.add(fileTreeNodeLeft);

            FileTreeNode fileTreeNodeRight = new FileTreeNode(path, null, FileTypeEnum.disk);
            rightRoot.add(fileTreeNodeRight);
        }

        DefaultTreeModel leftModel = new DefaultTreeModel(leftRoot, true);
        DefaultTreeModel rightModel = new DefaultTreeModel(rightRoot, true);

        model = new FsBrowserModel(leftModel, rightModel);
        getModel().addFsBrowserModelListener(new FsBrowserModelListenerImpl());

        view = viewFactory.createFsBrowserFrame(getModel());
        getView().addViewListener(new FsBrowserViewListenerImpl(this));
    }

    public FsBrowserModel getModel() {
        return (FsBrowserModel) model;
    }

    public FsBrowserFrame getView() {
        return (FsBrowserFrame) view;
    }

    public void diff() {
        diff(getModel().getFirstSelectedItem(), getModel().getSecondSelectedItem());
    }

    public void expand(final DefaultTreeModel treeModel, final FileTreeNode node) {
        if (node.getChildCount() > 0) {
            return;
        }

        SwingWorker worker = new SwingWorker<List<FileTreeNode>, Void>() {
            @Override
            protected List<FileTreeNode> doInBackground() throws Exception {
                File f = TreeUtils.calculateAbsolutePath(node);
                File[] children = f.listFiles();
                if (children != null) {
                    Arrays.sort(children, FileNameComparator.getInstance());
                }
                final List<FileTreeNode> childrenToAddToModel = new ArrayList<FileTreeNode>();
                if (children != null) {
                    for (File fileChild : children) {
                        @SuppressWarnings({"ConditionalExpression"})
                        FileTreeNode fileTreeNode = new FileTreeNode(fileChild.getName(), null, fileChild.isDirectory() ? FileTypeEnum.folder : FileTypeEnum.file);
                        fileTreeNode.setAllowsChildren(fileChild.isDirectory());
                        childrenToAddToModel.add(fileTreeNode);
                    }
                }
                return childrenToAddToModel;
            }

            @Override
            protected void done() {
                List<FileTreeNode> childrenToAddToModel = null;
                try {
                    childrenToAddToModel = get();
                } catch (Exception e) {
                    logger.error(e, e);
                }
                if (childrenToAddToModel != null) {
                    for (FileTreeNode fileTreeNode : childrenToAddToModel) {
                        treeModel.insertNodeInto(fileTreeNode, node, node.getChildCount());
                    }
                }
            }
        };
        worker.execute();
    }

    protected void diff(FileTreeNode leftNode, FileTreeNode rightNode) {
        final File leftFile = TreeUtils.calculateAbsolutePath(leftNode);
        final File rightFile = TreeUtils.calculateAbsolutePath(rightNode);

        if (leftFile.isFile() && rightFile.isFile()) {
            new FileDiffController(viewManager, codeHighlighterManager, leftFile, rightFile);
        } else if (leftFile.isDirectory() && rightFile.isDirectory()) {
            new FolderDiffController(viewManager, codeHighlighterManager, leftFile, rightFile);
        } else {
            // File and folder selected. Ignored.
        }
    }

    class FsBrowserModelListenerImpl implements FsBrowserModelListener {

        public void selectedNodesChanged(FileTreeNode firstNode, FileTreeNode secondNode) {
            boolean expectedEnabled = false;
            if (firstNode != null && secondNode != null) {
                if (firstNode.getFileType() == secondNode.getFileType()) {
                    expectedEnabled = true;
                }
            }

            if (getModel().isCompareActionEnabled() != expectedEnabled) {
                getModel().setCompareActionEnabled(expectedEnabled);
            }
        }

        public void compareActionEnablingChanged() {
            // ignored
        }
    }
}
