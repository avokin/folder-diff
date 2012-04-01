package ru.avokin.utils;

import ru.avokin.uidiff.fs.common.model.FileTreeNode;

import java.io.File;

/**
 * User: Andrey Vokin
 * Date: 27.08.2010
 */
public class TreeUtils {

    public static File calculateAbsolutePath(FileTreeNode node) {
        StringBuffer sb = new StringBuffer();
        while (node != null && !node.getFsName().equals(FileTreeNode.COMPUTER_FS_NAME)) {
            String fileName = node.getAbsolutePath() != null ? node.getAbsolutePath() : node.getFsName();
            sb.insert(0, File.separator).insert(0, fileName);
            node = (FileTreeNode) node.getParent();
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return new File(sb.toString());
    }
}
