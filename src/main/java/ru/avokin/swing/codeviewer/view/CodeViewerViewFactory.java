package ru.avokin.swing.codeviewer.view;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * User: Andrey Vokin
 * Date: 23.07.2010
 */
class CodeViewerViewFactory implements ViewFactory {

    public View create(Element elem) {
        return new CodeViewerView(elem);
    }
}
