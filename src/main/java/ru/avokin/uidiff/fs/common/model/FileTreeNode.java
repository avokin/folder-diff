package ru.avokin.uidiff.fs.common.model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * User: Andrey Vokin
 * Date: 01.08.2010
 */
public class FileTreeNode extends DefaultMutableTreeNode {

    public static final String COMPUTER_FS_NAME = "Computer";
    
    private final String fsName;

    private final String absolutePath;

    private final FileTypeEnum fileType;

    public FileTreeNode(String fsName, String absolutePath, FileTypeEnum fileType) {
        this.fsName = fsName;
        this.absolutePath = absolutePath;
        this.fileType = fileType;
    }

    public String getFsName() {
        return fsName;
    }

    public boolean getAllowsChildren() {
        return fileType != FileTypeEnum.file;
    }

    public boolean isLeaf() {
        return fileType == FileTypeEnum.file;
    }

    public FileTypeEnum getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        if (fsName == null) {
            return "";
        }

        return fsName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
