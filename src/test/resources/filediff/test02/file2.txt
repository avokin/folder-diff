package ru.avokin.uidiff.diff.filediff.controller;

import org.apache.log4j.Logger;
import ru.avokin.filediff.FileDifference;
import ru.avokin.filediff.LongestCommonSubsequenceSearcher;
import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.swing.codeviewer.model.LineNumberPosition;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeViewerModel;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;
import ru.avokin.uidiff.diff.filediff.model.FileDiffSideModel;
import ru.avokin.utils.FileUtils;
import ru.avokin.utils.StringUtils;

import javax.swing.*;
import java.io.File;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
public class FileDiffWorker extends SwingWorker<FileDiffModel, String> {

    private static final Logger logger = Logger.getLogger(FileDiffWorker.class);

    private final File f1;

    private final File f2;

    private final FileDiffController controller;

    private final CodeHighlighterManager codeHighlighterManager;

    public FileDiffWorker(File f1, File f2, FileDiffController controller,
                          CodeHighlighterManager codeHighlighterManager) {
        this.f1 = f1;
        this.f2 = f2;
        this.controller = controller;
        this.codeHighlighterManager = codeHighlighterManager;
    }

    @Override
    protected FileDiffModel doInBackground() throws Exception {
        String text1 = FileUtils.readFileAsString(f1.getAbsolutePath());
        String text2 = FileUtils.readFileAsString(f2.getAbsolutePath());

        List<String> lines1 = StringUtils.splitToLines(text1);
        List<String> lines2 = StringUtils.splitToLines(text2);

        FileDifference fileDifference = LongestCommonSubsequenceSearcher.diff(lines1, lines2);

        List<HighlightedCodeBlock> highlightedCodeBlockList1 =
                codeHighlighterManager.getCodeHighlighter(f1).highlight(text1);
        FileDiffCodeViewerModel leftFileDiffCodeViewerModel =
                new FileDiffCodeViewerModel(highlightedCodeBlockList1, text1, LineNumberPosition.left);
        FileDiffSideModel leftModel = new FileDiffSideModel(f1.getName(), f1.getAbsolutePath(),
                fileDifference.getLeftCodeBlockList(), leftFileDiffCodeViewerModel);

        List<HighlightedCodeBlock> highlightedCodeBlockList2 =
                codeHighlighterManager.getCodeHighlighter(f2).highlight(text2);
        FileDiffCodeViewerModel rightFileDiffCodeViewerModel =
                new FileDiffCodeViewerModel(highlightedCodeBlockList2, text2, LineNumberPosition.right);
        FileDiffSideModel rightModel = new FileDiffSideModel(f2.getName(), f2.getAbsolutePath(),
                fileDifference.getRightCodeBlockList(), rightFileDiffCodeViewerModel);
        return new FileDiffModel(leftModel, rightModel, fileDifference);
    }

    @Override
    protected void done() {
        FileDiffModel model = null;
        try {
            model = get();
        } catch (Exception e) {
            logger.error(e, e);
        }
        if (model != null) {
            controller.setModel(model);
        }
    }
}
