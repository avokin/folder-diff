package ru.avokin.uidiff.diff.folderdiff.model;

import ru.avokin.uidiff.fs.common.model.FileTypeEnum;
import ru.avokin.uidiff.fs.common.model.FileTreeNode;

/**
 * User: Andrey Vokin
 * Date: 30.07.2010
 */
public class DiffFileTreeNode extends FileTreeNode {

    private DiffFileTreeNode oppositeNode;

    private DiffStatusEnum status;

    private boolean markedDeleted;

    public DiffFileTreeNode(String fsName, String absolutePath, FileTypeEnum fileType) {
        super(fsName, absolutePath, fileType);
    }

    public DiffFileTreeNode getOppositeNode() {
        return oppositeNode;
    }

    public void setOppositeNode(DiffFileTreeNode oppositeNode) {
        this.oppositeNode = oppositeNode;
    }

    public DiffStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DiffStatusEnum status) {
        this.status = status;
    }

    public boolean isMarkedDeleted() {
        return markedDeleted;
    }

    public void setMarkedDeleted(boolean markedDeleted) {
        this.markedDeleted = markedDeleted;
    }
}
