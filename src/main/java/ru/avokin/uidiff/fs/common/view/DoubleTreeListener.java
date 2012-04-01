package ru.avokin.uidiff.fs.common.view;

import ru.avokin.uidiff.diff.folderdiff.model.DiffFileTreeNode;

import javax.swing.*;
import javax.swing.tree.TreePath;

/**
 * User: Andrey Vokin
 * Date: 01.08.2010
 */
public abstract class DoubleTreeListener {

    protected final JTree t1;

    protected final JTree t2;

    protected DoubleTreeListener(JTree t1, JTree t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    protected JTree getOppositeTree(Object t) {
        //noinspection ConditionalExpression
        return t == t1 ? t2 : t1;
    }

    protected TreePath getOppositeTreePath(DiffFileTreeNode node) {
        return new TreePath(node.getOppositeNode().getPath());
    }
}
