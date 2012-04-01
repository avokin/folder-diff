package ru.avokin.swing.codeviewer.model;

/**
 * User: Andrey Vokin
 * Date: 19.07.2010
 */
public class LinePosition {

    private final int left;

    private final int right;

    public LinePosition(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinePosition that = (LinePosition) o;

        if (left != that.left) return false;
        //noinspection RedundantIfStatement
        if (right != that.right) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left;
        result = 31 * result + right;
        return result;
    }
}
