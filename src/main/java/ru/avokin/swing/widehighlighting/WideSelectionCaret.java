package ru.avokin.swing.widehighlighting;

import javax.swing.text.DefaultCaret;
import javax.swing.text.Highlighter;

/**
 * User: Andrey Vokin
 * Date: 16.07.2010
 */
public class WideSelectionCaret extends DefaultCaret {

    private WideSelectionCaret() {
    }

    public static WideSelectionCaret getInstance() {
        return new WideSelectionCaret();
    }

    @Override
    protected Highlighter.HighlightPainter getSelectionPainter() {
        return WideSelectionHighlighterPainter.getInstance();
    }
}
