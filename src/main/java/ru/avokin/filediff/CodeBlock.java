package ru.avokin.filediff;

/**
 * User: Andrey Vokin
 * Date: 30.09.2010
 */
public class CodeBlock {

    private final int startLine;

    private final int endLine;

    public CodeBlock(int startLine, int endLine) {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeBlock codeBlock = (CodeBlock) o;

        if (endLine != codeBlock.endLine) return false;
        //noinspection RedundantIfStatement
        if (startLine != codeBlock.startLine) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startLine;
        result = 31 * result + endLine;
        return result;
    }
}
