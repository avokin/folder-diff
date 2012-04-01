package ru.avokin.uidiff.diff.filediff.model;

import ru.avokin.filediff.FileDifference;
import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;

/**
 * Created by Andrey Vokin.
 * Date: 25.07.2010
 */
public class FileDiffModel extends AbstractDiffModel {

    private final FileDifference fileDifference;

    public FileDiffModel(FileDiffSideModel leftModel, FileDiffSideModel rightModel, FileDifference fileDifference) {
        super(leftModel, rightModel);
        
        this.fileDifference = fileDifference;
        nextDiffActionEnabled = getDifferenceCount() > 0;
    }

    @Override
    public int getDifferenceCount() {
        return fileDifference.getDifferenceList().size();
    }

    public FileDiffSideModel getLeftModel() {
        return (FileDiffSideModel) super.getLeftSideModel();
    }

    public FileDiffSideModel getRightModel() {
        return (FileDiffSideModel) super.getRightSideModel();
    }

    public FileDifference getFileDifference() {
        return fileDifference;
    }
}
