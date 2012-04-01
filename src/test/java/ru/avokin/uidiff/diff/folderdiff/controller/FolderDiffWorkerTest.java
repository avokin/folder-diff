package ru.avokin.uidiff.diff.folderdiff.controller;

import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.uidiff.diff.folderdiff.model.*;
import ru.avokin.uidiff.fs.common.model.FileTypeEnum;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 28.09.2010
 */
public class FolderDiffWorkerTest {

    private FolderDiffWorker folderDiffWorker;

    private FolderDiffModel model;

    private File leftFolder;

    private File rightFolder;

    private FolderDiffSideModel createSideModel(String testFolder, File sideFolder) {
        DiffFileTreeNode root = new DiffFileTreeNode(testFolder, sideFolder.getParentFile().getAbsolutePath(),
                FileTypeEnum.folder);
        DiffTreeModel leftDiffTreeModel = new DiffTreeModel(root);
        return new FolderDiffSideModel(leftDiffTreeModel, sideFolder.getName(), sideFolder.getAbsolutePath());
    }

    private FolderDiffModel createModel(String testFolder, File leftFolder, File rightFolder) {
        FolderDiffSideModel leftSideModel = createSideModel(testFolder, leftFolder);
        FolderDiffSideModel rightSideModel = createSideModel(testFolder, rightFolder);

        return new FolderDiffModel(leftSideModel, rightSideModel);
    }

    public void init(String testFolder) throws URISyntaxException {
        URL urlLeftFolder = getClass().getResource(testFolder + "/left");
        leftFolder = new File(urlLeftFolder.toURI());
        URL urlRightFolder = getClass().getResource(testFolder + "/right");
        rightFolder = new File(urlRightFolder.toURI());

        model = createModel(testFolder, leftFolder, rightFolder);

        folderDiffWorker = new FolderDiffWorker(leftFolder, rightFolder, model, null);
    }

    @Test
    public void test01() throws URISyntaxException, InterruptedException {
        init("/folderdiff/test01");

        folderDiffWorker.diff(false, leftFolder, rightFolder, null, null, model,
                (DiffFileTreeNode) model.getLeftSideModel().getTreeModel().getRoot(),
                (DiffFileTreeNode) model.getRightSideModel().getTreeModel().getRoot());

        Assert.assertEquals(6, model.getDifferenceCount());

        Assert.assertEquals(DiffStatusEnum.deleted, model.getLeftSideModel().getTreeModel().getDifference(0).getStatus());
        Assert.assertEquals("folder01", model.getLeftSideModel().getTreeModel().getDifference(0).getFsName());

        Assert.assertEquals(DiffStatusEnum.added, model.getRightSideModel().getTreeModel().getDifference(1).getStatus());
        Assert.assertEquals("file02_01", model.getRightSideModel().getTreeModel().getDifference(1).getFsName());

        Assert.assertEquals(DiffStatusEnum.changed, model.getLeftSideModel().getTreeModel().getDifference(2).getStatus());
        Assert.assertEquals("file02_04", model.getLeftSideModel().getTreeModel().getDifference(2).getFsName());

        Assert.assertEquals(DiffStatusEnum.changed, model.getLeftSideModel().getTreeModel().getDifference(3).getStatus());
        Assert.assertEquals("file02_05", model.getLeftSideModel().getTreeModel().getDifference(3).getFsName());

        Assert.assertEquals(DiffStatusEnum.deleted, model.getLeftSideModel().getTreeModel().getDifference(4).getStatus());
        Assert.assertEquals("file02_06", model.getLeftSideModel().getTreeModel().getDifference(4).getFsName());

        Assert.assertEquals(DiffStatusEnum.added, model.getRightSideModel().getTreeModel().getDifference(5).getStatus());
        Assert.assertEquals("folder03", model.getRightSideModel().getTreeModel().getDifference(5).getFsName());
    }
}
