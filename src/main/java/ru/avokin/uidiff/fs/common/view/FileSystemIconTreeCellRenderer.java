package ru.avokin.uidiff.fs.common.view;

import ru.avokin.uidiff.fs.common.model.FileTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 02.08.2010
 */
public class FileSystemIconTreeCellRenderer extends DefaultTreeCellRenderer {

    private static final URL ICON_FILE_URL = FileSystemIconTreeCellRenderer.class.getResource("/icons/file.png");

    private static final URL ICON_FOLDER_URL = FileSystemIconTreeCellRenderer.class.getResource("/icons/folder.png");

    private static final URL ICON_DISK_URL = FileSystemIconTreeCellRenderer.class.getResource("/icons/disk.png");

    private static final URL ICON_COMPUTER_URL = FileSystemIconTreeCellRenderer.class.getResource("/icons/myComputer.png");

    private static final Icon ICON_FILE = new ImageIcon(ICON_FILE_URL);

    private static final Icon ICON_FOLDER = new ImageIcon(ICON_FOLDER_URL);

    private static final Icon ICON_DISK = new ImageIcon(ICON_DISK_URL);

    private static final Icon ICON_COMPUTER = new ImageIcon(ICON_COMPUTER_URL);

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (value instanceof FileTreeNode) {
            FileTreeNode node = (FileTreeNode) value;

            //noinspection SwitchStatement
            switch (node.getFileType()) {
                case file:
                    setIcon(ICON_FILE);
                    break;
                case folder:
                    setIcon(ICON_FOLDER);
                    break;
                case disk:
                    setIcon(ICON_DISK);
                    break;
                case computer:
                    setIcon(ICON_COMPUTER);
                    break;
            }
        }

        return this;
    }
}
