package ru.avokin.uidiff.diff.filediff.model;

import ru.avokin.filediff.CodeBlock;
import ru.avokin.uidiff.diff.folderdiff.model.DiffStatusEnum;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
public class FileDiffCodeBlock extends CodeBlock {

    private final DiffStatusEnum status;

    public FileDiffCodeBlock(int startLine, int endLine, DiffStatusEnum status) {
        super(startLine, endLine);
        this.status = status;
    }

    public DiffStatusEnum getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileDiffCodeBlock)) return false;
        if (!super.equals(o)) return false;

        FileDiffCodeBlock that = (FileDiffCodeBlock) o;

        //noinspection RedundantIfStatement
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }
}
