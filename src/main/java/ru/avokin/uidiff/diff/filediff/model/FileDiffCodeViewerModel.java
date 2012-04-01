package ru.avokin.uidiff.diff.filediff.model;

import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.swing.codeviewer.model.CodeViewerModel;
import ru.avokin.swing.codeviewer.model.LineNumberPosition;

import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
public class FileDiffCodeViewerModel extends CodeViewerModel {

    private FileDiffCodeBlock selectedCodeBlock;

    public FileDiffCodeViewerModel(List<HighlightedCodeBlock> highlightedCodeBlockList, String text, LineNumberPosition lineNumberPosition) {
        super(highlightedCodeBlockList, text, lineNumberPosition);
    }

    public FileDiffCodeBlock getSelectedCodeBlock() {
        return selectedCodeBlock;
    }

    public void setSelectedCodeBlock(FileDiffCodeBlock selectedCodeBlock) {
        this.selectedCodeBlock = selectedCodeBlock;
    }
}
