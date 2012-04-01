package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.filediff.CodeBlock;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;

import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 12.08.2010
 */
public class FileDiffAdjuster {

    private final int charHeight;

    public FileDiffAdjuster(int charHeight) {
        this.charHeight = charHeight;
    }

    public int getIndexOfLastBlockBeforeMiddleArea(int middle, List<FileDiffCodeBlock> codeBlockList) {
        int result = -1;
        if (codeBlockList.size() == 0) {
            return result;
        }

        int l = 0;
        int r = codeBlockList.size() - 1;

        // Binary search
        while (l + 1 < r) {
            int m = (l + r) / 2;

            FileDiffCodeBlock cb = codeBlockList.get(m);
            if (isBlockBeforeMiddleLine(cb, middle)) {
                l = m;
            } else {
                r = m;
            }
        }

        FileDiffCodeBlock cb = codeBlockList.get(r);
        if (isBlockBeforeMiddleLine(cb, middle)) {
            result = r;
        } else {
            cb = codeBlockList.get(l);
            if (isBlockBeforeMiddleLine(cb, middle)) {
                result = l;
            }
        }

        return result;
    }

    protected boolean isBlockBeforeMiddleLine(FileDiffCodeBlock cb, int middle) {
        int bcHeight = cb.getEndLine() - cb.getStartLine() + 1;
        return (cb.getStartLine() + bcHeight) * charHeight <= middle;
    }

    public FileDiffCodeBlock getBlockInMiddleArea(int indexLastBlockBeforeMiddleLine, int middle, List<FileDiffCodeBlock> codeBlockList) {
        FileDiffCodeBlock result = null;
        
        indexLastBlockBeforeMiddleLine++;

        if (indexLastBlockBeforeMiddleLine < codeBlockList.size()) {
            FileDiffCodeBlock cb = codeBlockList.get(indexLastBlockBeforeMiddleLine);
            if (cb.getStartLine() * charHeight <= middle + charHeight) {
                int bcHeight = cb.getEndLine() - cb.getStartLine() + 1;
                if ((cb.getStartLine() + bcHeight) * charHeight > middle) {
                    result = cb;
                }
            }
        }

        return result;
    }

    public int deltaOfPassedBlocks(List<FileDiffCodeBlock> codeBlockList, List<FileDiffCodeBlock> oppositeCodeBlockList, int indexOfLastBlockBeforeMiddleLine) {
        CodeBlock cb = codeBlockList.get(indexOfLastBlockBeforeMiddleLine);
        CodeBlock oppositeCb = oppositeCodeBlockList.get(indexOfLastBlockBeforeMiddleLine);
        return (oppositeCb.getEndLine() - cb.getEndLine()) * charHeight;
    }

    public int deltaInMiddle(List<FileDiffCodeBlock> codeBlockList, List<FileDiffCodeBlock> oppositeCodeBlockList, int middle, int index, int delta) {
        int leftHeight = codeBlockList.get(index).getEndLine() - codeBlockList.get(index).getStartLine() + 1;
        int leftDone = middle - ((codeBlockList.get(index).getStartLine() - 1) * charHeight);
        double doneRelative = 1.0 * leftDone / ((leftHeight + 1) * charHeight);

        int finalDelta = deltaOfPassedBlocks(codeBlockList, oppositeCodeBlockList, index);
        return (int) (delta + (finalDelta - delta) * doneRelative);
    }

    public int delta(List<FileDiffCodeBlock> codeBlockList, List<FileDiffCodeBlock> oppositeCodeBlockList, int sourceScrollValue, int windowHeight) {
        int middle = sourceScrollValue + windowHeight / 3;
        int indexOfLastBlockBeforeMiddleLine = getIndexOfLastBlockBeforeMiddleArea(middle, codeBlockList);
        FileDiffCodeBlock cb = indexOfLastBlockBeforeMiddleLine >= 0 ? codeBlockList.get(indexOfLastBlockBeforeMiddleLine) : null;
        int result = 0;

        if (cb != null) {
            result = deltaOfPassedBlocks(codeBlockList, oppositeCodeBlockList, indexOfLastBlockBeforeMiddleLine);
        }

        cb = getBlockInMiddleArea(indexOfLastBlockBeforeMiddleLine, middle, codeBlockList);
        if (cb != null) {
            if (cb.getStartLine() * charHeight <= middle + charHeight) {
                int bcHeight = cb.getEndLine() - cb.getStartLine() + 1;
                if ((cb.getStartLine() + bcHeight) * charHeight > middle) {
                    result = deltaInMiddle(codeBlockList, oppositeCodeBlockList, middle, indexOfLastBlockBeforeMiddleLine + 1, result);
                }
            }
        }

        return result;
    }
}
