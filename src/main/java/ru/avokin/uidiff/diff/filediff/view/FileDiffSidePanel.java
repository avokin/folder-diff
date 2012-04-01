package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.uidiff.diff.common.model.DiffSideModel;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;
import ru.avokin.uidiff.diff.filediff.model.FileDiffSideModel;
import ru.avokin.uidiff.diff.common.view.AbstractDiffSidePanel;

import javax.swing.*;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 01.10.2010
 */
public class FileDiffSidePanel extends AbstractDiffSidePanel {

    private FileDiffCodeViewerPanel codeViewer;

    protected FileDiffSidePanel(FileDiffSideModel model, boolean left) {
        super(model, left);

        for (FileDiffCodeBlock cb : model.getHighlightedBlockList()) {
            Color c = DiffColorManager.getColor(cb);
            codeViewer.addHighlighter(cb, c);
        }
    }

    @Override
    protected JComponent createDiffSideView(DiffSideModel model, boolean left) {
        FileDiffSideModel fileDiffSideModel = (FileDiffSideModel) model;
        codeViewer = new FileDiffCodeViewerPanel(fileDiffSideModel.getFileDiffCodeViewerModel());
        return codeViewer;
    }

    @Override
    public void selectDifference(int number, boolean withScrolling) {
        FileDiffSideModel model = (FileDiffSideModel) getModel();
        codeViewer.selectFileDiffCodeBlock(model.getHighlightedBlockList().get(number), withScrolling);
    }

    @Override
    public JScrollBar getHorizontalScrollBar() {
        return codeViewer.getHorizontalScrollBar();
    }

    @Override
    public JScrollBar getVerticalScrollBar() {
        return codeViewer.getVerticalScrollBar();
    }
}
