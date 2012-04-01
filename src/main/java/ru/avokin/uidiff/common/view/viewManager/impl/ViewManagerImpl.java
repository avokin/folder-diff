package ru.avokin.uidiff.common.view.viewManager.impl;

import ru.avokin.uidiff.common.view.View;
import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;
import ru.avokin.uidiff.diff.filediff.view.FileDiffFrame;
import ru.avokin.uidiff.diff.folderdiff.view.FolderDiffFrame;
import ru.avokin.uidiff.fs.fsbrowser.model.FsBrowserModel;
import ru.avokin.uidiff.fs.fsbrowser.view.FsBrowserFrame;
import ru.avokin.uidiff.mainframe.view.MainFrame;
import ru.avokin.uidiff.common.view.actions.ActionManager;
import ru.avokin.uidiff.diff.filediff.model.FileDiffModel;
import ru.avokin.uidiff.diff.folderdiff.model.FolderDiffModel;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 10.09.2010
 */
public class ViewManagerImpl implements ViewManager {

    private static final String FILE_DIFF_HEADER_TEMPLATE = "%s - %s";

    private static final URL ICON_FOLDER_DIFF_URL = MainFrame.class.getResource("/icons/folderDiff.png");

    private static final Icon ICON_FOLDER_DIFF = new ImageIcon(ICON_FOLDER_DIFF_URL);

    private static final URL ICON_FILE_DIFF_URL = MainFrame.class.getResource("/icons/fileDiff.png");

    private static final Icon ICON_FILE_DIFF = new ImageIcon(ICON_FILE_DIFF_URL);

    private static final URL ICON_FILE_SYSTEM_URL = MainFrame.class.getResource("/icons/fileSystem.png");

    private static final Icon ICON_FILE_SYSTEM = new ImageIcon(ICON_FILE_SYSTEM_URL);

    private MainFrame mainFrame;

    private final ActionManager actionManager;

    private String getDiffTitle(AbstractDiffModel model) {
        String result;
        String leftName = model.getLeftSideModel().getFileName();
        String rightName = model.getRightSideModel().getFileName();
        //noinspection IfMayBeConditional
        if (leftName.equals(rightName)) {
            result = leftName;
        } else {
            result = String.format(FILE_DIFF_HEADER_TEMPLATE, leftName, rightName);
        }

        return result;
    }

    public ViewManagerImpl(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public MainFrame createMainFrame() {
        mainFrame = new MainFrame(actionManager, this);
        return mainFrame;
    }

    public FileDiffFrame createFileDiffFrame(FileDiffModel model) {
        FileDiffFrame result = new FileDiffFrame(model, actionManager);
        String title = getDiffTitle(model);
        mainFrame.addTab(title, result, ICON_FILE_DIFF, true);
        mainFrame.validate();
        result.performAdjusting();
        return result;
    }

    public FolderDiffFrame createFolderDiffFrame(FolderDiffModel model) {
        FolderDiffFrame result = new FolderDiffFrame(model, actionManager);
        String title = getDiffTitle(model);
        mainFrame.addTab(title, result, ICON_FOLDER_DIFF, true);
        mainFrame.validate();
        result.performAdjusting();
        return result;
    }

    public FsBrowserFrame createFsBrowserFrame(FsBrowserModel model) {
        FsBrowserFrame result = new FsBrowserFrame(model, actionManager);
        mainFrame.addTab("File System", result, ICON_FILE_SYSTEM, false);
        return result;
    }

    public View getCurrentView() {
        return (View) mainFrame.getSelectedTab();
    }
}
