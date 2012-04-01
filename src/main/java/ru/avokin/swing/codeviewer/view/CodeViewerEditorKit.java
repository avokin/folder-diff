package ru.avokin.swing.codeviewer.view;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.ViewFactory;

/**
 * User: Andrey Vokin
 * Date: 23.07.2010
 */
class CodeViewerEditorKit extends DefaultEditorKit {

    @Override
    public ViewFactory getViewFactory() {
        return new CodeViewerViewFactory();
    }
}
