package ru.avokin.uidiff.diff.folderdiff.model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Andrey Vokin
 * Date: 27.08.2010
 */
public class DiffTreeModel extends DefaultTreeModel {

    private int differenceCount;

    private final Map<Integer, DiffFileTreeNode> numberDiffMap;

    public DiffTreeModel(TreeNode root) {
        super(root);

        // Writing to map performed in working thread, reading from map performed from swing thread
        numberDiffMap = new ConcurrentHashMap<Integer, DiffFileTreeNode>();
    }

    @Override
    public void insertNodeInto(MutableTreeNode newChild, MutableTreeNode parent, int index) {
        DiffFileTreeNode diffFileTreeNode = (DiffFileTreeNode) newChild;
        if (newChild.children().hasMoreElements()) {
            throw new UnsupportedOperationException();
        }
        DiffStatusEnum status = diffFileTreeNode.getStatus();
        if (status != DiffStatusEnum.equal) {
            numberDiffMap.put(differenceCount, (DiffFileTreeNode) newChild);
            differenceCount++;
        }
        super.insertNodeInto(newChild, parent, index);
    }

    @Override
    public void removeNodeFromParent(MutableTreeNode node) {
        throw new UnsupportedOperationException();
    }

    public int getDifferenceCount() {
        return differenceCount;
    }

    public DiffFileTreeNode getDifference(int number) {
        return numberDiffMap.get(number);
    }
}
