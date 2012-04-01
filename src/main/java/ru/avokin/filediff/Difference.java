package ru.avokin.filediff;

import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;

/**
 * Created by Andrey Vokin.
 * Date: 25.07.2010
 */
public class Difference {

    private final FileDiffCodeBlock leftCodeBlock;

    private final FileDiffCodeBlock rightCodeBlock;

    public Difference(FileDiffCodeBlock leftCodeBlock, FileDiffCodeBlock rightCodeBlock) {
        this.leftCodeBlock = leftCodeBlock;
        this.rightCodeBlock = rightCodeBlock;
    }

    public FileDiffCodeBlock getLeftCodeBlock() {
        return leftCodeBlock;
    }

    public FileDiffCodeBlock getRightCodeBlock() {
        return rightCodeBlock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Difference that = (Difference) o;

        if (leftCodeBlock != null ? !leftCodeBlock.equals(that.leftCodeBlock) : that.leftCodeBlock != null)
            return false;
        //noinspection RedundantIfStatement
        if (rightCodeBlock != null ? !rightCodeBlock.equals(that.rightCodeBlock) : that.rightCodeBlock != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = leftCodeBlock != null ? leftCodeBlock.hashCode() : 0;
        result = 31 * result + (rightCodeBlock != null ? rightCodeBlock.hashCode() : 0);
        return result;
    }
}
