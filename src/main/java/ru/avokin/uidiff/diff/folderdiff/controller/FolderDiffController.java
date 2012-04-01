package ru.avokin.uidiff.diff.folderdiff.controller;

import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.uidiff.diff.common.controller.AbstractDiffController;
import ru.avokin.uidiff.diff.filediff.controller.FileDiffController;
import ru.avokin.uidiff.diff.folderdiff.model.*;
import ru.avokin.uidiff.diff.folderdiff.view.FolderDiffViewListener;
import ru.avokin.uidiff.fs.common.model.FileTypeEnum;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;
import ru.avokin.utils.TreeUtils;

import java.io.File;

/**
 * User: Andrey Vokin
 * Date: 01.08.2010
 */
public class FolderDiffController extends AbstractDiffController {

    private final FolderDiffWorker folderDiffWorker;

    private final CodeHighlighterManager codeHighlighterManager;

    public FolderDiffController(final ViewManager viewFactory, CodeHighlighterManager codeHighlighterManager, File leftFolder, File rightFolder) {
        super(viewFactory);
        this.codeHighlighterManager = codeHighlighterManager;

        model = createDiffResult(leftFolder, rightFolder);

        folderDiffWorker = new FolderDiffWorker(leftFolder, rightFolder, getModel(), this);
        folderDiffWorker.execute();

        view = viewFactory.createFolderDiffFrame(getModel());
        viewInitialized();

        getModel().addFolderDiffModelChangeListener(new FolderDiffModelChangeListenerImpl());
    }

    protected FolderDiffModel createDiffResult(File f1, File f2) {
        String fsPath1 = f1.getName();
        String fsPath2 = f2.getName();
        @SuppressWarnings({"ConditionalExpression"})
        DiffFileTreeNode root1 = new DiffFileTreeNode(fsPath1, f1.getAbsolutePath(), f1.isDirectory() ? FileTypeEnum.folder : FileTypeEnum.file);
        @SuppressWarnings({"ConditionalExpression"})
        DiffFileTreeNode root2 = new DiffFileTreeNode(fsPath2, f2.getAbsolutePath(), f1.isDirectory() ? FileTypeEnum.folder : FileTypeEnum.file);
        root1.setOppositeNode(root2);
        root2.setOppositeNode(root1);
        root1.setStatus(DiffStatusEnum.equal);
        root2.setStatus(DiffStatusEnum.equal);

        DiffTreeModel leftTreeModel = new DiffTreeModel(root1);
        FolderDiffSideModel leftSideModel = new FolderDiffSideModel(leftTreeModel, f1.getName(), f1.getAbsolutePath());

        DiffTreeModel rightTreeModel = new DiffTreeModel(root2);
        FolderDiffSideModel rightSideModel = new FolderDiffSideModel(rightTreeModel, f2.getName(), f2.getAbsolutePath());

        return new FolderDiffModel(leftSideModel, rightSideModel);
    }

    public FolderDiffModel getModel() {
        return (FolderDiffModel) model;
    }

    @Override
    protected void viewInitialized() {
        FolderDiffViewListener folderDiffViewListener = new FolderDiffViewListenerImpl(this);
        view.addViewListener(folderDiffViewListener);
    }

    public void compare() {
        if (getModel().getLeftSideModel().getSelectedNode() == null ||
                getModel().getRightSideModel().getSelectedNode() == null) {
            return;
        }

        final File f1 = TreeUtils.calculateAbsolutePath(getModel().getLeftSideModel().getSelectedNode());
        final File f2 = TreeUtils.calculateAbsolutePath(getModel().getRightSideModel().getSelectedNode());

        if (f1.exists() && f2.exists()) {
            new FileDiffController(viewManager, codeHighlighterManager, f1, f2);
        }
    }

    public void cancel() {
        folderDiffWorker.cancel(true);
    }

    public void disableCancelActionEnabling() {
        getModel().setCancelActionEnabled(false);
    }

    class FolderDiffModelChangeListenerImpl implements FolderDiffModelChangeListener {

        public void statusChanged() {
            // ignored.
        }

        public void nodeInserted(DiffFileTreeNode inserted) {
            // ignored.
        }

        public void selectedNodesChanged(DiffFileTreeNode leftNode, DiffFileTreeNode rightNode) {
            boolean expectedEnabling = false;
            if (leftNode != null && rightNode != null) {
                if (leftNode.getFileType() == FileTypeEnum.file && leftNode.getStatus() == DiffStatusEnum.changed) {
                    expectedEnabling = true;
                }
            }
            if (getModel().isCompareActionEnabled() != expectedEnabling) {
                getModel().setCompareActionEnabled(expectedEnabling);
            }
        }

        public void compareActionEnablingChanged() {
            // ignored.
        }

        public void cancelActionEnablingChanged() {
            // ignored.
        }
    }
}
