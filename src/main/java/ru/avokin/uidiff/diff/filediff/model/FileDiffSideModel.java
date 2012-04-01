package ru.avokin.uidiff.diff.filediff.model;

import ru.avokin.uidiff.diff.common.model.DiffSideModel;

import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 30.09.2010
 */
public class FileDiffSideModel extends DiffSideModel {

    private final List<FileDiffCodeBlock> highlightedBlockList;

    private final FileDiffCodeViewerModel fileDiffCodeViewerModel;

    public FileDiffSideModel(String title, String filePath, List<FileDiffCodeBlock> highlightedBlockList, FileDiffCodeViewerModel fileDiffCodeViewerModel) {
        super(title, filePath);

        this.highlightedBlockList = highlightedBlockList;
        this.fileDiffCodeViewerModel = fileDiffCodeViewerModel;
    }

    public List<FileDiffCodeBlock> getHighlightedBlockList() {
        return highlightedBlockList;
    }

    public FileDiffCodeViewerModel getFileDiffCodeViewerModel() {
        return fileDiffCodeViewerModel;
    }
}
