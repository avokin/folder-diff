package ru.avokin.swing.codeviewer.view;

import ru.avokin.swing.codeviewer.model.CodeViewerModel;
import ru.avokin.swing.widehighlighting.WideHighlightingTextUI;

import javax.swing.*;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 16.07.2010
 */
class CodeViewerPane extends JEditorPane {

    private CodeViewerModel model;

    public CodeViewerPane() {
        setUI(new WideHighlightingTextUI());
    }

    protected int getSymbolWidth() {
        // I'm using monospaced font, so i can use theLongestLineLength of any char.
        return getFontMetrics(getFont()).getWidths()[' '];
    }

    @Override
    public Dimension getPreferredSize() {
        int theLongestLineLength = model.getTheLongestLineLength() * getSymbolWidth();

        Dimension pref = super.getPreferredSize();
        return new Dimension(Math.max(getParent().getWidth(), theLongestLineLength), (int) pref.getHeight());
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public void setDocument(CodeViewerModel doc) {
        model = doc;
        super.setDocument(doc);
    }
}
