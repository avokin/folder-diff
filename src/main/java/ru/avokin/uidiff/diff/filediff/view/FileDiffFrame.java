package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;
import ru.avokin.uidiff.diff.common.view.AbstractDiffFrame;
import ru.avokin.uidiff.diff.common.view.AbstractDiffSplitPane;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;
import ru.avokin.uidiff.common.view.actions.ActionManager;

import javax.swing.*;

/**
 * User: Andrey Vokin
 * Date: 09.08.2010
 */
public class FileDiffFrame extends AbstractDiffFrame {

    public FileDiffFrame(FileDiffModel model, ActionManager actionManager) {
        super(model, actionManager);
        setStatusBarMessage(String.format(DIFF_NUMBER_STATUS_MESSAGE, model.getDifferenceCount()));
    }

    public JComponent createLeftComponent(AbstractDiffModel model) {
        FileDiffModel fileDiffModel = (FileDiffModel) model;
        return new FileDiffSidePanel(fileDiffModel.getLeftModel(), true);
    }

    public JComponent createRightComponent(AbstractDiffModel model) {
        FileDiffModel fileDiffModel = (FileDiffModel) model;
        return new FileDiffSidePanel(fileDiffModel.getRightModel(), false);
    }

    @Override
    public AbstractDiffSplitPane createSplitPane(AbstractDiffModel model) {
        FileDiffModel fileDiffModel = (FileDiffModel) model;

        final FileDiffSidePanel leftEditorPanel = (FileDiffSidePanel) createLeftComponent(fileDiffModel);
        final FileDiffSidePanel rightEditorPanel = (FileDiffSidePanel) createRightComponent(fileDiffModel);
        return new FileDiffSplitPane(fileDiffModel, leftEditorPanel, rightEditorPanel);
    }
}
