package ru.avokin.swing.codeviewer.model;

import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.utils.StringUtils;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.Collections;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 29.09.2010
 */
public class CodeViewerModel extends PlainDocument {

    private final List<LinePosition> linePositionList;

    private List<LinePosition> lineNumbersLinePositionList;

    private final LineNumberPosition lineNumberPosition;

    private final List<HighlightedCodeBlock> highlightedCodeBlockList;

    private int theLongestLineLength;

    public CodeViewerModel(List<HighlightedCodeBlock> highlightedCodeBlockList, String text,
                           LineNumberPosition lineNumberPosition) {
        try {
            getContent().insertString(0, text);
        } catch (BadLocationException ignored) {
        }
        this.lineNumberPosition = lineNumberPosition;
        this.highlightedCodeBlockList = Collections.unmodifiableList(highlightedCodeBlockList);

        linePositionList = StringUtils.prepareLinePositions(text);
        for (LinePosition lp : linePositionList) {
            int length = lp.getRight() - lp.getLeft();
            if (length > theLongestLineLength) {
                theLongestLineLength = length;
            }
        }
    }

    public LineNumberPosition getLineNumberPosition() {
        return lineNumberPosition;
    }
   
    public int getTheLongestLineLength() {
        return theLongestLineLength;
    }

    public int getLineCount() {
        return linePositionList.size();
    }

    public List<HighlightedCodeBlock> getHighlightedCodeBlockList() {
        return highlightedCodeBlockList;
    }

    public List<LinePosition> getLinePositionList() {
        return linePositionList;
    }

    public List<LinePosition> getLineNumbersLinePositionList() {
        return lineNumbersLinePositionList;
    }

    public void setLineNumbersLinePositionList(List<LinePosition> lineNumbersLinePositionList) {
        this.lineNumbersLinePositionList = lineNumbersLinePositionList;
    }

    public String getText() {
        String result = null;
        try {
            result = getText(0, getLength());
        } catch (BadLocationException ignored) {
        }
        return result;
    }
}
