package ru.avokin.uidiff.mainframe.controller;

import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.uidiff.common.controller.AbstractController;
import ru.avokin.uidiff.fs.fsbrowser.controller.FsBrowserController;
import ru.avokin.uidiff.mainframe.view.MainFrame;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;

/**
 * User: Andrey Vokin
 * Date: 10.09.2010
 */
public class MainFrameController extends AbstractController {

    private final MainFrame view;

    public MainFrameController(ViewManager viewFactory, CodeHighlighterManager codeHighlighterManager) {
        super(viewFactory);

        view = viewFactory.createMainFrame();
        new FsBrowserController(viewFactory, codeHighlighterManager);
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.setVisible(true);
            }
        });
    }
}