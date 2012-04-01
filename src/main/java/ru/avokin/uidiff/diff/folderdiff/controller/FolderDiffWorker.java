package ru.avokin.uidiff.diff.folderdiff.controller;

import org.apache.log4j.Logger;
import ru.avokin.filediff.QuickFileContentComparator;
import ru.avokin.uidiff.diff.folderdiff.model.DiffFileTreeNode;
import ru.avokin.uidiff.diff.folderdiff.model.DiffStatusEnum;
import ru.avokin.uidiff.diff.folderdiff.model.FolderDiffModel;
import ru.avokin.uidiff.fs.common.model.FileTypeEnum;
import ru.avokin.utils.FileNameComparator;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 28.09.2010
 */
public class FolderDiffWorker extends SwingWorker<FolderDiffModel, String> {

    private static final Logger logger = Logger.getLogger(FolderDiffWorker.class);

    private static final int MAXIMUM_FILE_SIZE_TO_READ = 1024 * 1024;

    private final FolderDiffModel model;

    private final File rootFolder1;

    private final File rootFolder2;

    private final FolderDiffController controller;

    public FolderDiffWorker(File rootFolder1, File rootFolder2, FolderDiffModel model, FolderDiffController controller) {
        this.rootFolder1 = rootFolder1;
        this.rootFolder2 = rootFolder2;
        this.model = model;
        this.controller = controller;
    }

    @Override
    protected FolderDiffModel doInBackground() throws Exception {
        diff(true, rootFolder1, rootFolder2, null, null, model, (DiffFileTreeNode) model.getLeftSideModel().getTreeModel().getRoot(), (DiffFileTreeNode) model.getRightSideModel().getTreeModel().getRoot());
        return model;
    }

    private void addNode(boolean updateModelInEDT, final DiffFileTreeNode parent1, final DiffFileTreeNode parent2, String name1, String name2, final DiffStatusEnum status, boolean isDirectory, final FolderDiffModel result) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        @SuppressWarnings({"ConditionalExpression"})
        final DiffFileTreeNode n1 = new DiffFileTreeNode(name1, null, isDirectory ? FileTypeEnum.folder : FileTypeEnum.file);
        @SuppressWarnings({"ConditionalExpression"})
        final DiffFileTreeNode n2 = new DiffFileTreeNode(name2, null, isDirectory ? FileTypeEnum.folder : FileTypeEnum.file);
        n1.setOppositeNode(n2);
        n2.setOppositeNode(n1);
        n1.setStatus(status);
        n2.setStatus(status);

        if (status == DiffStatusEnum.deleted) {
            n2.setMarkedDeleted(true);
        } else if (status == DiffStatusEnum.added) {
            n1.setMarkedDeleted(true);
        }

        if (updateModelInEDT) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    result.addItem(n1, parent1, n2, parent2);
                    if (status != DiffStatusEnum.equal) {
                        if (!result.isNextDiffActionEnabled()) {
                            result.setNextDiffActionEnabled(true);
                        }
                    }
                }
            });
        } else {
            result.addItem(n1, parent1, n2, parent2);
            if (status != DiffStatusEnum.equal) {
                if (!result.isNextDiffActionEnabled()) {
                    result.setNextDiffActionEnabled(true);
                }
            }
        }
    }

    protected String calculateRelativePath(String rootPath, String absolutePath) {
        return absolutePath.substring(rootPath.length());
    }

    @Override
    protected void done() {
        FolderDiffModel model = null;
        try {
            model = get();
        } catch (Exception ignored) {
        }
        if (model != null) {
            model.setStatusMessage("Folder diff complete");
        }

        controller.disableCancelActionEnabling();
    }

    public void diff(boolean updateModelInEDT, File f1, File f2, final DiffFileTreeNode parent1,
                     final DiffFileTreeNode parent2, final FolderDiffModel result, DiffFileTreeNode createdNode1,
                     DiffFileTreeNode createdNode2) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        String fsPath1 = f1.getName();
        String fsPath2 = f2.getName();
        @SuppressWarnings({"ConditionalExpression", "NestedConditionalExpression"})
        final DiffFileTreeNode root1 = createdNode1 != null ? createdNode1 : new DiffFileTreeNode(fsPath1, null, f1.isDirectory() ? FileTypeEnum.folder : FileTypeEnum.file);
        @SuppressWarnings({"ConditionalExpression", "NestedConditionalExpression"})
        final DiffFileTreeNode root2 = createdNode2 != null ? createdNode2 : new DiffFileTreeNode(fsPath2, null, f1.isDirectory() ? FileTypeEnum.folder : FileTypeEnum.file);
        root1.setOppositeNode(root2);
        root2.setOppositeNode(root1);

        DiffStatusEnum status = DiffStatusEnum.equal;
        if (f1.isFile()) {
            try {
                if (f1.length() == f2.length()) {
                    // Skipping big files, I suppose that they binary
                    if (f1.length() <  MAXIMUM_FILE_SIZE_TO_READ) {
                        if (!QuickFileContentComparator.equals(f1, f2)) {
                            status = DiffStatusEnum.changed;
                        }
                    }
                } else {
                    status = DiffStatusEnum.changed;
                }
            } catch (Exception e) {
                logger.error(e, e);
            }
        }

        root1.setStatus(status);
        root2.setStatus(status);

        if (createdNode1 == null) {
            if (updateModelInEDT) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        result.addItem(root1, parent1, root2, parent2);
                    }
                });
            } else {
                result.addItem(root1, parent1, root2, parent2);
            }
        }

        if (f1.isFile()) {
            return;
        }

        File[] l1 = f1.listFiles();
        File[] l2 = f2.listFiles();

        if (l1 != null) {
            Arrays.sort(l1, FileNameComparator.getInstance());
        }
        if (l2 != null) {
            Arrays.sort(l2, FileNameComparator.getInstance());
        }

        int i1 = 0;
        int i2 = 0;

        boolean[] isDirectory = new boolean[] {true, false};

        for (int i = 0; i < 2; i++) {

            while (l1 != null && i1 < l1.length && l2 != null && i2 < l2.length && (l1[i1].isDirectory() == isDirectory[i]) && (l2[i2].isDirectory() == isDirectory[i])) {
                int c = l1[i1].getName().compareToIgnoreCase(l2[i2].getName());
                if (c == 0) {
                    publish(calculateRelativePath(rootFolder1.getAbsolutePath(), l1[i1].getAbsolutePath()));
                    diff(updateModelInEDT, l1[i1], l2[i2], root1, root2, result, null, null);
                    i1++;
                    i2++;
                } else if (c > 0) {
                    publish(calculateRelativePath(rootFolder2.getAbsolutePath(), l2[i2].getAbsolutePath()));
                    addNode(updateModelInEDT, root1, root2, " ", l2[i2].getName(), DiffStatusEnum.added, l2[i2].isDirectory(), result);
                    i2++;
                } else if (c < 0) {
                    publish(calculateRelativePath(rootFolder1.getAbsolutePath(), l1[i1].getAbsolutePath()));
                    addNode(updateModelInEDT,root1, root2, l1[i1].getName(), " ", DiffStatusEnum.deleted, l1[i1].isDirectory(), result);
                    i1++;
                }
            }

            while (l1 != null && i1 < l1.length && (l1[i1].isDirectory() == isDirectory[i])) {
                publish(calculateRelativePath(rootFolder1.getAbsolutePath(), l1[i1].getAbsolutePath()));
                addNode(updateModelInEDT,root1, root2, l1[i1].getName(), " ", DiffStatusEnum.deleted, l1[i1].isDirectory(), result);
                i1++;
            }

            while (l2 != null && i2 < l2.length && (l2[i2].isDirectory() == isDirectory[i])) {
                publish(calculateRelativePath(rootFolder2.getAbsolutePath(), l2[i2].getAbsolutePath()));
                addNode(updateModelInEDT,root1, root2, " ", l2[i2].getName(), DiffStatusEnum.added, l2[i2].isDirectory(), result);
                i2++;
            }
        }
    }

    @Override
    protected void process(List<String> chunks) {
        if (chunks.size() > 0) {
            model.setStatusMessage(chunks.get(chunks.size() - 1));
        }
    }
}
