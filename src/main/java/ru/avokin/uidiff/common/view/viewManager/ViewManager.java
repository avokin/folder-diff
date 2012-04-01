package ru.avokin.uidiff.common.view.viewManager;

import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;
import ru.avokin.uidiff.diff.filediff.view.FileDiffFrame;
import ru.avokin.uidiff.diff.folderdiff.model.FolderDiffModel;
import ru.avokin.uidiff.diff.folderdiff.view.FolderDiffFrame;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModel;
import ru.avokin.uidiff.common.view.View;
import ru.avokin.uidiff.fs.fsbrowser.view.FsBrowserFrame;
import ru.avokin.uidiff.mainframe.view.MainFrame;

/**
 * User: Andrey Vokin
 * Date: 10.09.2010
 */
public interface ViewManager {

    public MainFrame createMainFrame();

    public FileDiffFrame createFileDiffFrame(FileDiffModel model);

    public FolderDiffFrame createFolderDiffFrame(FolderDiffModel model);

    public FsBrowserFrame createFsBrowserFrame(FsBrowserModel model);

    public View getCurrentView();
}
