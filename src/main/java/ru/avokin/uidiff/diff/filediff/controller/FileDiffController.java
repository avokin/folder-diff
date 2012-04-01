package ru.avokin.uidiff.diff.filediff.controller;

import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;
import ru.avokin.uidiff.diff.common.controller.AbstractDiffController;
import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;

import java.io.File;

/**
 * User: Andrey Vokin
 * Date: 10.08.2010
 */
public class FileDiffController extends AbstractDiffController {

    public FileDiffController(final ViewManager viewFactory, CodeHighlighterManager codeHighlighterManager, File f1, File f2) {
        super(viewFactory);

        FileDiffWorker fileDiffWorker = new FileDiffWorker(f1, f2, this, codeHighlighterManager);
        fileDiffWorker.execute();
    }

    public void setModel(AbstractDiffModel model) {
        this.model = model;
        view = viewManager.createFileDiffFrame((FileDiffModel) model);
        viewInitialized();
    }
}
