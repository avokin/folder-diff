package ru.avokin.uidiff.diff.folderdiff.view;

import ru.avokin.settings.ColorManager;
import ru.avokin.uidiff.diff.folderdiff.model.DiffFileTreeNode;
import ru.avokin.uidiff.fs.common.model.FileTypeEnum;
import ru.avokin.uidiff.fs.common.view.FileSystemIconTreeCellRenderer;
import ru.avokin.uidiff.fs.common.view.FsTreeUI;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.net.URL;
import java.util.Enumeration;

/**
 * User: Andrey Vokin
 * Date: 28.07.2010
 */
public class FolderDiffTreeUI extends FsTreeUI {

    private static final URL ICON_FILE_MISSED_URL = FolderDiffCellRenderer.class.getResource("/icons/fileMissed.png");

    private static final URL ICON_FOLDER_MISSED_URL = FolderDiffCellRenderer.class.getResource("/icons/folderMissed.png");

    private static final Icon ICON_FILE_MISSED = new ImageIcon(ICON_FILE_MISSED_URL);

    private static final Icon ICON_FOLDER_MISSED = new ImageIcon(ICON_FOLDER_MISSED_URL);

    private final FolderDiffCellRenderer folderDiffCellRenderer;

    public FolderDiffTreeUI() {
        folderDiffCellRenderer = new FolderDiffCellRenderer();
    }

    @Override
    protected TreeCellRenderer getCellRenderer() {
        return folderDiffCellRenderer;
    }

    private Rectangle getPathBounds(TreePath path, Insets insets, Rectangle bounds) {
        bounds = treeState.getBounds(path, bounds);
        if (bounds != null) {
            bounds.x += insets.left;
            bounds.y += insets.top;
        }
        return bounds;
    }

    protected Color getRowBackgroundColor(Object pathComponent, int row) {
        Color result = null;

        boolean gray = row % 2 > 0;

        if (pathComponent instanceof DiffFileTreeNode) {
            DiffFileTreeNode m = (DiffFileTreeNode) pathComponent;
            switch (m.getStatus()) {
                case added:
                    result = gray ? ColorManager.addedGray : ColorManager.added;
                    break;
                case changed:
                    result = gray ? ColorManager.changedGray : ColorManager.changed;
                    break;
                case deleted:
                    result = gray ? ColorManager.deletedGray : ColorManager.deleted;
                    break;
            }
        }
        if (result == null) {
            result = gray ? ColorManager.notMarkedGray : ColorManager.notMarked;
        }

        return result;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        TreePath initialPath = getClosestPathForLocation(tree, 0, 0);
        Enumeration paintingEnumerator = treeState.getVisiblePathsFrom (initialPath);
        Insets insets = tree.getInsets();

        if(initialPath != null && paintingEnumerator != null) {
            int k = 0;
            while (paintingEnumerator.hasMoreElements()) {
                TreePath path = (TreePath) paintingEnumerator.nextElement();
                Color bgColor = getRowBackgroundColor(path.getLastPathComponent(), k);

                Rectangle boundsBuffer = new Rectangle();
                Rectangle bounds = getPathBounds(path, insets, boundsBuffer);

                g.setColor(bgColor);
                g.fillRect(0, bounds.y, c.getWidth(), bounds.height);

                k++;
            }
        }
        super.paint(g, c);
    }

    class FolderDiffCellRenderer extends FileSystemIconTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            backgroundNonSelectionColor = getRowBackgroundColor(value, row);

            if (value instanceof DiffFileTreeNode) {
                DiffFileTreeNode node = (DiffFileTreeNode) value;

                if (node.isMarkedDeleted()) {
                    if (node.getFileType() == FileTypeEnum.file) {
                        setIcon(ICON_FILE_MISSED);
                    } else if (node.getFileType() == FileTypeEnum.folder) {
                        setIcon(ICON_FOLDER_MISSED);
                    }
                }
            }

            return this;
        }
    }
}
